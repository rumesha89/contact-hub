/* eslint-disable no-undef */
import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import { ContactList } from "..";
import { contactListData } from "@contact-hub/utils/mockTestData";

jest.mock("next/router", () => ({
  useRouter: jest.fn(),
}));

test("it should render ContactList and handle button click", () => {
  const push = jest.fn();
  require("next/router").useRouter.mockReturnValue({ push });

  render(<ContactList items={contactListData} />);

  const createNewButton = screen.getByText("Create New");

  fireEvent.click(createNewButton);

  expect(push).toHaveBeenCalledWith("contact/");
});
