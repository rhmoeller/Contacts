package com.hjortsholm.contacts;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.forms.Start;

import java.awt.*;

public class Application {
    private static Database database;
    private static int windowWidth;
    private static int windowHeight;
    private static String title;

    public static void start() {
        Start.show();
    }


    public static Database getDatabase() {
        return Application.database;
    }

    public static void setDatabase(Database database) {
        Application.database = database;
    }

    public static void checkDatabaseIntegrity() {
        Application.getDatabase();
    }

    public static void createDatabase() {

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

