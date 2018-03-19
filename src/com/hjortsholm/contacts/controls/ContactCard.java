package com.hjortsholm.contacts.controls;

import com.hjortsholm.contacts.controls.style.Style;
import com.hjortsholm.contacts.models.Contact;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ContactCard extends CompositeControl {
    private Contact contact;

    private EditableLabel nameLabel;

    public ContactCard() {
        this.nameLabel = new EditableLabel();

        super.init(nameLabel); // Todo: fix me
    }

    public void refresh() {
        if(this.contact == null) {

        }
    }

    public void setContact(Contact contact) {
        this.contact = contact;
        this.nameLabel.setText(this.contact.getFirstName());
    }


}
