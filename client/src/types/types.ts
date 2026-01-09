export interface IRouterContext {
  isLoggedIn: boolean;
}

export interface IStore {
  user: UserDTO;
}

export interface LoginFormData {
  email: string;
  password: string;
}

export interface SignupFormData {
  name: string;
  email: string;
  password: string;
}

export interface UserDTO {
  id: string;
  name: string;
  email: string;
  createdAt: string;
  updatedAt: string;
}
