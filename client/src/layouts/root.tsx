import Navbar from "@/components/core/navbar";

export function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <main className="p-4">
      <Navbar />
      <section>{children}</section>
    </main>
  );
}
