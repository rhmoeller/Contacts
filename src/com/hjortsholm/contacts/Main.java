package com.hjortsholm.contacts;

import com.hjortsholm.contacts.database.Database;

public class Main {
    public static void main(String[] args) {
        Database.configure("contacts.db");
        Application.checkDatabaseIntegrity();
        Application.setTitle("Contacts");
        Application.setWindowSize(720, 440);
        Application.start();
    }
}
