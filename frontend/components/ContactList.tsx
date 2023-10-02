import { Row } from "react-bootstrap";
import ContactCard from "./ContactCard";
import { Contact } from "@/utils/types";

type Props = {
  items: Contact[];
};

export default function ContactList({ items }: Props) {
  return (
    <Row>
      {items.map((item, index) => {
        return <ContactCard key={index} contact={item} />;
      })}
    </Row>
  );
}
