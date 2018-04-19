package com.hjortsholm.contacts.gui.panels;

import com.hjortsholm.contacts.Application;
import com.hjortsholm.contacts.gui.controls.ContactFieldRow;
import com.hjortsholm.contacts.gui.controls.ContactFieldsList;
import com.hjortsholm.contacts.gui.controls.ContactInfoHeader;
import com.hjortsholm.contacts.gui.controls.ScrollableView;
import com.hjortsholm.contacts.gui.style.Style;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.function.Consumer;

/**
 * <h1>Contact Card</h1>
 * Displays one contacts associated fields.
 *
 * @author robertmoller
 * @version 1.0
 * @see Contact
 * @see ContactFieldsList
 * @see ContactInfoHeader
 * @see ContactFieldRow
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

    /**
     * Creates and adds all controls to pane.
     */
    public ContactCard() {
        Style.addGenericStyleClass(this);
        Style.addStylesheet(this, "Buttons");
        Style.addStylesheet(this, "ContactCard");

        this.isEditable = false;
        AnchorPane utilitiesContainer = new AnchorPane();
        AnchorPane.setBottomAnchor(utilitiesContainer, 0.);
        AnchorPane.setRightAnchor(utilitiesContainer, 0.);
        AnchorPane.setLeftAnchor(utilitiesContainer, 0.);

        this.contactInfoHeader = new ContactInfoHeader();
        AnchorPane.setLeftAnchor(this.contactInfoHeader, 0.);
        AnchorPane.setRightAnchor(this.contactInfoHeader, 0.);
        AnchorPane.setTopAnchor(this.contactInfoHeader, 0.);
        AnchorPane.setBottomAnchor(this.contactInfoHeader, 0.);


        this.topSeparator = new Separator();
        AnchorPane.setTopAnchor(this.topSeparator, 108.);
        AnchorPane.setLeftAnchor(this.topSeparator, 16.);
        AnchorPane.setRightAnchor(this.topSeparator, 17.);

        this.bottomSeparator = new Separator();
        AnchorPane.setBottomAnchor(this.bottomSeparator, 48.);
        AnchorPane.setLeftAnchor(this.bottomSeparator, 16.);
        AnchorPane.setRightAnchor(this.bottomSeparator, 17.);
        Style.addStyleClass(this.bottomSeparator, "bottom-separator");

        this.contactFieldsList = new ContactFieldsList();

        this.scrollContainer = new ScrollableView(this.contactFieldsList);
        this.scrollContainer.setPrefHeight(Application.getWindowHeight() - 90);
        this.scrollContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.scrollContainer.vvalueProperty().addListener((observable, oldValue, newValue) -> this.refreshHorisontalBar());
        this.scrollContainer.setVvalue(1);
        AnchorPane.setLeftAnchor(this.scrollContainer, 0.);
        AnchorPane.setRightAnchor(this.scrollContainer, 0.);
        AnchorPane.setTopAnchor(this.scrollContainer, 108.);
        AnchorPane.setBottomAnchor(this.scrollContainer, 50.);
        Style.addStylesheet(this.scrollContainer, "ContactCard");

        this.noContactSelectedLabel = new Label("No contact selected");
        this.noContactSelectedLabel.setAlignment(Pos.CENTER);
        AnchorPane.setRightAnchor(this.noContactSelectedLabel, 0.);
        AnchorPane.setLeftAnchor(this.noContactSelectedLabel, 0.);
        AnchorPane.setTopAnchor(this.noContactSelectedLabel, 0.);
        AnchorPane.setBottomAnchor(this.noContactSelectedLabel, 0.);

        this.edit = new Button();
        this.edit.setOnMouseClicked(this::toggleEdit);
        Style.addStyleClass(this.edit, "default-button");
        Style.addStyleClass(this.edit, "edit");
        AnchorPane.setRightAnchor(this.edit, 100.);
        AnchorPane.setBottomAnchor(this.edit, 15.);

        this.delete = new Button("delete");
        Style.addStyleClass(this.delete, "default-button");
        Style.addStyleClass(this.delete, "delete");
        AnchorPane.setRightAnchor(this.delete, 15.);
        AnchorPane.setBottomAnchor(this.delete, 15.);

        this.add = new MenuButton();
        this.add.setText("+");
        this.add.setPopupSide(Side.TOP);
        Style.addStyleClass(this.add, "default-button");
        Style.addStyleClass(this.add, "add");
        AnchorPane.setLeftAnchor(this.add, 15.);
        AnchorPane.setBottomAnchor(this.add, 15.);

        this.addContactMenuItem = new MenuItem("Contact");
        this.add.getItems().addAll(this.addContactMenuItem, new SeparatorMenuItem());
        for (FieldType type : FieldType.values()) {
            if (type != FieldType.NAME && type != FieldType.PICTURE) {
                MenuItem item = new MenuItem(type.name().substring(0, 1).toUpperCase() + type.name().substring(1).toLowerCase());
                item.setOnAction(action -> {
                    this.setEditable(true);
                    Field field = new Field(-1, this.contact.getId(), type, "", "::focus");
                    field.push();
                    this.focusedRow = new ContactFieldRow(field);
                    this.setContact(this.contact);
                    this.focusedRow.setEditable(true);
                });
                this.add.getItems().add(item);
            }
        }

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

    /**
     * Intelligently control visibility on the horisontal separators and scrollbar.
     */
    private void refreshHorisontalBar() {
        Platform.runLater(() -> {
            double verticalValue = this.scrollContainer.getVvalue(),
                    viewHeight = this.scrollContainer.getHeight(),
                    contentHeight = this.scrollContainer.getContent().getBoundsInParent().getHeight();
            if (contentHeight > viewHeight) {
                Style.addStyleClass(this.scrollContainer, "overflowed");
                this.bottomSeparator.setVisible(contentHeight * verticalValue < contentHeight);
            } else {
                Style.removeStyleClass(this.scrollContainer, "overflowed");
                this.bottomSeparator.setVisible(false);
            }
        });
    }

    /**
     * Sets the delete buttons click event.
     *
     * @param event Delete button pressed event.
     */
    public void setOnContactDelete(Consumer<Contact> event) {
        this.delete.setOnMouseClicked(event1 -> {
            event.accept(this.contact);
            this.contact = null;
            this.setEditable(false);
            this.refresh();
        });
    }

    /**
     * Sets the contact save event.
     *
     * @param event Contact save event.
     */
    public void setOnContactSave(Consumer<Contact> event) {
        this.onContactSave = event;
    }

    /**
     * Sets the contact creation event.
     *
     * @param event Contact creation event.
     */
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

    /**
     * Gets the shown contact.
     *
     * @return Current contact.
     * @see Contact
     */
    public Contact getContact() {
        return this.contact;
    }

    /**
     * Sets the shown contact.
     *
     * @param contact Contact to show.
     */
    public void setContact(Contact contact) {
        this.contact = contact;
        this.contactFieldsList.setContact(contact);
        this.contactInfoHeader.setContact(contact);
        this.scrollContainer.setVvalue(0);
        this.refresh();
    }

    /**
     * Refreshes controls and makes sure the right things are visible.
     */
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
            this.topSeparator.setVisible(true);
        } else {
            this.noContactSelectedLabel.setVisible(true);
            this.scrollContainer.setVisible(false);
            this.edit.setVisible(false);
            this.delete.setVisible(false);
            this.contactFieldsList.setEditable(false);
            this.contactInfoHeader.setEditable(false);
            this.contactInfoHeader.setVisibility(false);
            this.topSeparator.setVisible(false);
//            this.bottomSeparator.setVisible(false);
        }
        this.refreshHorisontalBar();

    }

    /**
     * Runs contact save event.
     */
    private void save() {
        if (this.onContactSave != null && this.contact != null) {
            this.onContactSave.accept(this.contact);
        }
    }

    /**
     * Toggle editing the selected contact.
     *
     * @param mouseEvent Mouse click event
     */
    private void toggleEdit(MouseEvent mouseEvent) {
        this.setEditable(!this.isEditable);
    }

    /**
     * Checks if the contact fields needed for a valid contact is not empty.
     *
     * @return Selected contact is valid.
     */
    public boolean isValid() {
        return this.contactInfoHeader.isValid();
    }

    /**
     * Checks if there is a contact shown.
     *
     * @return Is the contact card empty.
     */
    public boolean isEmpty() {
        return this.contact == null;
    }

    /**
     * Is the user editing a contact.
     *
     * @return Is the contact card currently editable.
     */
    public boolean isEditable() {
        return this.isEditable;
    }

    /**
     * Sets weather or not the contact card is editable.
     *
     * @param editable Should the contact card be editable.
     */
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
