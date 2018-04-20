package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName(value = "Field")
public class FieldTest {

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
    void afterEach() {
        this.contact.delete();
    }

    @Test
    @DisplayName(value = "Empty field is empty")
    void isEmpty() {
        Field field = new Field(-1, FieldType.NAME, "first");
        assertTrue(field.isEmpty());
    }

    @Test
    @DisplayName(value = "New fields exists in database")
    void newFieldExists() {
        Field field = new Field(-1, this.contact.getId(), FieldType.NAME, "first", "Robert");
        field.push();
        assertTrue(field.exists());
    }

    @Test
    @DisplayName(value = "New fields exists in database")
    void fieldChangeSaved() {
        Field field = new Field(-1, this.contact.getId(), FieldType.NAME, "first", "Robert");
        field.push();
        field.setValue("Rob");
        assertTrue(this.contact.getFirstName().getValue().equals(field.getValue()));
    }
}