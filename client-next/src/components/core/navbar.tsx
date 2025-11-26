import { LogoutButton } from "@/components/custom/logout-button";
import { ThemeSwitcher } from "@/components/custom/theme-switcher";
import Image from "next/image";

export function Navbar() {
  return (
    <nav className="mb-4 flex items-center justify-between gap-4">
      <Image src="/vercel.svg" alt="Logo" width={32} height={32} />

      <div className="flex items-center gap-4">
        <ThemeSwitcher />
        <LogoutButton />
      </div>
    </nav>
  );
}
