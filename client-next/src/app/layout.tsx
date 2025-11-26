import "@/app/globals.css";
import { ThemeProvider } from "@/context/theme-provider";
import { type Metadata } from "next";
import { Toaster } from "sonner";

export const metadata: Metadata = {
  title: "Spring Micro-Services",
  description: "A micro-services project using Spring Boot.",
};

export default function Layout({ children }: Readonly<{ children: React.ReactNode }>) {
  return (
    <>
      <html lang="en" suppressHydrationWarning>
        {/* <head /> */}
        <body className="antialiased">
          <ThemeProvider
            attribute="class"
            defaultTheme="system"
            enableSystem
            disableTransitionOnChange
          >
            {children}
          </ThemeProvider>
        </body>
      </html>
      <Toaster richColors position="top-center" />
    </>
  );
}
