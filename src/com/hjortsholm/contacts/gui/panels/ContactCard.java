package com.hjortsholm.contacts.gui.panels;

import com.hjortsholm.contacts.Application;
import com.hjortsholm.contacts.gui.controls.ContactFieldRow;
import com.hjortsholm.contacts.gui.controls.ContactFieldsList;
import com.hjortsholm.contacts.gui.controls.ContactInfoHeader;
import com.hjortsholm.contacts.gui.controls.ScrollableView;
import com.hjortsholm.contacts.gui.util.Anchor;
import com.hjortsholm.contacts.gui.util.Style;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.function.Consumer;

/**
 * <h1>Contact card</h1>
 * Displays one contacts asociated fields.
 *
 * @author robertmoller
 * @version 1.0
 * @since 2018-04-14
 */
public class ContactCard extends AnchorPane {
    private Contact contact;

    private Label noContactSelectedLabel;

    private Button edit;
    private Button delete;
    private MenuButton add;
    private MenuItem addContactMenuItem;
    private Separator topSeparator;
    private Separator bottomSeparator;

    private ScrollableView scrollContainer;
    private ContactFieldsList contactFieldsList;
    private ContactInfoHeader contactInfoHeader;
    private ContactFieldRow focusedRow;
    private boolean isEditable;
    private Consumer<Contact> onContactSave;

    public ContactCard() {
        Style.addStylesheet(this, "ContactCard");
        Style.addGenericStyleClass(this);
        Style.addStylesheet(this, "Buttons");

        this.isEditable = false;
        AnchorPane utilitiesContainer = new AnchorPane();
        Anchor.setBottomAnchor(utilitiesContainer, 0.);
        Anchor.setRightAnchor(utilitiesContainer, 0.);
        Anchor.setLeftAnchor(utilitiesContainer, 0.);

        this.contactInfoHeader = new ContactInfoHeader();
        Anchor.anchorAll(this.contactInfoHeader, 0);

        this.contactFieldsList = new ContactFieldsList();
        this.topSeparator = new Separator();
        this.bottomSeparator = new Separator();
        Anchor.setTopAnchor(this.topSeparator, 68.);
        Anchor.setLeftAnchor(this.topSeparator, 21.);
        Anchor.setRightAnchor(this.topSeparator, 27.);
        Anchor.setBottomAnchor(this.bottomSeparator, 48.);
        Anchor.setLeftAnchor(this.bottomSeparator, 21.);
        Anchor.setRightAnchor(this.bottomSeparator, 27.);

        this.scrollContainer = new ScrollableView(this.contactFieldsList);
        this.scrollContainer.setPrefHeight(Application.getWindowHeight() - 90);
        this.scrollContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        this.scrollContainer.vvalueProperty().addListener((observable, oldValue, newValue) -> this.horisontalBarController());
        this.scrollContainer.setVvalue(1);
        Anchor.anchorAll(this.scrollContainer, 0.);
        Anchor.setTopAnchor(this.scrollContainer, 68.);
        Anchor.setBottomAnchor(this.scrollContainer, 50.);

        this.noContactSelectedLabel = new Label("No contact selected");
        this.noContactSelectedLabel.setAlignment(Pos.CENTER);
        Anchor.setRightAnchor(this.noContactSelectedLabel, 0.);
        Anchor.setLeftAnchor(this.noContactSelectedLabel, 0.);
        Anchor.setTopAnchor(this.noContactSelectedLabel, 0.);
        Anchor.setBottomAnchor(this.noContactSelectedLabel, 0.);

        this.edit = new Button();
        this.edit.setOnMouseClicked(this::toggleEdit);
        Style.addStyleClass(this.edit, "default-button");
        Style.addStyleClass(this.edit, "edit");
        Anchor.setRightAnchor(this.edit, 100.);
        Anchor.setBottomAnchor(this.edit, 15.);

        this.add = new MenuButton();
        this.add.setText("+");
        this.add.setPopupSide(Side.TOP);
        Style.addStyleClass(this.add, "default-button");
        Style.addStyleClass(this.add, "add");
        Anchor.setLeftAnchor(this.add, 15.);
        Anchor.setBottomAnchor(this.add, 15.);

        this.addContactMenuItem = new MenuItem("Contact");
        this.add.getItems().addAll(this.addContactMenuItem, new SeparatorMenuItem());

        for (FieldType type : FieldType.values()) {
            if (type != FieldType.NAME) {
                MenuItem item = new MenuItem(type.name().substring(0, 1).toUpperCase() + type.name().substring(1).toLowerCase());
                item.setOnAction(action -> {
                    this.setEditable(true);
                    Field field = new Field(-1, this.contact.getId(), type, type.getDefaultName(), "¿?");
                    field.push();
                    this.focusedRow = new ContactFieldRow(field);
                    this.setContact(this.contact);
                    this.focusedRow.setEditable(true);
                });
                this.add.getItems().add(item);
            }
        }

        this.delete = new Button("delete");
        Style.addStyleClass(this.delete, "default-button");
        Style.addStyleClass(this.delete, "delete");
        Anchor.setRightAnchor(this.delete, 15.);
        Anchor.setBottomAnchor(this.delete, 15.);


        this.getChildren().addAll(
                this.noContactSelectedLabel,
                this.topSeparator,
                this.bottomSeparator,
                this.contactInfoHeader,
                this.scrollContainer,
                this.add,
                this.edit,
                this.delete);
        this.refresh();
    }

    private void horisontalBarController() {
        double verticalValue = this.scrollContainer.getVvalue(),
                viewHeight = this.scrollContainer.getHeight(),
                contentHeight = this.scrollContainer.getContent().getBoundsInParent().getHeight();
        this.bottomSeparator.setVisible(contentHeight - (verticalValue * contentHeight) > 0 && contentHeight > viewHeight);
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
        EventHandler<ActionEvent> handler = actionEvent -> {
            event.accept(new Contact(-1));
            this.setEditable(true);
            this.refresh();
            this.contactInfoHeader.setFocus();
        };

        this.add.setOnMouseClicked(action -> {
            if (this.isEmpty())
                handler.handle(null);
        });
        this.addContactMenuItem.setOnAction(handler);
    }

    public Contact getContact() {
        return this.contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
        this.contactFieldsList.setContact(contact);
        this.contactInfoHeader.setContact(contact);
        this.scrollContainer.setVvalue(.01);
        this.refresh();

    }

    private void refresh() {
        if (this.contact != null) {
            this.noContactSelectedLabel.setVisible(false);
            this.edit.setText(this.isEditable ? "done" : "edit");
            this.scrollContainer.setVisible(true);
            this.edit.setVisible(true);
            this.delete.setVisible(true);
            this.contactFieldsList.setEditable(this.isEditable);
            this.contactInfoHeader.setEditable(this.isEditable);
            this.contactInfoHeader.setVisibility(true);


        } else {
            this.noContactSelectedLabel.setVisible(true);
            this.scrollContainer.setVisible(false);
            this.edit.setVisible(false);
            this.delete.setVisible(false);
            this.contactFieldsList.setEditable(false);
            this.contactInfoHeader.setEditable(false);
            this.contactInfoHeader.setVisibility(false);
            this.topSeparator.setVisible(false);
            this.bottomSeparator.setVisible(false);

        }
        this.horisontalBarController();
    }

    private void save() {
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
        } else {
            this.isEditable = true;
        }
        this.refresh();
    }

}
