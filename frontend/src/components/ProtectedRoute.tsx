import { useAuth } from "@contact-hub/hooks";
import { Container } from "@mui/material";
import { useRouter } from "next/router";
import { useEffect } from "react";

type Props = {
  children: any;
};

export default function ProtectedRoute({ children }: Props) {
  const { isAuthenticated } = useAuth();
  const { replace } = useRouter();

  useEffect(() => {
    if (!isAuthenticated) {
      replace("/");
    }
  }, [isAuthenticated]);

  if (!isAuthenticated) {
    return <></>;
  }

  return <Container maxWidth="lg">{children}</Container>;
}
