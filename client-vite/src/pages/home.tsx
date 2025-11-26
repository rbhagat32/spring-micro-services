import { type IStore } from "@/types/types";
import { useSelector } from "react-redux";

export function HomePage() {
  const user = useSelector((state: IStore) => state.user);

  return (
    <div>
      <p>Id: {user.id}</p>
      <p>Name: {user.name}</p>
      <p>Email: {user.email}</p>
      <p>Created at: {new Date(user.createdAt).toLocaleString()}</p>
      <p>Updated at: {new Date(user.updatedAt).toLocaleString()}</p>
    </div>
  );
}
