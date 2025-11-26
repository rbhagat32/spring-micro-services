import "@/app/globals.css";
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Spring Micro-Services",
  description: "A micro-services project using Spring Boot.",
};

export default function RootLayout({ children }: Readonly<{ children: React.ReactNode }>) {
  return (
    <html lang="en">
      <body className="antialiased">{children}</body>
    </html>
  );
}
