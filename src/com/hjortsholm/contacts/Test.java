package com.hjortsholm.contacts;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.database.Query;
import com.hjortsholm.contacts.database.QueryRow;
import com.hjortsholm.contacts.database.QuerySet;
import com.hjortsholm.contacts.models.Field;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.hjortsholm.contacts.models.FieldType;
import com.hjortsholm.contacts.util.Future;

public class Test {
    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
//        database(db);
//        BufferedImage image = new HexImage().fromHex("68656c6c6f");
//        Application.setDatabase(new Database("contacts.db"));
//        Application.checkDatabaseIntegrity();

        Database db = new Database("contacts.db");
//        CompletableFuture<QuerySet> f = db.getFuture(Query.NAME);
//
//        System.out.println("bar");
//        f.thenAcceptAsync(res -> {
//            for (QueryRow row : res) {
//                System.out.println("Row: " + row.toString());
//            }
//        });

//        db.verifyTable(FieldType.class);
        /*Future<Field> foo = */new Future<>(() -> {
            QueryRow row = db.getPresent( "SELECT * FROM Field WHERE contact = 0 AND type = "+FieldType.NUMBER.getIndex()).first();
            return new Field(null,FieldType.NAME,row.getColumn("name").toString(),row.getColumn("value").toString());
        }).then(System.out::println);
//        db.getPresent()

//        Future<String> foo = new Future<>(() -> "bar");
//        foo.then(System.out::println);
//        Contact contact = new Contact("0");
//        contact.getFoo().then(System.out::println);

        Thread.sleep(1000);
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

}

