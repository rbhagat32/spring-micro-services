import { Button } from "@/components/ui/button";

export function LogoutButton() {
  //   const [logout, { isLoading }] = useLogoutMutation();

  return (
    <Button
      // onClick={() => logout()}
      // disabled={isLoading}
      variant="outline"
    >
      Logout
    </Button>
  );
}
