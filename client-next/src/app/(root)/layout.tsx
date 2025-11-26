import { Navbar } from "@/components/core/navbar";

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <main className="container mx-auto p-4">
      <Navbar />
      <section>{children}</section>
    </main>
  );
}
