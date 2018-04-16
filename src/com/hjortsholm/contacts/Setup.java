package com.hjortsholm.contacts;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Setup {

    public void importSampleData() throws IOException {
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

    public static void main(String[] args) throws IOException{
        new Setup().importSampleData();
    }
}
