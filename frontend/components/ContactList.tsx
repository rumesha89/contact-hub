import { Box } from "@mui/material";
import ContactCard from "./ContactCard";
import { Contact } from "@/utils/types";

type Props = {
  items: Contact[];
};

export default function ContactList({ items }: Props) {
  return (
    <Box>
      {items.map((item, index) => {
        return <ContactCard key={index} contact={item} />;
      })}
    </Box>
  );
}
