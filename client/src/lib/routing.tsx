import { Route, Routes } from "react-router-dom";
import { ProtectedRoute } from "@/lib/protected-route";
import { AuthLayout } from "@/layouts/auth";
import { RootLayout } from "@/layouts/root";
import { NotFoundPage } from "@/pages/not-found";

const Routing = ({ isLoggedIn = false }: { isLoggedIn: boolean }) => {
  return (
    <Routes>
      <Route element={<ProtectedRoute isLoggedIn={isLoggedIn} redirect="/login" />}>
        <Route
          path="/"
          element={
            <RootLayout>
              <div>Home Page</div>
            </RootLayout>
          }
        />
      </Route>

      <Route element={<ProtectedRoute isLoggedIn={!isLoggedIn} redirect="/" />}>
        <Route
          path="/login"
          element={
            <AuthLayout>
              <div>Login Page</div>
            </AuthLayout>
          }
        />
        <Route
          path="/signup"
          element={
            <AuthLayout>
              <div>Signup Page</div>
            </AuthLayout>
          }
        />
      </Route>

      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  );
};

export { Routing };
