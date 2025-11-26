import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { signupSchema } from "@/schemas/signup";
import { useSignupMutation } from "@/store/api";
import { type SignupFormData } from "@/types/types";
import { zodResolver } from "@hookform/resolvers/zod";
import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { Link } from "react-router-dom";
import { toast } from "sonner";

export function SignupPage() {
  const [signup, { isLoading }] = useSignupMutation();

  const {
    handleSubmit,
    register,
    reset,
    trigger,
    formState: { errors },
  } = useForm<SignupFormData>({
    resolver: zodResolver(signupSchema),
    mode: "onSubmit",
  });

  const onSubmit = async (formData: SignupFormData) => {
    const valid = await trigger();
    if (!valid) return;

    const res = await signup(formData);
    if (res.data) reset();
  };

  useEffect(() => {
    if (errors.name) toast.error(errors.name.message);
    else if (errors.email) toast.error(errors.email.message);
    else if (errors.password) toast.error(errors.password.message);
  }, [errors]);

  return (
    <div className="flex flex-col gap-6">
      <Card className="overflow-hidden p-0">
        <CardContent className="grid p-0 md:grid-cols-2">
          <div className="bg-muted relative hidden md:block">
            <img
              src="/tendou.jpeg"
              alt="Image"
              className="absolute inset-0 h-full w-full rotate-180 object-cover dark:brightness-[0.5]"
            />
          </div>

          <form className="p-6 md:p-8" onSubmit={handleSubmit(onSubmit)}>
            <div className="flex flex-col gap-6">
              <div className="flex flex-col items-center text-center">
                <h1 className="text-2xl font-bold">Welcome</h1>
                <p className="text-muted-foreground text-balance">
                  Create a new <span className="underline">Spring Micro-Services</span> account
                </p>
              </div>

              <div className="grid gap-3">
                <Label htmlFor="name">Name</Label>
                <Input id="name" {...register("name")} />
              </div>

              <div className="grid gap-3">
                <Label htmlFor="email">Email</Label>
                <Input id="email" {...register("email")} />
              </div>

              <div className="grid gap-3">
                <Label htmlFor="password">Password</Label>
                <Input id="password" {...register("password")} type="password" />
              </div>

              <Button type="submit" className="w-full" disabled={isLoading}>
                Sign up
              </Button>

              <div className="text-center text-sm">
                Already have an account?{" "}
                <Link to="/login" className="underline underline-offset-4">
                  Log in
                </Link>
              </div>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
