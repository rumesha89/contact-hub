import { Contact } from "@/utils/types";
import { Stack, Row, Col } from "react-bootstrap";

type Props = {
  contact: Contact;
};

export default function ContactCard({ contact }: Props) {
  return (
    <Row>
      <Col>{contact.name}</Col>
      <Col>{contact.email}</Col>
      <Col>{contact.number}</Col>
    </Row>
  );
}
