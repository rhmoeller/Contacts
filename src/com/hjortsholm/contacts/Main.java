package com.hjortsholm.contacts;

public class Main {
    public static void main(String[] args) {
        Application app = new Application();
        app.configureDatabase("contacts.db");
        app.checkDatabaseIntegrity();
        app.setTitle("Contacts");
        app.setWindowSize(720, 440);
        app.start();
    }
}
