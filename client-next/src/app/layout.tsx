import "@/app/globals.css";
import type { Metadata } from "next";
import { Toaster } from "sonner";

export const metadata: Metadata = {
  title: "Spring Micro-Services",
  description: "A micro-services project using Spring Boot.",
};

export default function Layout({ children }: Readonly<{ children: React.ReactNode }>) {
  return (
    <>
      <html lang="en">
        <body className="antialiased">{children}</body>
      </html>
      <Toaster richColors position="top-center" />
    </>
  );
}
