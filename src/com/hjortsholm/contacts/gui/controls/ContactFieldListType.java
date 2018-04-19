package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;
import javafx.scene.control.Separator;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * <h1>Contact Field List Type</h1>
 * This is a custom list of contact fields grouped by a type.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @see ContactNavigationTab
 * @see Contact
 * @since 10/04/2018
 */
public class ContactFieldListType extends CustomGrid {

    private Separator seperator;
    private CustomGrid container;
    private ArrayList<ContactFieldRow> contactFieldRows;
    private boolean isEditable;
    private boolean isVisible;
    private int contact;
    private FieldType type;

    /**
     * Gets all fields from contact with given type and adds them to a list.
     *
     * @param contact Contact to get the fields from.
     * @param type    Field type to get.
     */
    public ContactFieldListType(Contact contact, FieldType type) {
        initialiseComponent();
        this.isVisible = true;
        this.container = new CustomGrid();
        this.contact = contact.getId();
        this.type = type;
        this.contactFieldRows = new ArrayList<>();
        this.seperator = new Separator();
        for (Field field : contact.getFieldsOfType(type)) {
            ContactFieldRow row = this.createRow(field);
            this.contactFieldRows.add(row);
            this.container.addRow(row);
        }
        this.addRow(this.seperator);
        this.addRow(this.container);
        this.setEditable(false);
    }

    /**
     * Checks if the list is empty.
     *
     * @return Is the list empty.
     */
    public boolean isEmpty() {
        return this.contactFieldRows.isEmpty();
    }

    /**
     * Adds a row to the list.
     *
     * @param row Row to add.
     */
    public void add(ContactFieldRow row) {
        this.container.addRow(row);
    }

    /**
     * Gets the type of fields stored in this list.
     *
     * @return Field type
     */
    public FieldType getType() {
        return this.type;
    }

    /**
     * Sets the rows to editable or not.
     *
     * @param editable Are rows supposed to be editable.
     */
    public void setEditable(boolean editable) {
        this.isEditable = editable;
        ArrayList<ContactFieldRow> emptyFieldRows = new ArrayList<>();
        for (ContactFieldRow row : this.contactFieldRows) {
            if (row.isEmpty()) {
                emptyFieldRows.add(row);
            } else {
                row.setEditable(this.isEditable);
            }
        }

        if (this.isEditable) {
            this.addEmptyRow();
        }

        for (ContactFieldRow row : emptyFieldRows)
            this.deleteRow(row);
        this.refreshSeperator();
    }

    /**
     * Makes sure the separator only is visible if the list is not empty.
     */
    private void refreshSeperator() {
        this.seperator.setVisible(this.contactFieldRows.size() > 0 && this.isVisible);
    }

    /**
     * Sets the visibility of the separator.
     *
     * @param visibility Separator visible.
     */
    public void setSeperatorVisibility(boolean visibility) {
        this.isVisible = visibility;
        this.refreshSeperator();
    }

    /**
     * Adds an empty row to the list.
     */
    private void addEmptyRow() {
        ContactFieldRow newFieldRow = this.createRow(new Field(this.contact, this.type));
        Consumer newRowUsed = event -> this.addEmptyRow();

        newFieldRow.setOnKeyReleased(event -> {
            newRowUsed.accept(event);
            newFieldRow.setOnKeyReleased(null);
        });
        newFieldRow.setEditable(true);
        this.contactFieldRows.add(newFieldRow);
        this.container.addRow(newFieldRow);
    }

    /**
     * Deletes a row from the list.
     *
     * @param row Row to delete.
     */
    private void deleteRow(ContactFieldRow row) {
        row.getField().delete();
        this.contactFieldRows.remove(row);
        this.container.remove(row);
    }

    /**
     * Create a new row.
     *
     * @param field Field to create row from.
     * @return The new row.
     */
    private ContactFieldRow createRow(Field field) {
        ContactFieldRow row = new ContactFieldRow(field);
        row.setOnRowDelete(this::deleteRow);
        return row;
    }

}
