package com.hjortsholm.contacts;

import com.hjortsholm.contacts.database.Database;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Application.setDatabase(new Database("contacts.db"));
        Application.checkDatabaseIntegrity();
        Application.setTitle("Contacts");
        Application.setWindowSize(720,480);
        Application.start();
    }
}
