import axios from "axios";

const SERVER_URL = process.env.BACKEND_URL;

const instance = axios.create({
  baseURL: SERVER_URL as string,
  withCredentials: true,
});

export { instance as axios };
