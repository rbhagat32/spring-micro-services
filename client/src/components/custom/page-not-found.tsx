export function PageNotFound() {
  return (
    <div className="flex h-screen w-screen items-center justify-center">
      <div className="flex items-center gap-4">
        <h1 className="text-2xl font-semibold">404</h1>
        <div className="mt-1 h-8 border border-zinc-500"></div>
        <p className="mt-0.5 font-light">Page Not Found</p>
      </div>
    </div>
  );
}
