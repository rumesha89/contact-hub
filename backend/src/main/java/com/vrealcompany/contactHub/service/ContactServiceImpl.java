package com.vrealcompany.contactHub.service;

import com.vrealcompany.contactHub.model.Contact;
import com.vrealcompany.contactHub.model.filter.ContactFilter;
import com.vrealcompany.contactHub.repository.ContactRepository;
import com.vrealcompany.contactHub.service.client.ContactClient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ContactServiceImpl implements ContactService {

    Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    private ContactClient contactClient;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Contact> getContacts() {
        logger.info("Retrieving all contacts");
        List<Contact> contactsFromDb = contactRepository.findAll();
        List<Contact> contactsFromApi = contactClient.getAllContacts();
        return Stream.concat(contactsFromDb.stream(), contactsFromApi.stream())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Contact getContactById(Long id) {
        logger.info("Retrieving contact by id: " + id);
        Contact contact = null;
        contact = contactClient.getContactById(id);
        if(contact.getId() == null){
            Optional<Contact> optionalContact = contactRepository.findById(id);
            if(optionalContact.isEmpty()){
                throw new EntityNotFoundException();
            }
            contact = optionalContact.get();
        }

        return contact;
    }

    @Override
    public List<Contact> getContactsByFilter(ContactFilter filter) {
        logger.info("Filtering contacts with name: " + filter.getName());

        List<Contact> contactsFromApi = contactClient.getAllContacts().stream()
                .filter(contact -> filter.getName() == null || contact.getName().contains(filter.getName())).toList();
        List<Contact> contactsFromDb =  getTypedQueryByFilter(filter).getResultList();

        return Stream.concat(contactsFromDb.stream(), contactsFromApi.stream())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Contact createContact(Contact contact) throws Exception {
        if(isExistingContact(contact)) {
            logger.info("Entity already present.");
            throw new IllegalArgumentException("Contact already present in the system.");
        }
        logger.info("Creating new contact with email: " + contact.getEmail() + " and Company " + contact.getCompanyName());
        try {
            Contact contactSavedInRemote = contactClient.createContact(contact);
            Contact persistedContact = contactRepository.save(contactSavedInRemote);
            return persistedContact;
        } catch (Exception e) {
            logger.error("Error creating contact with email: " + contact.getEmail() + " and Company " + contact.getCompanyName());
            throw new Exception("Error Creating Contact", e);
        }
    }

    @Override
    public void createContacts(List<Contact> contacts) {
        contacts.forEach(contact -> {
            if(!isExistingContact(contact)) {
                contactRepository.save(contact);
            }
        });
    }

    @Override
    public Contact updateContact(Contact contact) throws Exception {
        Optional<Contact> existingContact = contactRepository.findById(contact.getId());
        if(existingContact.isEmpty())
            throw new EntityNotFoundException();

        /**
         * Assuming I can only edit few of the properties
         */
        Contact tempContact = existingContact.get();
        tempContact.setName(contact.getName());
        tempContact.setPhone(contact.getPhone());
        tempContact.setWebsite(contact.getWebsite());

        logger.info("Updating new contact with email: " + contact.getEmail() + " and Company " + contact.getCompanyName());
        try {
            Contact contactSavedInRemote = contactClient.updateContact(contact);
            Contact persistedContact = contactRepository.save(contactSavedInRemote);
            return persistedContact;
        } catch (Exception e) {
            logger.error("Error updating contact with email: " + contact.getEmail() + " and Company " + contact.getCompanyName());
            throw new Exception("Error updating Contact", e);
        }
    }

    private TypedQuery<Contact> getTypedQueryByFilter(ContactFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Contact> cq = cb.createQuery(Contact.class);
        Root<Contact> contact = cq.from(Contact.class);
        List<Predicate> predicates = new ArrayList<>();
        if (filter != null && filter.getName() != null && !filter.getName().isEmpty()) {
            predicates.add(cb.like(contact.get("name"), "%" + filter.getName() + "%"));
        }
        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Contact> query = em.createQuery(cq);

        return  query;
    }

    private boolean isExistingContact(Contact contact) {
        Optional<Contact> existingContact =
                contactRepository.findByEmailAndCompanyName(contact.getEmail(), contact.getCompanyName());

        return existingContact.isPresent();
    }
}
