package com.hjortsholm.contacts;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.forms.Start;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;

public class Application {
    private static int windowWidth;
    private static int windowHeight;
    private static String title;

    public static void start() {
        Start.show();
    }



    public static void configureDatabase(String path) {
        Database.configure(path);
    }

    public static void checkDatabaseIntegrity() {

//        if (Database.isClosed()) {
////            Application.setDatabase(new Database(Application.getDatabase().getPath()));
//        }
//        if (!Application.getDatabase().isValid()) {
//            System.err.println("db not valid");
//        }
//        if (!Application.getDatabase().verifyTable(Contact.class)) {
//            Application.getDatabase().dropTable(Contact.class);
//            Application.getDatabase().createTable(Contact.class);
//        }
//        if (!Application.getDatabase().verifyTable(Field.class)) {
//            Application.getDatabase().dropTable(Field.class);
//            Application.getDatabase().createTable(Field.class);
//        }
//        if (!Application.getDatabase().verifyTable(FieldType.class)) {
//            Application.getDatabase().dropTable(FieldType.class);
//            Application.getDatabase().createTable(FieldType.class);
//        }
    }

    public static void setWindowSize(int width, int height) {
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

    public static void setTitle(String title) {
        Application.title = title;
    }
}

