package com.hjortsholm.contacts.controls;

import com.hjortsholm.contacts.controls.style.Style;
import com.hjortsholm.contacts.models.Contact;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ContactCard extends CompositeControl {
    private Contact contact;

    private Label emptyCard;

    private EditableLabel firstName;
    private EditableLabel lastName;


    public ContactCard() {
        super();
        this.emptyCard = new Label("No contact selected");
        this.firstName = new EditableLabel();
        this.lastName = new EditableLabel();

        this.addChild(this.emptyCard);
        this.addChild(this.firstName);
        this.addChild(this.lastName);
        refresh();
    }

    public void refresh() {
        if(this.contact != null) {
            this.emptyCard.setVisible(false);
            this.firstName.setText(this.contact.getFirstName());
            this.lastName.setText(this.contact.getFirstName());
        } else {
            this.emptyCard.setVisible(true);
        }
    }

    public void setContact(Contact contact) {
        this.contact = contact;
        this.refresh();
    }
}
