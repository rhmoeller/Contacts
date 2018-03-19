package com.hjortsholm.contacts.controls;

import com.hjortsholm.contacts.models.Contact;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CategorisedListView extends CustomGrid {
    public CategorisedListView(String categoryHeader, ArrayList<Contact> contacts, Consumer<ContactNavigationTab> onTabSelected) {
        CategoryHeader header = new CategoryHeader(categoryHeader);
        Separator separator = new CustomSeperator();
        VBox contactsList = new VBox();

        for (Contact contact : contacts)
            contactsList.getChildren().add(new ContactNavigationTab(contact,onTabSelected));

        this.addRow(header);
        this.addRow(separator);
        this.addRow(contactsList);
    }
}
