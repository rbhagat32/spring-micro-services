import { ThemeProvider } from "@/context/theme-provider";
import { ThemeSwitcher } from "@/components/custom/theme-switcher";

export function App() {
  return (
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <div>
        <ThemeSwitcher />
      </div>
    </ThemeProvider>
  );
}
