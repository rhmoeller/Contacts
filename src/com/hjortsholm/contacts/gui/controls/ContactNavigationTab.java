package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.models.Contact;
import javafx.scene.control.Label;

import java.util.function.Consumer;

/**
 * <h1>Contact Navigation Tab</h1>
 * This one list item of the {@link CategorisedListView categorised list view}.
 * Shows the display title of a contact.
 *
 * @author robertmoller
 * @version 1.0
 * @see Contact
 * @see CategorisedListView
 * @since 10/04/2018
 */
public class ContactNavigationTab extends CustomGrid {

    private boolean selected;
    private Contact contact;
    private Consumer<ContactNavigationTab> onTabSelected;

    /**
     * Creates list item from contact with selected event.
     *
     * @param contact Contact to display information about.
     * @param onTabSelected Selected event.
     */
    public ContactNavigationTab(Contact contact, Consumer<ContactNavigationTab> onTabSelected) {
        this.contact = contact;
        this.selected = false;
        this.onTabSelected = onTabSelected;

        Label name = new Label(contact.getDisplayTitle());

        this.addColumn(name);
        this.setPrefWidth(180);
        this.setOnMouseClicked(event -> this.onTabSelected.accept(this));
    }

    /**
     * Toggles the selected class.
     */
    public void toggleSelected() {
        this.selected = !this.selected;
        if (this.selected)
            this.getStyleClass().add("selected");
        else
            this.getStyleClass().remove("selected");
    }

    /**
     * Gets the contact used to create this list item.
     *
     * @return Contact.
     */
    public Contact getContact() {
        return contact;
    }
}
