package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.models.Contact;

public class ContactInfoHeader extends CustomGrid {

    private EditableLabel firstName;
    private EditableLabel lastName;
    private EditableLabel nickName;

    public ContactInfoHeader() {
        initialiseComponent();

        firstName = new EditableLabel();
        lastName = new EditableLabel();
        nickName = new EditableLabel();


        this.add(firstName, 0, 0);
        this.add(lastName, 1, 0);
        this.add(nickName, 0, 1);
    }

    public void setContact(Contact contact) {
        firstName.setField(contact.getFirstName());
        lastName.setField(contact.getLastName());
        nickName.setField(contact.getNickName());

        firstName.setVisible(!firstName.getText().isEmpty());
        lastName.setVisible(!lastName.getText().isEmpty());
        nickName.setVisible(!nickName.getText().isEmpty());
    }

    public boolean isValid() {
        return !firstName.getText().isEmpty() ||
                !lastName.getText().isEmpty() ||
                !nickName.getText().isEmpty();
    }

    public void setEditable(boolean editable) {
        this.firstName.setEdit(editable);
        this.lastName.setEdit(editable);
        this.nickName.setEdit(editable);
    }

    public void setFocus() {
        this.firstName.requestFocus();
    }


}
