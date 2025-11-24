import { AuthLayout } from "@/layouts/auth";
import { RootLayout } from "@/layouts/root";
import { ProtectedRoute } from "@/lib/protected-route";
import { HomePage } from "@/pages/home";
import { LoginPage } from "@/pages/login";
import { NotFoundPage } from "@/pages/not-found";
import { SignupPage } from "@/pages/signup";
import { Route, Routes } from "react-router-dom";

export function Routing({ isLoggedIn }: { isLoggedIn: boolean }) {
  return (
    <Routes>
      <Route element={<ProtectedRoute isLoggedIn={isLoggedIn} redirect="/login" />}>
        <Route
          path="/"
          element={
            <RootLayout>
              <HomePage />
            </RootLayout>
          }
        />
      </Route>

      <Route element={<ProtectedRoute isLoggedIn={!isLoggedIn} redirect="/" />}>
        <Route
          path="/login"
          element={
            <AuthLayout>
              <LoginPage />
            </AuthLayout>
          }
        />
        <Route
          path="/signup"
          element={
            <AuthLayout>
              <SignupPage />
            </AuthLayout>
          }
        />
      </Route>

      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  );
}
