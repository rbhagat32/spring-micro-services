import axios from "axios";

const SERVER_URL =
  typeof window === "undefined" ? process.env.BACKEND_URL : process.env.NEXT_PUBLIC_BACKEND_URL;

const instance = axios.create({
  baseURL: SERVER_URL as string,
  withCredentials: true,
});

export { instance as axios };
