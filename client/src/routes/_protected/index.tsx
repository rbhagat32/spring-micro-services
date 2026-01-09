import { createFileRoute } from "@tanstack/react-router";
import { useSelector } from "react-redux";

import { type IStore } from "@/types/types";

export const Route = createFileRoute("/_protected/")({
  component: HomePage,
});

function HomePage() {
  const user = useSelector((state: IStore) => state.user);

  return (
    <section>
      <p>Id: {user.id}</p>
      <p>Name: {user.name}</p>
      <p>Email: {user.email}</p>
      <p>Created at: {new Date(user.createdAt).toLocaleString()}</p>
      <p>Updated at: {new Date(user.updatedAt).toLocaleString()}</p>
    </section>
  );
}
