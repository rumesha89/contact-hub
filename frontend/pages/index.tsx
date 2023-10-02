import { Card, Container } from "react-bootstrap";
import ContactList from "@/components/ContactList";
import { Contact } from "@/utils/types";

const items: Contact[] = [
  {
    id: 1,
    name: "Rumesha",
    number: "7788488993",
    email: "tharindu@gail.com",
  },
  {
    id: 2,
    name: "Rumesha",
    number: "7788488993",
    email: "tharindu@gail.com",
  },
  {
    id: 3,
    name: "Rumesha",
    number: "7788488993",
    email: "tharindu@gail.com",
  },
  {
    id: 4,
    name: "Rumesha",
    number: "7788488993",
    email: "tharindu@gail.com",
  },
];

export default function Home() {
  return (
    <Container>
      <ContactList items={items} />
    </Container>
  );
}
