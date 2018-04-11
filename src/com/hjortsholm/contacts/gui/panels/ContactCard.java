package com.hjortsholm.contacts.gui.panels;

import com.hjortsholm.contacts.Application;
import com.hjortsholm.contacts.gui.controls.EditableLabel;
import com.hjortsholm.contacts.gui.parents.CompositeControl;
import com.hjortsholm.contacts.gui.parents.LayerPane;
import com.hjortsholm.contacts.models.Contact;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ContactCard extends CompositeControl {
    private Contact contact;

    private Label emptyCard;
    private LayerPane container;
    private EditableLabel firstName;
    private EditableLabel lastName;

    private Button edit;

    private LayerPane backgroundLayer;
    private LayerPane middlegroundLayer;

    private boolean editing;

    public ContactCard() {
        super();
        this.editing = false;
        this.container = new LayerPane();
        this.emptyCard = new Label("No contact selected");
        this.firstName = new EditableLabel();
        this.lastName = new EditableLabel();

        backgroundLayer = new LayerPane();
        backgroundLayer.addLayer(this.emptyCard);
        middlegroundLayer = new LayerPane();

//        middlegroundLayer.addLayer(this.firstName,this.lastName);
//        this.addColumn(new Spacer(1,20));
//        this.container.addLayer(backgroundLayer);
//        this.container.addLayer(middlegroundLayer);
//        this.addColumn(this.emptyCard);
        edit = new Button();
        edit.setOnMouseClicked(event -> edit());

        this.addColumn(this.firstName);
        this.addColumn(this.lastName);
        this.addRow(edit);

        this.setPrefHeight(Application.getWindowHeight());
        refresh();
    }

    public void refresh() {
        if(this.contact != null) {
//            this.middlegroundLayer.setVisible(false);
            this.firstName.setText(this.contact.getFirstName());
            this.lastName.setText(this.contact.getLastName());

            if(this.editing) {
                this.edit.setText("Save");
            } else {
                this.edit.setText("Edit");
            }

        } else {
//            this.middlegroundLayer.setVisible(true);
        }
    }

    public void edit() {
        this.editing = !this.editing;
        this.firstName.toggleEdit();
        this.lastName.toggleEdit();

        if (!this.editing) {
            this.contact.setFirstName(this.firstName.getText());
            this.contact.setLastName(this.lastName.getText());
        }
        this.refresh();

    }

    public void setContact(Contact contact) {
        this.contact = contact;
        this.refresh();
    }
}
