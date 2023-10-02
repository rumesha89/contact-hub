package com.vrealcompany.contactHub.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vrealcompany.contactHub.model.Contact;
import com.vrealcompany.contactHub.model.dto.CompanyResponse;
import com.vrealcompany.contactHub.model.dto.ContactResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ContactClientImplTest {

    @Mock
    private WebClient webClient;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private ContactClientImpl contactClient;

    @Autowired
    private ObjectMapper objectMapper;

    public static MockWebServer mockPartnerBackend;

    @BeforeAll
    static void setUp() throws IOException {
        mockPartnerBackend = new MockWebServer();
        mockPartnerBackend.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockPartnerBackend.shutdown();
    }

    @BeforeEach
    void initialize() {

        String webClientBaseUrl = String.format("http://localhost:%s", mockPartnerBackend.getPort());

        contactClient = new ContactClientImpl();
        contactClient.webClient = WebClient.builder().baseUrl(webClientBaseUrl).build();
    }

    @Test
    void whenCalledGetAllContacts_returnsAll10FromPartner() throws IOException {
        List<ContactResponse> responseList = Collections.singletonList(ContactResponse.builder()
                .id(1)
                .email("test@mail.com")
                .name("Test")
                .phone("223344555")
                .website("Test.com")
                .company(CompanyResponse.builder().name("Test Company").build()).build());
        List<Contact> expected = List.of(
                new Contact(1L, "Test", "test@mail.com", "223344555", "Test.com", "Test Company"));

        mockPartnerBackend.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(responseList))
                .addHeader("Content-Type", "application/json"));

        List<Contact> actual = contactClient.getAllContacts();

        assertEquals(expected.size(), actual.size());
        assertEquals(actual.get(0).getEmail(), "test@mail.com");
    }

    @Test
    void whenCalledGetAllContactsThrownError_returnsEmptyList() {
        mockPartnerBackend.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error")
                .addHeader("Content-Type", "text/plain"));
        List<Contact> actual = contactClient.getAllContacts();

        assertEquals(0, actual.size());
    }

    @Test
    void whenCalledGetContactById_returnsCorrectContact() throws IOException {
        ContactResponse response = ContactResponse.builder()
                .id(1)
                .email("test@mail.com")
                .name("Test")
                .phone("223344555")
                .website("Test.com")
                .company(CompanyResponse.builder().name("Test Company").build()).build();
        Contact expected = new Contact(1L, "Test", "test@mail.com", "223344555",
                "Test.com", "Test Company");

        mockPartnerBackend.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(response))
                .addHeader("Content-Type", "application/json"));

        Contact actual = contactClient.getContactById(1L);
        assertEquals(actual.getEmail(), "test@mail.com");
    }

    @Test
    void whenCalledGetContactByIdThrownError_returnsNull() {
        mockPartnerBackend.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error")
                .addHeader("Content-Type", "text/plain"));
        Contact actual = contactClient.getContactById(1L);

        assertNull(actual);
    }

    @Test
    public void whenCalledCreateContactWithPayload_createContactSuccessfully() throws Exception {
        ContactResponse payload = ContactResponse.builder()
                .email("test@mail.com")
                .name("Test")
                .phone("223344555")
                .website("Test.com")
                .company(CompanyResponse.builder().name("Test Company").build()).build();
        mockPartnerBackend.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(payload))
                .addHeader("Content-Type", "application/json"));
        Contact expected = new Contact(1L, "Test", "test@mail.com", "223344555",
                "Test.com", "Test Company");

        Contact createdContact = contactClient.createContact(expected);
        assertEquals("test@mail.com", createdContact.getEmail());
    }

    @Test
    public void whenCalledCreateContactThrowError_throwsError() throws Exception {
        mockPartnerBackend.enqueue(new MockResponse().setResponseCode(500));
        Contact contact = new Contact(null, "Test", "test@mail.com", "223344555",
                "Test.com", "Test Company");

        assertThrows(Exception.class, () -> {
            contactClient.createContact(contact);
        });
    }

    @Test
    public void whenCalledUpdateContactWithPayload_updateContactSuccessfully() throws Exception {
        ContactResponse payload = ContactResponse.builder()
                .id(1)
                .email("test@mail.com")
                .name("Test")
                .phone("223344555")
                .website("Test.com")
                .company(CompanyResponse.builder().name("Test Company").build()).build();
        mockPartnerBackend.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(payload))
                .addHeader("Content-Type", "application/json"));
        Contact expected = new Contact(1L, "Test", "test@mail.com", "223344555",
                "Test.com", "Test Company");

        Contact updatedContact = contactClient.updateContact(expected);
        assertEquals("test@mail.com", updatedContact.getEmail());
    }

    @Test
    public void whenCalledUpdateContactThrowError_throwsError() throws Exception {
        mockPartnerBackend.enqueue(new MockResponse().setResponseCode(500));
        Contact contact = new Contact(1L, "Test", "test@mail.com", "223344555",
                "Test.com", "Test Company");

        assertThrows(Exception.class, () -> {
            contactClient.updateContact(contact);
        });
    }
}
