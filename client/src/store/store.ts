import { configureStore } from "@reduxjs/toolkit";
import { api } from "@/store/api";
import { AuthSlice } from "@/store/reducers/auth-slice";

export const store = configureStore({
  reducer: {
    [api.reducerPath]: api.reducer,
    [AuthSlice.name]: AuthSlice.reducer,
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(api.middleware),
});
