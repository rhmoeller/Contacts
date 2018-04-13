package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.models.Contact;
import javafx.scene.control.Label;

import java.util.function.Consumer;

public class ContactNavigationTab extends CustomGrid {
    private static int number = 1;

    private boolean selected;
    private Contact contact;
    private Consumer<ContactNavigationTab> onTabSelected;

    public ContactNavigationTab(Contact contact, Consumer<ContactNavigationTab> onTabSelected) {
        this.contact = contact;
        this.selected = false;
        this.onTabSelected = onTabSelected;

        // Todo: Edit tab content
        Label name = new Label();

        name.setText(contact.getFirstName().getValue());

//        this.addColumn(a);
        this.addColumn(name);
        this.setPrefWidth(180);
        this.setOnMouseClicked(event -> this.onTabSelected.accept(this));
    }

    public void toggleSelected() {
        this.selected = !this.selected;
        if (this.selected)
            this.getStyleClass().add("selected");
        else
            this.getStyleClass().remove("selected");
    }

    public Contact getContact() {
        return contact;
    }
}
