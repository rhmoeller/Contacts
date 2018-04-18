package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.gui.style.Style;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

/**
 * <h1>Contact Info Header</h1>
 * Displays the contact's names.
 *
 * @author robertmoller
 * @version 1.0
 * @see Contact
 * @see Field
 * @see ContactInfoHeader
 * @see ContactFieldRow
 * @since 2018-04-14
 */
public class ContactInfoHeader extends AnchorPane {

    private ProfilePicture profilePicture;
    private EditableLabel firstName;
    private EditableLabel lastName;
    private EditableLabel nickName;

    /**
     * Creates controls, binds events and adds style classes.
     */
    public ContactInfoHeader() {
        Style.addGenericStyleClass(this);

        this.firstName = new EditableLabel("", "first");
        this.firstName.setOnTextFieldChanged(this::textChanged);

        this.lastName = new EditableLabel("", "last");
        this.lastName.setOnTextFieldChanged(this::textChanged);

        this.nickName = new EditableLabel("", "nickname");
        this.nickName.setOnTextFieldChanged(this::textChanged);

        this.profilePicture = new ProfilePicture();
        AnchorPane.setTopAnchor(this.profilePicture, 0.);
        AnchorPane.setLeftAnchor(this.profilePicture, 0.);

        FlowPane formalNames = new FlowPane(this.firstName, this.lastName);
        AnchorPane.setLeftAnchor(formalNames, 85.);
        AnchorPane.setTopAnchor(formalNames, 20.);

        FlowPane informalNames = new FlowPane(this.nickName);
        AnchorPane.setLeftAnchor(informalNames, 85.);
        AnchorPane.setTopAnchor(informalNames, 45.);

        Style.addStyleClass(this.firstName, "first");
        Style.addStyleClass(this.lastName, "last");
        Style.addStyleClass(this.nickName, "nickname");

        this.getChildren().addAll(this.profilePicture,formalNames,informalNames);
        this.setEditable(false);
    }

    /**
     * Sets the value of a field.
     *
     * @param text  What value to set.
     * @param field What field to change the value in.
     */
    private void textChanged(String text, Field field) {
        field.setValue(text);
    }

    /**
     * Set the contact to display names from.
     *
     * @param contact Contact to display.
     */
    public void setContact(Contact contact) {
        this.profilePicture.setContact(contact);
        this.firstName.setField(contact.getFirstName());
        this.lastName.setField(contact.getLastName());
        this.nickName.setField(contact.getNickName());
    }

    /**
     * Checks if the field with the first name is empty.
     *
     * @return Is the contact in a valid state.
     */
    public boolean isValid() {
        return !this.firstName.getText().isEmpty();
    }

    /**
     * Sets all relevant fields to editable or not.
     *
     * @param editable Should fields be editable.
     */
    public void setEditable(boolean editable) {
        this.profilePicture.setEditable(editable);
        this.firstName.setEdit(editable);
        this.lastName.setEdit(editable);
        this.nickName.setEdit(editable);
    }

    /**
     * Sets the visibility of the relevant text fields.
     *
     * @param visible Should the text fields be visible.
     */
    public void setVisibility(boolean visible) {
        this.firstName.setVisible(visible && (!this.firstName.getText().isEmpty() || (this.firstName.isEditable() && !this.firstName.getPromptText().isEmpty())));
        this.lastName.setVisible(visible && (!this.lastName.getText().isEmpty() || (this.lastName.isEditable() && !this.lastName.getPromptText().isEmpty())));
        this.nickName.setVisible(visible && (!this.nickName.getText().isEmpty() || (this.nickName.isEditable() && !this.nickName.getPromptText().isEmpty())));
    }

    /**
     * Sets the window focus to the text field with the first name.
     */
    public void setFocus() {
        this.firstName.requestFocus();
    }


}
