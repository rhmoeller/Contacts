package com.hjortsholm.contacts;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws SQLException {
//        Database db = new Database("contacts.db");
//        database(db);
    }

    public static void database(Database db) {
        ArrayList<Boolean> tests = new ArrayList<>();

        tests.add(db.dropTable("fields"));
        tests.add(db.createTable("fields", Field.class));
        tests.add(db.post("INSERT INTO fields (id,contact,type,name,value) VALUES(?,?,?,?,?)", 1,1,1,"foo","bar"));
        tests.add(db.post("UPDATE fields SET ID = ? WHERE ID = ?", 71,70));
        tests.add(db.get("SELECT * FROM fields;", System.out::println));

        System.out.println("\n" + Arrays.toString(tests.toArray()));
    }

}

