import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { toast } from "sonner";
import { setAuth } from "@/store/reducers/auth-slice";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL;

export const api = createApi({
  reducerPath: "api",
  baseQuery: fetchBaseQuery({
    baseUrl: BACKEND_URL as string,
    credentials: "include",
  }),
  tagTypes: ["AUTH"],

  endpoints: (builder) => ({
    checkLogin: builder.query<{ isLoggedIn: boolean }, void>({
      query: () => "api/auth/check",
      providesTags: ["AUTH"],
      async onQueryStarted(_, { dispatch, queryFulfilled }) {
        try {
          const { data } = await queryFulfilled;
          dispatch(setAuth(data.isLoggedIn));
        } catch (error) {
          toast.error("Failed to check login status !");
        }
      },
    }),
  }),
});

export const { useCheckLoginQuery } = api;
