import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "sonner";
import { useForm, type SubmitHandler } from "react-hook-form";
import { useEffect, useState } from "react";
import { api } from "@/lib/axios";

interface LoginFormData {
  email: string;
  password: string;
}

export function LoginPage() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState<boolean>(false);

  const {
    handleSubmit,
    register,
    reset,
    trigger,
    formState: { errors },
  } = useForm<LoginFormData>();

  const handleLogin: SubmitHandler<LoginFormData> = async (data) => {
    setLoading(true);
    try {
      const res = await api.post<{ message: string }>("/api/auth/login", data);
      reset();
      // dispatch(setAuth(true));
      navigate("/");
      toast.success(res.data.message);
    } catch (error: any) {
      toast.error(error.response?.data?.message || "Failed to Log In !");
      console.error("Failed to Login:", error);
    } finally {
      setLoading(false);
    }
  };

  const validation = async (data: LoginFormData) => {
    const isValid = await trigger();
    if (!isValid) return;
    handleLogin(data);
  };

  useEffect(() => {
    if (errors.email) {
      toast.error(errors.email.message);
    } else if (errors.password) {
      toast.error(errors.password.message);
    }
  }, [errors]);

  return (
    <div className="flex flex-col gap-6">
      <Card className="overflow-hidden p-0">
        <CardContent className="grid p-0 md:grid-cols-2">
          <form
            className="p-6 md:p-8"
            onSubmit={handleSubmit(validation)}
            onKeyDown={(e) => {
              if (e.key === "Enter") handleSubmit(validation);
            }}
          >
            <div className="flex flex-col gap-6">
              <div className="flex flex-col items-center text-center">
                <h1 className="text-2xl font-bold">Welcome</h1>
                <p className="text-muted-foreground text-balance">
                  Login to your <span className="underline">Spring Micro-Services</span> account.
                </p>
              </div>
              <div className="grid gap-3">
                <Label htmlFor="email">Email</Label>
                <Input id="email" {...register("email")} type="email" required />
              </div>
              <div className="grid gap-3">
                <div className="flex items-center">
                  <Label htmlFor="password">Password</Label>
                  <Link
                    to="/forgot-password"
                    className="ml-auto text-sm underline-offset-2 hover:underline"
                  >
                    Forgot your password?
                  </Link>
                </div>
                <Input id="password" {...register("password")} type="password" required />
              </div>
              <Button type="submit" className="w-full" disabled={loading}>
                Login
              </Button>

              <div className="text-center text-sm">
                Don&apos;t have an account?{" "}
                <Link to="/signup" className="underline underline-offset-4">
                  Sign up
                </Link>
              </div>
            </div>
          </form>

          <div className="bg-muted relative hidden md:block">
            <img
              src="/tendou.jpeg"
              alt="Login Image"
              loading="eager"
              className="absolute inset-0 h-full w-full object-cover dark:brightness-[0.5]"
            />
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
