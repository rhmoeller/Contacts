package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.gui.util.Style;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import javafx.scene.layout.FlowPane;

public class ContactInfoHeader extends CustomGrid {

    private EditableLabel firstName;
    private EditableLabel lastName;
    private EditableLabel nickName;

    public ContactInfoHeader() {
        initialiseComponent();

        this.firstName = new EditableLabel("", "first");
        this.firstName.setOnTextFieldChanged(this::textChanged);

        this.lastName = new EditableLabel("", "last");
        this.lastName.setOnTextFieldChanged(this::textChanged);

        this.nickName = new EditableLabel("", "nick");
        this.nickName.setOnTextFieldChanged(this::textChanged);

        Style.addStyleClass(this.firstName, "first");
        Style.addStyleClass(this.lastName, "last");
        Style.addStyleClass(this.nickName, "nickname");

        this.addRow(new FlowPane(this.firstName, this.lastName));
        this.addRow(new FlowPane(this.nickName));
        this.setEditable(false);
    }

    private void textChanged(String text, Field field) {
        field.setValue(text);
    }

    public void setContact(Contact contact) {
        this.firstName.setField(contact.getFirstName());
        this.lastName.setField(contact.getLastName());
        this.nickName.setField(contact.getNickName());
    }

    public boolean isValid() {
        return !this.firstName.getText().isEmpty();
    }

    public void setEditable(boolean editable) {
        this.firstName.setEdit(editable);
        this.lastName.setEdit(editable);
        this.nickName.setEdit(editable);
    }


    public void setVisibility(boolean visible) {
        this.firstName.setVisible(visible && (!this.firstName.getText().isEmpty() || (this.firstName.isEditable() && !this.firstName.getPromptText().isEmpty())));
        this.lastName.setVisible(visible && (!this.lastName.getText().isEmpty() || (this.lastName.isEditable() && !this.lastName.getPromptText().isEmpty())));
        this.nickName.setVisible(visible && (!this.nickName.getText().isEmpty() || (this.nickName.isEditable() && !this.nickName.getPromptText().isEmpty())));
    }

    public void setFocus() {
        this.firstName.requestFocus();
    }


}
