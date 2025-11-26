"use client";

import { loginAction } from "@/actions/login";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useActionState, useEffect, useState, useTransition } from "react";
import { toast } from "sonner";

export function LoginForm() {
  const router = useRouter();
  const [_isPending, startTransition] = useTransition();

  const [state, formAction] = useActionState(loginAction, null);
  const [loading, setLoading] = useState(false);

  function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    setLoading(true);

    const formData = new FormData(e.currentTarget);

    startTransition(() => {
      formAction(formData);
    });
  }

  useEffect(() => {
    if (!state) return;

    // zod validation errors
    if (state.zodErrors) {
      Object.values(state.zodErrors).forEach((errors) => {
        if (errors) {
          errors.forEach((err) => toast.error(err));
        }
      });
      setLoading(false);
      return;
    }

    // server errors
    if (state.success === false && state.message) {
      toast.error(state.message);
      setLoading(false);
      return;
    }

    // success
    if (state.success) {
      toast.success("Login successful!");
      router.push("/");
    }

    setLoading(false);
  }, [state]);

  return (
    <form onSubmit={handleSubmit} className="p-6 md:p-8">
      <div className="flex flex-col gap-6">
        <div className="flex flex-col items-center text-center">
          <h1 className="text-2xl font-bold">Welcome</h1>
          <p className="text-muted-foreground text-balance">
            Login to your <span className="underline">Spring Micro-Services</span> account.
          </p>
        </div>

        <div className="grid gap-3">
          <Label htmlFor="email">Email</Label>
          <Input id="email" name="email" />
        </div>

        <div className="grid gap-3">
          <div className="flex items-center">
            <Label htmlFor="password">Password</Label>
            <Link
              href="/forgot-password"
              className="ml-auto text-sm underline-offset-2 hover:underline"
            >
              Forgot your password?
            </Link>
          </div>
          <Input id="password" name="password" type="password" />
        </div>

        <Button type="submit" className="w-full" disabled={loading}>
          Login
        </Button>

        <div className="text-center text-sm">
          Don&apos;t have an account?{" "}
          <Link href="/signup" className="underline underline-offset-4">
            Sign up
          </Link>
        </div>
      </div>
    </form>
  );
}
