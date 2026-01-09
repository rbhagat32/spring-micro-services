import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { toast } from "sonner";

import { clearUser, setUser } from "@/store/reducers/user-slice";
import type { LoginFormData, SignupFormData, UserDTO } from "@/types/types";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL;

export const api = createApi({
  reducerPath: "api",
  baseQuery: fetchBaseQuery({
    baseUrl: BACKEND_URL as string,
    credentials: "include",
  }),
  tagTypes: ["USER"],

  endpoints: (builder) => ({
    getLoggedInUser: builder.query<UserDTO, void>({
      query: () => "api/auth/get-user",
      providesTags: ["USER"],
      async onQueryStarted(_, { dispatch, queryFulfilled }) {
        try {
          const { data } = await queryFulfilled;
          dispatch(setUser(data));
        } catch {
          dispatch(clearUser());
        }
      },
    }),

    login: builder.mutation<{ token: string; user: UserDTO }, LoginFormData>({
      query: (body) => ({
        url: "api/auth/login",
        method: "POST",
        body,
      }),

      async onQueryStarted(_, { dispatch, queryFulfilled }) {
        try {
          const { data } = await queryFulfilled;
          dispatch(setUser(data.user));
          toast.success(`Welcome back, ${data.user.name}!`);
        } catch (err: any) {
          toast.error(err.error.data.message || "Failed to Log In !");
          console.error("Login error:", err);
        }
      },
    }),

    signup: builder.mutation<{ token: string; user: UserDTO }, SignupFormData>({
      query: (body) => ({
        url: "api/auth/register",
        method: "POST",
        body,
      }),

      async onQueryStarted(_, { dispatch, queryFulfilled }) {
        try {
          const { data } = await queryFulfilled;
          dispatch(setUser(data.user));
          toast.success(`Account created! Welcome, ${data.user.name}!`);
        } catch (err: any) {
          toast.error(err.error.data.message || "Failed to Sign Up !");
          console.error("Signup error:", err);
        }
      },
    }),

    logout: builder.mutation<{ message: string }, void>({
      query: () => ({
        url: "api/auth/logout",
        method: "POST",
      }),

      async onQueryStarted(_, { dispatch, queryFulfilled }) {
        try {
          const { data } = await queryFulfilled;
          dispatch(clearUser());
          toast.success(data.message || "Logged out successfully!");
        } catch (err: any) {
          toast.error(err.error.data.message || "Failed to Log Out !");
          console.error("Logout error:", err);
        }
      },
    }),
  }),
});

export const { useGetLoggedInUserQuery, useLoginMutation, useSignupMutation, useLogoutMutation } =
  api;
