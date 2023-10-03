import { ContactForm, ProtectedRoute } from "@contact-hub/components";
import { useAuth } from "@contact-hub/hooks";
import { useGetContactDetailsQuery } from "@contact-hub/services";
import { Container } from "@mui/material";
import { useRouter } from "next/router";
import { useEffect } from "react";

export default function EditContactPage() {
  const { query } = useRouter();
  const contactId = query["id"] as string;

  const { data: contact } = useGetContactDetailsQuery(contactId, {
    skip: !contactId,
  });

  return (
    <ProtectedRoute>
      <ContactForm contact={contact} />
    </ProtectedRoute>
  );
}
