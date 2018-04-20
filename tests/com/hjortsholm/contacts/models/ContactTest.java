package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName(value = "Contact")
public class ContactTest {

    private Contact contact;

    @BeforeAll
    static void beforeAll() {
        Database.configure("data/contacts.db");
    }

    @BeforeEach
    void beforeEach() {
        this.contact = new Contact(100);
    }

    @AfterEach
    void afterAll() {
        this.contact.delete();
    }

    @Test
    @DisplayName(value = "New contacts are automatically added to database")
    void newContactIsAddedToDatabase() {
        assertTrue(this.contact.exists());
    }

    @Test
    @DisplayName(value = "Getting a field with name and type that does not exists")
    void getNullField() {
        assertTrue(this.contact.getField(FieldType.NAME, "first").isEmpty());
    }

    @Test
    @DisplayName(value = "Getting a field with name and type that does exists")
    void getField() {
        Field field = new Field(-1, this.contact.getId(), FieldType.NAME, "first", "Robert");
        field.push();
        assertFalse(this.contact.getField(FieldType.NAME, "first").isEmpty());
    }

    @Test
    @DisplayName(value = "Deleting a contact from the database")
    void deletingContactFromDatabase() {
        contact.delete();
        assertFalse(this.contact.exists());
    }

    @Test
    @DisplayName(value = "A new contact is marked as a new contact")
    void contactIsNewContact() {
        assertTrue(this.contact.isNewContact());
    }
}