package com.vrealcompany.contactHub.service;

import com.vrealcompany.contactHub.model.Contact;
import com.vrealcompany.contactHub.model.filter.ContactFilter;

import java.util.List;

public interface ContactService {
    public List<Contact> getContacts();

    public Contact getContactById(Long id);

    public List<Contact> getContactsByFilter(ContactFilter filter);

    public Contact createContact(Contact contact) throws Exception;

    public void createContacts(List<Contact> contacts);

    public Contact updateContact(Contact contact) throws Exception;
}
