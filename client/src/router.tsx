import { createRouter, RouterProvider } from "@tanstack/react-router";
import { useEffect } from "react";
import { useSelector } from "react-redux";

import { PageLoader } from "@/components/custom/page-loader";
import { routeTree } from "@/routeTree.gen";
import { useGetLoggedInUserQuery } from "@/store/api";
import type { IRouterContext, IStore } from "@/types/types";

export const router = createRouter({
  routeTree,
  context: {
    isLoggedIn: undefined!,
  } satisfies IRouterContext,
});

declare module "@tanstack/react-router" {
  interface Register {
    router: typeof router;
  }
}

export function Router() {
  const { isLoading } = useGetLoggedInUserQuery();
  const user = useSelector((state: IStore) => state.user);

  useEffect(() => {
    router.invalidate();
  }, [!!user.id]);

  return isLoading ? (
    <PageLoader fullScreen />
  ) : (
    <RouterProvider router={router} context={{ isLoggedIn: !!user.id }} />
  );
}
