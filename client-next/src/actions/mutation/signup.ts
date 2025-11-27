"use server";

import { axios } from "@/lib/axios";
import { signupSchema } from "@/schemas/signup";
import { type UserDTO } from "@/types/types";
import { cookies } from "next/headers";
import setCookieParser from "set-cookie-parser";

export async function signupAction(_prevState: any, formData: FormData) {
  const parsed = signupSchema.safeParse({
    name: formData.get("name"),
    email: formData.get("email"),
    password: formData.get("password"),
  });

  if (!parsed.success) {
    return {
      success: false,
      zodErrors: parsed.error.flatten().fieldErrors,
      message: "Validation failed",
    };
  }

  const { name, email, password } = parsed.data;

  try {
    const res = await axios.post<{ token: string; user: UserDTO }>("/api/auth/register", {
      name,
      email,
      password,
    });

    const cookieStore = await cookies();
    const cookieData = setCookieParser(res.headers["set-cookie"] || []);

    cookieData.forEach((cookie) => {
      // @ts-ignore
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      cookieStore.set(cookie.name, cookie.value, { ...cookie });
    });

    return { success: true };
  } catch (error: any) {
    return {
      success: false,
      message: error.response?.data?.message || "LULULU",
    };
  }
}
