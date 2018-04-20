package com.hjortsholm.contacts.tests;

import com.hjortsholm.contacts.database.Query;
import com.hjortsholm.contacts.models.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName(value = "Query")
public class QueryTest {

    private Query query;

    @BeforeEach
    void setUp() {
        this.query = new Query();
    }

    @Test
    @DisplayName(value = "Select everything")
    void selectAll() {
        this.query.select();
        assertEquals("SELECT * ", this.query.toString());
    }

    @Test
    @DisplayName(value = "Select fields")
    void selectSpecific() {
        this.query.select("id, name");
        assertEquals("SELECT id, name ", this.query.toString());
    }

    @Test
    @DisplayName(value = "From table")
    void fromTable() {
        this.query.from("foo");
        assertEquals("FROM foo ", this.query.toString());
    }

    @Test
    @DisplayName(value = "From class table")
    void fromClassTable() {
        this.query.from(Contact.class);
        assertEquals("FROM Contact ", this.query.toString());
    }

    @Test
    @DisplayName(value = "Where condition")
    void where() {
        this.query.where("id = 1");
        assertEquals("WHERE id = 1 ", this.query.toString());
    }

    @Test
    @DisplayName(value = "Where multiple conditions")
    void whereMultipleConditions() {
        this.query.where("id = 1", "name = 2");
        assertEquals("WHERE id = 1 AND name = 2 ", this.query.toString());
    }

    @Test
    @DisplayName(value = "And condition")
    void and() {
        this.query.and("name = 2");
        assertEquals("AND name = 2 ", this.query.toString());
    }

    @Test
    @DisplayName(value = "Drop table")
    void deleteTable() {
        this.query.drop("foo");
        assertEquals("DROP TABLE foo", this.query.toString());
    }

    @Test
    @DisplayName(value = "Drop class table")
    void deleteClass() {
        this.query.drop(Contact.class);
        assertEquals("DROP TABLE Contact", this.query.toString());
    }

    @Test
    @DisplayName(value = "Get table info")
    void getTableInfo() {
        this.query.getInfo("foo");
        assertEquals("PRAGMA table_info(foo)", this.query.toString());
    }

    @Test
    @DisplayName(value = "Get class table info")
    void getClassTableInfo() {
        this.query.getInfo(Contact.class);
        assertEquals("PRAGMA table_info(Contact)", this.query.toString());
    }

    @Test
    @DisplayName(value = "Select fields from a table where condition is true")
    void selectFieldsFromTableWhere() {
        this.query.select("id").from(Contact.class).where("id > -1");
        assertEquals("SELECT id FROM Contact WHERE id > -1 ", this.query.toString());
    }
}