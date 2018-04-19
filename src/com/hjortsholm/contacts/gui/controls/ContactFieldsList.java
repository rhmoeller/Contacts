package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;

import java.util.ArrayList;

/**
 * <h1>Contact Field List</h1>
 * A custom list containing a contact's fields grouped by type.
 *
 * @author robertmoller
 * @version 1.0
 * @see Contact
 * @see Field
 * @see FieldType
 * @since 2018-04-14
 */
public class ContactFieldsList extends CustomGrid {

    private ArrayList<ContactFieldListType> contactFieldsLists;

    /**
     * Initialise component and create empty list.
     */
    public ContactFieldsList() {
        initialiseComponent();
        this.contactFieldsLists = new ArrayList<>();
    }

    /**
     * Creates the list of fields associated with given contact.
     *
     * @param contact Contact to get fields from.
     */
    public void setContact(Contact contact) {
        this.clear();
        this.contactFieldsLists = new ArrayList<>();
        boolean isFirstList = false;
        for (int i = 0; i < FieldType.values().length; i++) {
            FieldType fieldType = FieldType.valueOf(i);
            if (fieldType != FieldType.NAME && fieldType != FieldType.PICTURE) {
                ContactFieldListType fieldList = new ContactFieldListType(contact, fieldType);
                fieldList.setSeperatorVisibility(this.contactFieldsLists.size() > 0);
                if (!fieldList.isEmpty() && !isFirstList) {
                    isFirstList = true;
                    fieldList.setSeperatorVisibility(false);
                }

                this.contactFieldsLists.add(fieldList);

                this.addRow(fieldList);
            }
        }
    }

    /**
     * Sets the every field to editable or not.
     *
     * @param editable Should fields be editable.
     */
    public void setEditable(boolean editable) {
        for (ContactFieldListType fieldList : this.contactFieldsLists) {
            fieldList.setEditable(editable);
        }
    }

    /**
     * Adds a row to the respective list with the same type.
     *
     * @param row Row to add.
     */
    public void add(ContactFieldRow row) {
        for (ContactFieldListType fieldList : this.contactFieldsLists) {
            if (fieldList.getType().equals(row.getField().getType())) {
                fieldList.add(row);
            }
        }
    }
}
