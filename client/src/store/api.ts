import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { toast } from "sonner";
import { setUser } from "@/store/reducers/user-slice";
import type { UserDTO } from "@/types/types";

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
        } catch (error) {
          toast.error("Failed to fetch logged in user !");
        }
      },
    }),
  }),
});

export const { useGetLoggedInUserQuery } = api;
