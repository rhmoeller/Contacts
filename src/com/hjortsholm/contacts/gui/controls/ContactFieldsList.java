package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.Application;
import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.FieldType;

import java.util.ArrayList;

public class ContactFieldsList extends CustomGrid {

    private ArrayList<ContactFieldListType> contactFieldsLists;

    public ContactFieldsList() {
        initialiseComponent();
        this.contactFieldsLists = new ArrayList<>();
        this.setPrefWidth(Application.getWindowWidth()-180);
    }

    public void setContact(Contact contact) {
        this.clear();
        contactFieldsLists = new ArrayList<>();
//        for (FieldType fieldType : contact.getAllFieldTypes()) {
        for (int i = 0; i < FieldType.values().length; i++) {
            FieldType fieldType = FieldType.valueOf(i);
            if (fieldType != FieldType.NAME) {
                ContactFieldListType fieldList = new ContactFieldListType(contact, fieldType);
                contactFieldsLists.add(fieldList);
                this.addRow(fieldList);
            }
        }
    }

    public void setEditable(boolean editable) {
        for (ContactFieldListType fieldList : this.contactFieldsLists) {
            fieldList.setEditable(editable);
        }
    }
}