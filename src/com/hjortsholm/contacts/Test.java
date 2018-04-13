package com.hjortsholm.contacts;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.database.Query;
import com.hjortsholm.contacts.database.QueryRow;
import com.hjortsholm.contacts.database.QuerySet;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws InterruptedException {
//        database(db);
//        BufferedImage image = new HexImage().fromHex("68656c6c6f");
//        Application.setDatabase(new Database("contacts.db"));
//        Application.checkDatabaseIntegrity();

//        Database db = new Database("contacts.db");
//        CompletableFuture<QuerySet> f = db.getFuture(Query.NAME);
//
//        System.out.println("bar");
//        f.thenAcceptAsync(res -> {
//            for (QueryRow row : res) {
//                System.out.println("Row: " + row.toString());
//            }
//        });

//        db.verifyTable(FieldType.class);
//        /*Future<Field> foo = */new Future<>(() -> {
//            QueryRow row = db.getPresent( "SELECT * FROM Field WHERE contact = 0 AND type = "+FieldType.NUMBER.getIndex()).first();
//            return new Field(null,FieldType.NAME,row.getColumn("name").toString(),row.getColumn("value").toString());
//        }).then(System.out::println);
//        db.getPresent()

//        Future<String> foo = new Future<>(() -> "bar");
//        foo.then(System.out::println);
//        Contact contact = new Contact("0");
//        contact.getFoo().then(System.out::println);


//        System.out.println(new Query().select("*").from("Contact, Field").where("Contact.id = Field.contact","Field.type = 2"));

//        System.out.println(Database.get("SELECT * FROM Contact;"));
//        Query q = new Query()
//                .select("COUNT(contact)")
//                .from(Field.class)
//                .where("contact = \"0\"");
//
//
//
//        System.out.println(q);

        Database.configure("contacts.db");


        Contact contact = new Contact(2);
        System.out.println(contact);

        Thread.sleep(1000);

//        contact.hasValue("Robert");
//        contact.getField(FieldType.NAME, "first").then(qr -> {
//            System.out.println(qr);
//        });

//        contact.getFieldTypes().then(fieldTypes -> {
//            for (FieldType type : fieldTypes) {
//                System.out.println("Contact "+ contact.getId() + " has a " + type.name());
//            }
//        });

//        contact.getFieldsOfType(FieldType.NAME).then(System.out::println);



    }

    private static void foo(QuerySet queryResults) {
        for (QueryRow row : queryResults) {
            System.out.println(row.toString());
        }
    }

    public static void database(Database db) {
        ArrayList<Boolean> tests = new ArrayList<>();

        tests.add(db.dropTable(Field.class));
//        tests.add(db.createTable(Field.class));
//        tests.add(db.post("INSERT INTO fields (id,contact,type,name,value) VALUES(?,?,?,?,?)", 1,1,1,"foo","bar"));
//        tests.add(db.post("UPDATE fields SET ID = ? WHERE ID = ?", 71,70));
//        tests.add(db.get("SELECT * FROM fields;", System.out::println));

        System.out.println("\n" + Arrays.toString(tests.toArray()));
    }


    @FunctionalInterface
    public interface Me<R, P> {
        R accept(P parameter);
    }

}

