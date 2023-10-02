package com.vrealcompany.contactHub.service;

import com.vrealcompany.contactHub.model.Contact;
import com.vrealcompany.contactHub.model.filter.ContactFilter;

import java.util.List;

public interface ContactService {
    List<Contact> getContacts();

    Contact getContactById(Long id);

    List<Contact> getContactsByFilter(ContactFilter filter);

    Contact createContact(Contact contact) throws Exception;

    void createContacts(List<Contact> contacts);

    Contact updateContact(Contact contact) throws Exception;
}
