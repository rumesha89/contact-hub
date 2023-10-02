import { Contact } from "@/utils/types";
import { Grid } from "@mui/material";

type Props = {
  contact: Contact;
};

export default function ContactCard({ contact }: Props) {
  return (
    <Grid container>
      <Grid item>{contact.name}</Grid>
      <Grid item>{contact.email}</Grid>
      <Grid item>{contact.number}</Grid>
    </Grid>
  );
}
