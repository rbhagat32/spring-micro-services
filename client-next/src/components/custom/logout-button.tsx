"use client";

import { Button } from "@/components/ui/button";
import { axios } from "@/lib/axios";
import { useRouter } from "next/navigation";
import { toast } from "sonner";

export function LogoutButton() {
  const router = useRouter();

  const handleLogout = async () => {
    const res = await axios.post<{ message: string }>("/api/auth/logout");

    if (res.status === 200) {
      toast.success(res.data.message);
      router.push("/login");
    }
  };

  return (
    <Button onClick={handleLogout} variant="outline">
      Logout
    </Button>
  );
}
