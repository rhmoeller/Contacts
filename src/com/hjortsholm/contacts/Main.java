package com.hjortsholm.contacts;

public class Main {
    public static void main(String[] args) {
        Application.configureDatabase("contacts.db");
        Application.checkDatabaseIntegrity();
        Application.setTitle("Contacts");
        Application.setWindowSize(720, 440);
        Application.start();
    }
}
