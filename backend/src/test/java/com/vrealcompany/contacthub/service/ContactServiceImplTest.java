package com.vrealcompany.contacthub.service;

import com.vrealcompany.contacthub.model.Contact;
import com.vrealcompany.contacthub.repository.ContactRepository;
import com.vrealcompany.contacthub.service.client.ContactClientImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ContactServiceImplTest {
    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactClientImpl contactClientImpl;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ContactServiceImpl contactService;

    @Test
    public void whenCalledGetContacts_returnAllGetContactsCombined() {
        List<Contact> contactsFromApi = new ArrayList<>();
        contactsFromApi.add(new Contact(null, "", "Sincere",
                "1-770-736-8031 x56442", "hildegard.org", "Romaguera-Crona"));
        contactsFromApi.add(new Contact(2L, "Jane Smith", "jane@example.com",
                "1-770-736-8031 x56442", "hildegard.org", "Romaguera-Crona"));

        List<Contact> contactsFromDb = new ArrayList<>();
        contactsFromDb.add(new Contact(3L, "Alice Johnson", "alice@example.com",
                "1-770-736-8031 x56442", "hildegard.org", "Romaguera-Crona"));

        when(contactClientImpl.getAllContacts()).thenReturn(contactsFromApi);
        when(contactRepository.findAll()).thenReturn(contactsFromDb);

        List<Contact> resultContacts = contactService.getContacts();

        assertEquals(3, resultContacts.size());
        assertEquals(contactsFromApi.get(0), resultContacts.get(1));
        assertEquals(contactsFromApi.get(1), resultContacts.get(2));
        assertEquals(contactsFromDb.get(0), resultContacts.get(0));
    }

    @Test
    public void whenNoContactsInDatabase_returnAllGetContactsFromApi() {
        List<Contact> contactsFromApi = new ArrayList<>();
        contactsFromApi.add(new Contact(null, "", "Sincere",
                "1-770-736-8031 x56442", "hildegard.org", "Romaguera-Crona"));
        contactsFromApi.add(new Contact(2L, "Jane Smith", "jane@example.com",
                "1-770-736-8031 x56442", "hildegard.org", "Romaguera-Crona"));

        List<Contact> contactsFromDb = new ArrayList<>();

        when(contactClientImpl.getAllContacts()).thenReturn(contactsFromApi);
        when(contactRepository.findAll()).thenReturn(contactsFromDb);

        List<Contact> resultContacts = contactService.getContacts();

        assertEquals(2, resultContacts.size());
    }

    @Test
    public void whenCalledGetById_returnsCorrectEntity() {
        Contact contactFormApi = new Contact(1L, "", "Sincere@gmail",
                "1-770-736-8031 x56442", "hildegard.org", "Romaguera-Crona");
        when(contactClientImpl.getContactById(1L)).thenReturn(contactFormApi);

        Contact resultContact = contactService.getContactById(1L);

        assertEquals("Sincere@gmail", resultContact.getEmail());
    }

    @Test
    public void testWhenCalledGetById_returnsCorrectCachedEntity() {
        Contact contactFormDB = new Contact(1L, "", "Sincere@gmail",
                "1-770-736-8031 x56442", "hildegard.org", "Romaguera-Crona");
        when(contactClientImpl.getContactById(1L)).thenReturn(null);
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contactFormDB));

        Contact resultContact = contactService.getContactById(1L);

        assertEquals("Sincere@gmail", resultContact.getEmail());
    }

    @Test
    public void testWhenCalledGetByIdWithWrongId_throwsNotFoundException() {
        Long nonExistentId = 999L;
        when(contactClientImpl.getContactById(nonExistentId)).thenReturn(null);
        when(contactRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> contactService.getContactById(nonExistentId));
    }
}
