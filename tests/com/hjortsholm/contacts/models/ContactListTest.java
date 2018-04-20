package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.ContactList;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName(value = "ContactList")
public class ContactListTest {

    private ContactList contacts;

    @BeforeAll
    static void beforeAll() {
        Database.configure("data/contacts.db");
    }

    @AfterAll
    static void afterAll() {
        Contact contact = new Contact(100);
        contact.delete();
    }

    @BeforeEach
    void beforeEach() {
        this.contacts = new ContactList();
    }

    @Test
    @DisplayName(value = "All contacts exists")
    void allContactsExists() {
        assertAll(() -> this.contacts.getContacts().forEach(contact -> assertTrue(contact.exists())));
    }

    @Test
    @DisplayName(value = "Adding a new contact")
    void addContact() {
        Contact contact = new Contact(100);
        this.contacts.addContact(contact);
        assertTrue(this.contacts.contactExists(contact));
    }

    @Test
    @DisplayName(value = "Removing a new contact")
    void removeContact() {
        Contact contact = new Contact(100);
        this.contacts.addContact(contact);
        this.contacts.remove(contact);
        assertFalse(this.contacts.getContacts().contains(contact));
    }

    @Test
    @DisplayName(value = "Get contacts with given values")
    void getContactsWithValues() {
        Contact contact = new Contact(100);
        this.contacts.addContact(contact);
        Field field = new Field(-1, contact.getId(), FieldType.NAME, "first", "¿?");
        field.push();

        assertTrue(this.contacts.getContactsWith("¿?").size() == 1);
        assertTrue(((Contact) this.contacts.getContactsWith("¿?").toArray()[0]).getId() == contact.getId());
    }
}