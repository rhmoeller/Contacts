package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.FieldType;
import javafx.scene.Node;

import java.util.ArrayList;

public class ContactFieldsList extends CustomGrid {

    private ArrayList<ContactFieldListType> contactFieldsLists;

    public ContactFieldsList() {
        initialiseComponent();
    }

    public void setContact(Contact contact) {
        this.clear();
        System.out.println("Set: "+contact.getFirstName());
        contactFieldsLists = new ArrayList<>();
        for (FieldType fieldType : contact.getAllFieldTypes()) {
            contactFieldsLists.add(new ContactFieldListType(contact, fieldType));
            System.out.println("fieldType: "+ fieldType);
        }

        for (ContactFieldListType fieldList : contactFieldsLists)
            this.addRow(fieldList);
    }

    public void toggleEdit() {
        for (ContactFieldListType fieldList : this.contactFieldsLists) {
            fieldList.toggleEdit();
        }
    }
}
