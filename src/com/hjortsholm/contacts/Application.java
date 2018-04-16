package com.hjortsholm.contacts;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.gui.Start;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;


public class Application {
    private static int windowWidth;
    private static int windowHeight;
    private static String title;
    private static Database database;

    public static void main(String[] args) {
        Application app = new Application();
        app.configureDatabase("contacts.db");
        app.checkDatabaseIntegrity();
        app.setTitle("Contacts");
        app.setWindowSize(720, 440);
        app.start();
    }

    public void start() {
        Start.show();
    }


    public void configureDatabase(String path) {
        Database.configure(path);
    }

    public static Database getDatabase() {
        return Application.database;
    }

    public void checkDatabaseIntegrity() {
        if (Database.isClosed() || !Database.isValid()) {
            Database.configure(Database.getPath());
        }
        if (!Database.verifyTable(Contact.class)) {
            Database.dropTable(Contact.class);
            Database.createTable(Contact.class);
        }
        if (!Database.verifyTable(Field.class)) {
            Database.dropTable(Field.class);
            Database.createTable(Field.class);
        }
    }

    public void setWindowSize(int width, int height) {
        Application.windowWidth = width;
        Application.windowHeight = height;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Application.title = title;
    }
}

