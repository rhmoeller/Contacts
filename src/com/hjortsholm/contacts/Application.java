package com.hjortsholm.contacts;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.gui.Start;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;

/**
 * <h1>Application</h1>
 * This program is a address book that stores
 * a list of information about the people you add.
 * The types of information it stores is:
 * <ul>
 * <li>Name</li>
 * <li>Phone number</li>
 * <li>Email</li>
 * <li>Address</li>
 * <li>Note</li>
 * <li>Date</li>
 * </ul>
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @see Database
 * @see FieldType
 * @since 10/04/2018
 */
public class Application {
    private static int windowWidth;
    private static int windowHeight;
    private static String title;
    private static Database database;

    /**
     * Configures database and starts the GUI.
     *
     * @param args Program arguments.
     */
    public static void main(String[] args) {
        Database.configure("contacts.db");
        Application app = new Application();
        app.checkDatabaseIntegrity();
        app.setTitle("Contacts");
        app.setWindowSize(840, 440);
        Start.show();
    }

    /**
     * Gets the preffered window height.
     *
     * @return Window height.
     */
    public static int getWindowHeight() {
        return windowHeight;
    }

    /**
     * Gets the preffered window width.
     *
     * @return Window width.
     */
    public static int getWindowWidth() {
        return windowWidth;
    }

    /**
     * Gets the window title.
     *
     * @return Window title.
     */
    public static String getTitle() {
        return title;
    }

    /**
     * Sets the window title.
     *
     * @param title Window title.
     */
    public void setTitle(String title) {
        Application.title = title;
    }

    /**
     * Verifies the integrity of the needed tables in the database
     * and corrects any errors.
     */
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

    /**
     * Sets the preffered window size.
     *
     * @param width  Height of the window.
     * @param height Width of the window.
     */
    public void setWindowSize(int width, int height) {
        Application.windowWidth = width;
        Application.windowHeight = height;
    }
}

