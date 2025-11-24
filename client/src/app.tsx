import { ThemeProvider } from "@/context/theme-provider";
import { PageLoader } from "@/components/custom/page-loader";
import { Toaster } from "sonner";
import { Routing } from "@/lib/routing";
import { useGetLoggedInUserQuery } from "@/store/api";

export function App() {
  const { isFetching, data } = useGetLoggedInUserQuery();

  return (
    <ThemeProvider defaultTheme="dark" storageKey="ui-theme">
      <Toaster richColors position="top-center" duration={3000} />
      {isFetching ? <PageLoader fullScreen={true} /> : <Routing isLoggedIn={!!data} />}
    </ThemeProvider>
  );
}
