import { PageLoader } from "@/components/custom/page-loader";
import { ThemeProvider } from "@/context/theme-provider";
import { Routing } from "@/lib/routing";
import { useGetLoggedInUserQuery } from "@/store/api";
import { Toaster } from "sonner";

export function App() {
  const { isFetching, data } = useGetLoggedInUserQuery();

  return (
    <ThemeProvider>
      <Toaster richColors position="top-center" duration={3000} />
      {isFetching ? <PageLoader fullScreen={true} /> : <Routing isLoggedIn={!!data} />}
    </ThemeProvider>
  );
}
