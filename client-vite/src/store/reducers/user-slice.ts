import { type UserDTO } from "@/types/types";
import { createSlice, type PayloadAction } from "@reduxjs/toolkit";

const initialState: UserDTO | {} = {};

const UserSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    setUser: (_prevState, action: PayloadAction<UserDTO>) => {
      return action.payload;
    },
    clearUser: () => {
      return initialState;
    },
  },
});

export { UserSlice };
export const { setUser, clearUser } = UserSlice.actions;
