/* eslint-disable no-undef */
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import { contactData } from "@contact-hub/utils/mockTestData";
import { ContactDetails } from "..";

test("should render contact card details", () => {
  render(<ContactDetails contact={contactData} />);

  const name = screen.getByRole("heading", { name: contactData.name });
  const email = screen.getByRole("heading", { name: contactData.email });
  const company = screen.getByRole("heading", {
    name: contactData.companyName,
  });
  const phone = screen.getByRole("heading", { name: contactData.phone });
  const website = screen.getByRole("heading", { name: contactData.website });

  expect(name).toBeInTheDocument();
  expect(email).toBeInTheDocument();
  expect(company).toBeInTheDocument();
  expect(phone).toBeInTheDocument();
  expect(website).toBeInTheDocument();
});
