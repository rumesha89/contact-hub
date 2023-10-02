import ContactList from "@/components/ContactList";
import { Contact } from "@/utils/types";
import { Container } from "@mui/material";

const items: Contact[] = [
  {
    id: 1,
    name: "Rumesha",
    number: "7788488993",
    email: "Rumesha@gail.com",
  },
  {
    id: 2,
    name: "Rumesha",
    number: "7788488993",
    email: "Rumesha@gail.com",
  },
  {
    id: 3,
    name: "Rumesha",
    number: "7788488993",
    email: "Rumesha@gail.com",
  },
  {
    id: 4,
    name: "Rumesha",
    number: "7788488993",
    email: "Rumesha@gail.com",
  },
];

export default function Home() {
  return (
    <Container>
      <h2>Contact Hub</h2>
      <ContactList items={items} />
    </Container>
  );
}
