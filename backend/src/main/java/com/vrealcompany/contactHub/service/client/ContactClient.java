package com.vrealcompany.contactHub.service.client;

import com.vrealcompany.contactHub.model.Contact;

import java.util.List;

public interface ContactClient {
    List<Contact> getAllContacts();

    Contact getContactById(Long id);

    Contact createContact(Contact contact) throws Exception;

    Contact updateContact(Contact contact) throws Exception;
}
