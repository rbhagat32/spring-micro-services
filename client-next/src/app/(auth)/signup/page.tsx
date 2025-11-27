import { SignupForm } from "@/components/core/signup-form";
import { Card, CardContent } from "@/components/ui/card";
import Image from "next/image";

export default function SignupPage() {
  return (
    <div className="flex flex-col gap-6">
      <Card className="overflow-hidden p-0">
        <CardContent className="grid p-0 md:grid-cols-2">
          <div className="bg-muted relative hidden md:block">
            <Image
              src="/tendou.jpeg"
              alt="Login Image"
              loading="eager"
              width={1080}
              height={1080}
              className="absolute inset-0 h-full w-full rotate-180 object-cover dark:brightness-[0.5]"
            />
          </div>

          <SignupForm />
        </CardContent>
      </Card>
    </div>
  );
}
