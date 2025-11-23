import { ThemeProvider } from "@/context/theme-provider";
import { PageLoader } from "@/components/custom/page-loader";
import { Toaster } from "sonner";
import { Routing } from "@/lib/routing";

export function App() {
  const loading = false;
  const isLoggedIn = true;

  return (
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      {loading ? (
        <PageLoader fullScreen={true} />
      ) : (
        <>
          <Toaster richColors position="top-center" duration={5000} />
          <Routing isLoggedIn={!!isLoggedIn} />
        </>
      )}
    </ThemeProvider>
  );
}
