import { PageLoader } from "@/components/custom/page-loader";
import { ThemeProvider } from "@/context/theme-provider";
import { Routing } from "@/lib/routing";
import { useGetLoggedInUserQuery } from "@/store/api";
import { type IStore } from "@/types/types";
import { useSelector } from "react-redux";
import { Toaster } from "sonner";

export function App() {
  const { isLoading } = useGetLoggedInUserQuery();
  const user = useSelector((state: IStore) => state.user);

  return (
    <ThemeProvider>
      <Toaster richColors position="top-center" duration={3000} />
      {isLoading ? <PageLoader fullScreen={true} /> : <Routing isLoggedIn={!!user.id} />}
    </ThemeProvider>
  );
}
