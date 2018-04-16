package com.hjortsholm.contacts;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * <h1>Setup</h1>
 * Sets up database with sample data.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @see Database
 * @see Field
 * @see Contact
 * @since 10/04/2018
 */
public class Setup {

    /**
     * Deletes and recreates tables with sample data in database.
     *
     * @param args Program arguments.
     * @throws IOException Database file not found.
     */
    public static void main(String[] args) throws IOException {
        Database.configure("contacts.db");
        Setup setup = new Setup();
        setup.flushDatabase();
        setup.createDatabase();
        setup.importSampleData();
    }

    /**
     * Imports sample data to database.
     *
     * @throws IOException Sample data file not found.
     */
    private void importSampleData() throws IOException {
        File sampleDataCsv = new File("fields.csv");
        Scanner scanner = new Scanner(sampleDataCsv);
        scanner.nextLine();
        while (scanner.hasNext()) {
            Object[] data = scanner.nextLine().split(";");
            int id = Integer.parseInt(data[0].toString());
            int contactId = Integer.parseInt(data[1].toString());
            FieldType fieldType = FieldType.valueOf(Integer.parseInt(data[2].toString()));
            String name = data[3].toString();
            String value = data[4].toString();

            Contact contact = new Contact(contactId);
            Field field = new Field(id, contactId, fieldType, name, value);

            if (!contact.exists()) {
                Database.insert(contact);
            }
            Database.insert(field);
        }
    }

    /**
     * Deletes all tables in database.
     */
    private void flushDatabase() {
        Database.dropTable(Field.class);
        Database.dropTable(Contact.class);
    }

    /**
     * Creates table in database.
     */
    private void createDatabase() {
        Database.createTable(Contact.class);
        Database.createTable(Field.class);
    }
}
