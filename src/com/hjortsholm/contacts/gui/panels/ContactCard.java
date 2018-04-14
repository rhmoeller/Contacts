package com.hjortsholm.contacts.gui.panels;

import com.hjortsholm.contacts.Application;
import com.hjortsholm.contacts.gui.controls.ContactFieldsList;
import com.hjortsholm.contacts.gui.controls.ContactInfoHeader;
import com.hjortsholm.contacts.gui.controls.ScrollableView;
import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.gui.util.Anchor;
import com.hjortsholm.contacts.gui.util.Style;
import com.hjortsholm.contacts.models.Contact;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.function.Consumer;

public class ContactCard extends CustomGrid {
    private Contact contact;

    private Label noContactSelectedLabel;

    private Button edit;
    private Button add;
    private Button delete;

    private ScrollableView scrollContainer;
    private ContactFieldsList contactFieldsList;
    private ContactInfoHeader contactInfoHeader;
    private CustomGrid contactInfoPanel;
    private boolean isEditable;
    private boolean newContact;
    private Consumer<Contact> onContactSave;

    public ContactCard() {
        initialiseComponent();
        addDefaultStyleSheet();
        Style.addStylesheet(this,"Buttons");

        this.isEditable = false;
        this.newContact = false;
        AnchorPane container = new AnchorPane();
        AnchorPane utilitiesContainer = new AnchorPane();
        Anchor.setBottomAnchor(utilitiesContainer, 0.);
        Anchor.setRightAnchor(utilitiesContainer, 0.);
        Anchor.setLeftAnchor(utilitiesContainer, 0.);

        this.contactInfoPanel = new CustomGrid();
        this.contactInfoHeader = new ContactInfoHeader();
        this.contactFieldsList = new ContactFieldsList();
        this.scrollContainer = new ScrollableView(contactFieldsList);

        this.contactInfoPanel.addRow(this.contactInfoHeader);
        this.contactInfoPanel.addRow(this.scrollContainer);
        this.scrollContainer.setPrefHeight(Application.getWindowHeight() - 125);

        this.noContactSelectedLabel = new Label("No contact selected");
        this.noContactSelectedLabel.setAlignment(Pos.CENTER);
        Anchor.setRightAnchor(this.noContactSelectedLabel, 0.);
        Anchor.setLeftAnchor(this.noContactSelectedLabel, 0.);
        Anchor.setTopAnchor(this.noContactSelectedLabel, 0.);
        Anchor.setBottomAnchor(this.noContactSelectedLabel, 0.);

        this.edit = new Button();
        this.edit.setOnMouseClicked(this::toggleEdit);
        Style.addStyleClass(this.edit,"default-button");
        Style.addStyleClass(this.edit,"edit");
        Anchor.setRightAnchor(this.edit, 110.);
        Anchor.setBottomAnchor(this.edit, 0.);

        this.add = new Button("+");
        Style.addStyleClass(this.add,"default-button");
        Style.addStyleClass(this.add,"add");
        Anchor.setLeftAnchor(this.add, 40.);
        Anchor.setBottomAnchor(this.add, 0.);

        this.delete = new Button("delete");
        Style.addStyleClass(this.delete,"default-button");
        Style.addStyleClass(this.delete,"delete");
        Anchor.setRightAnchor(this.delete, 40.);
        Anchor.setBottomAnchor(this.delete, 0.);


         menuButton = new MenuButton("Don't touch this");
        MenuItem item1 = new MenuItem("ok");
        item1.setOnAction(value -> {System.out.println("click");});
        menuButton.getItems().addAll(item1, new MenuItem("Do not"));

        container.getChildren().addAll(this.noContactSelectedLabel, this.contactInfoPanel, this.add, this.edit, this.delete, menuButton);
        this.addRow(container);
        this.refresh();
    }

    public void setOnContactDelete(Consumer<Contact> event) {
        this.delete.setOnMouseClicked(event1 -> {
            event.accept(this.contact);
            this.contact = null;
            this.setEditable(false);
            this.refresh();
        });
    }

    public void setOnContactSave(Consumer<Contact> event) {
        this.onContactSave = event;
    }

    public void setOnNewContact(Consumer<Contact> event) {
        this.add.setOnMouseClicked(event1 -> {
            event.accept(new Contact(-1));
            this.setEditable(true);
            this.refresh();
        });
    }

    public Contact getContact() {
        return this.contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
        this.contactFieldsList.setContact(contact);
        this.contactInfoHeader.setContact(contact);
        this.refresh();
    }

    public void refresh() {
        if (this.contact != null) {
            this.noContactSelectedLabel.setVisible(false);
            this.edit.setText(this.isEditable ? "save" : "edit");
            this.scrollContainer.setVisible(true);
            this.edit.setVisible(true);
            this.delete.setVisible(true);
            this.contactFieldsList.setEditable(this.isEditable);
            this.contactInfoHeader.setEditable(this.isEditable);
            this.contactInfoHeader.setVisibility(true);
            if (this.isEditable) {
                this.contactInfoHeader.setFocus();
            }
        } else {
            this.noContactSelectedLabel.setVisible(true);
            this.scrollContainer.setVisible(false);
            this.edit.setVisible(false);
            this.delete.setVisible(false);
            this.contactFieldsList.setEditable(false);
            this.contactInfoHeader.setEditable(false);
            this.contactInfoHeader.setVisibility(false);
        }
    }

    public void save() {
        if (this.onContactSave != null && this.contact != null) {
            this.onContactSave.accept(this.contact);
        }
    }

    private void toggleEdit(MouseEvent mouseEvent) {
        this.setEditable(!this.isEditable);
    }

    public boolean isValid() {
        return this.contactInfoHeader.isValid();
    }

    public boolean isEmpty() {
        return this.contact == null;
    }

    public boolean isEditable() {
        return this.isEditable;
    }

    public void setEditable(boolean editable) {
        if (this.contactInfoHeader.isValid()) {
            if (!editable && this.isEditable) {
                this.save();
            }
            this.isEditable = editable;
        } else
            this.isEditable = true;
        this.refresh();
    }

}
