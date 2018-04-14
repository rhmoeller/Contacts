package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;
import javafx.scene.control.Separator;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ContactFieldListType extends CustomGrid {

    private Separator seperator;
    private CustomGrid container;
    private ArrayList<ContactFieldRow> contactFieldRows;
    private boolean isEditable;
    private int contact;
    private FieldType type;

    public ContactFieldListType(Contact contact, FieldType type) {
        initialiseComponent();
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
        this.refreshSeperator();

    }

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

    private void refreshSeperator() {
        this.seperator.setVisible(this.contactFieldRows.size() > 0);
    }

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

    private void deleteRow(ContactFieldRow row) {
        row.getField().delete();
        this.contactFieldRows.remove(row);
        this.container.remove(row);
    }

    private ContactFieldRow createRow(Field field) {
        ContactFieldRow row = new ContactFieldRow(field);
        row.setOnRowDelete(this::deleteRow);
        return row;
    }

}
