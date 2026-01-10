import { createFileRoute, Outlet, redirect } from "@tanstack/react-router";

import { Navbar } from "@/components/core/navbar";

export const Route = createFileRoute("/_protected")({
  beforeLoad: ({ context }) => {
    const { isLoggedIn } = context;

    if (isLoggedIn === undefined) return;
    if (!isLoggedIn) throw redirect({ to: "/login" });
  },
  component: ProtectedLayout,
});

function ProtectedLayout() {
  return (
    <main className="container mx-auto p-4">
      <Navbar />
      <Outlet />
    </main>
  );
}
