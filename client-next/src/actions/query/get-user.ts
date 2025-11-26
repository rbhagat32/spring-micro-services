"use server";

import { axios } from "@/lib/axios";
import { type UserDTO } from "@/types/types";
import { cookies } from "next/headers";

export async function getUserAction() {
  const cookieStore = await cookies();
  const cookieHeader = cookieStore
    .getAll()
    .map((cookie) => `${cookie.name}=${cookie.value}`)
    .join("; ");

  try {
    const response = await axios.get<UserDTO>("/api/auth/get-user", {
      headers: { Cookie: cookieHeader },
    });

    return response.data;
  } catch (error: any) {
    console.log("Error fetching user:", error);
  }
}
