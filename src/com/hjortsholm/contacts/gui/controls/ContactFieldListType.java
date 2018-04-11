package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;
import javafx.scene.control.Separator;

import java.util.ArrayList;

public class ContactFieldListType extends CustomGrid {

    private ArrayList<ContactFieldRow> contactFieldRows;
    private ContactFieldRow temperaryFieldRow;
    private boolean isEditable;

    public ContactFieldListType(Contact contact, FieldType type) {
        initialiseComponent();
        this.isEditable = false;
        this.contactFieldRows = new ArrayList<>();
        this.addRow(new Separator());
        for (Field field : contact.getFieldsByType(type).values())
            this.contactFieldRows.add(new ContactFieldRow(field));
        for (ContactFieldRow row : contactFieldRows)
            this.addRow(row);
    }

    public void toggleEdit() {
        this.isEditable = !this.isEditable;
        ArrayList<ContactFieldRow> emptyFieldRows = new ArrayList<>();
        for (ContactFieldRow row : this.contactFieldRows) {
            if (row.isEmpty()) {
                emptyFieldRows.add(row);
            } else {
                row.toggleEdit();
            }
        }

        if (this.isEditable) {
            ContactFieldRow newFieldRow = new ContactFieldRow(new Field(FieldType.NAME, "fist"));
            newFieldRow.toggleEdit();
            this.contactFieldRows.add(newFieldRow);
            this.addRow(newFieldRow);
        } else {
//            Save any changes
        }

        for (ContactFieldRow row : emptyFieldRows) {
            this.contactFieldRows.remove(row);
            this.remove(row);
        }
    }

}
