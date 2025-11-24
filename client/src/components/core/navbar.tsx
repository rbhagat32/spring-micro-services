import { ThemeSwitcher } from "@/components/custom/theme-switcher";
import { LogoutButton } from "@/components/custom/logout-button";

export function Navbar() {
  return (
    <nav className="mb-4 flex items-center justify-between gap-4">
      <img src="/vite.svg" alt="Logo" />

      <div className="flex items-center gap-4">
        <ThemeSwitcher />
        <LogoutButton />
      </div>
    </nav>
  );
}
