package com.vrealcompany.contactHub.event;

import com.vrealcompany.contactHub.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ContactEventListener {
//
//    @Autowired
//    private ContactClient contactClient;

    @Autowired
    private ContactService contactService;

//    @Async
//    @EventListener
//    public void handleContactsCreatedEvent(ContactCreatedEvent event) {
////        contactClient.createContact(event.contact);
//    }
//
//    @Async
//    @EventListener
//    public void handleContactUpdatedEvent(ContactUpdatedEvent event) {
////        contactClient.updateContact(event.contact);
//    }

    @Async
    @EventListener
    public void handleContactsReceivedEvent(ContactReceivedEvent event) {
        contactService.createContacts(event.contactsReceived);
    }
}
