"use server";

import { axios } from "@/lib/axios";
import { loginSchema } from "@/schemas/login";
import { type UserDTO } from "@/types/types";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";
import setCookieParser from "set-cookie-parser";

export async function loginAction(formData: FormData) {
  const parsed = loginSchema.safeParse({
    email: formData.get("email"),
    password: formData.get("password"),
  });

  if (!parsed.success) {
    console.log("Login validation failed", parsed.error);
    return;
  }

  const { email, password } = parsed.data;

  try {
    const response = await axios.post<{ token: string; user: UserDTO }>("/api/auth/login", {
      email,
      password,
    });

    const cookieStore = await cookies();
    const cookieData = setCookieParser(response.headers["set-cookie"]);

    cookieData.forEach((cookie) => {
      // @ts-ignore
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      cookieStore.set(cookie.name, cookie.value, { ...cookie });
    });
  } catch (error) {
    console.error("Login failed:", error);
  }

  redirect("/");
}
