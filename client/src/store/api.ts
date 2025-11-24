import { setUser } from "@/store/reducers/user-slice";
import type { UserDTO } from "@/types/types";
import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { toast } from "sonner";

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
        } catch {}
      },
    }),

    login: builder.mutation<
      { token: string; user: UserDTO }, // response
      { email: string; password: string } // body
    >({
      query: (body) => ({
        url: "api/auth/login",
        method: "POST",
        body,
      }),
      invalidatesTags: ["USER"],

      async onQueryStarted(_, { dispatch, queryFulfilled }) {
        try {
          const { data } = await queryFulfilled;
          dispatch(setUser(data.user));
          toast.success(`Welcome back, ${data.user.name}!`);
        } catch (err: any) {
          toast.error(err?.data?.message || "Failed to Log In !");
        }
      },
    }),
  }),
});

export const { useGetLoggedInUserQuery, useLoginMutation } = api;
