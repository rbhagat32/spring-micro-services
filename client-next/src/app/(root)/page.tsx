import { getUserAction } from "@/actions/query/get-user";

export default async function HomePage() {
  const user = await getUserAction();

  return (
    <div>
      <p>Id: {user!.id}</p>
      <p>Name: {user!.name}</p>
      <p>Email: {user!.email}</p>
      <p>Created at: {new Date(user!.createdAt).toLocaleString()}</p>
      <p>Updated at: {new Date(user!.updatedAt).toLocaleString()}</p>
    </div>
  );
}
