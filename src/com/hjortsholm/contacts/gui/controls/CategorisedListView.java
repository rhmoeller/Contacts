package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.models.Contact;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CategorisedListView extends CustomGrid {
    private ArrayList<ContactNavigationTab> navigationTabs;
    public CategorisedListView(String categoryHeader, ArrayList<Contact> contacts, Consumer<ContactNavigationTab> onTabSelected) {
        CategoryHeader header = new CategoryHeader(categoryHeader);
        Separator separator = new CustomSeperator();
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

    public ArrayList<ContactNavigationTab> getNavigationTabs() {
        return this.navigationTabs;
    }
}
