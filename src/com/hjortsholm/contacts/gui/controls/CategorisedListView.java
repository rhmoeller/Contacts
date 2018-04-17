package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.gui.style.Style;
import com.hjortsholm.contacts.models.Contact;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * <h1>Categorised List View</h1>
 * This is a custom list that takes a list of contacts and a list title.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @see ContactNavigationTab
 * @see Contact
 * @since 10/04/2018
 */
public class CategorisedListView extends CustomGrid {
    private ArrayList<ContactNavigationTab> navigationTabs;

    /**
     * Creates navigation tabs for every contact given and
     * adds them to the list.
     *
     * @param categoryHeader Header string for the group.
     * @param contacts       Contacts to be added to group.
     * @param onTabSelected  Tab selected event.
     */
    public CategorisedListView(String categoryHeader, ArrayList<Contact> contacts, Consumer<ContactNavigationTab> onTabSelected) {
        Label header = new Label(categoryHeader.toUpperCase());
        Style.addStyleClass(header, "CategoryHeader");
        Separator separator = new Separator();
        VBox contactsList = new VBox();
        this.navigationTabs = new ArrayList<>();

        for (Contact contact : contacts) {
            ContactNavigationTab navigationTab = new ContactNavigationTab(contact, onTabSelected);
            this.navigationTabs.add(navigationTab);
            contactsList.getChildren().add(navigationTab);

        }
        this.addRow(header);
        this.addRow(separator);
        this.addRow(contactsList);
    }

    /**
     * Gets all tabs in list.
     *
     * @return All tabs in list.
     */
    public ArrayList<ContactNavigationTab> getNavigationTabs() {
        return this.navigationTabs;
    }
}
