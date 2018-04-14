package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.database.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class ContactList {
    private HashMap<Integer, Contact> contacts;

    public ContactList() {
        this.contacts = new HashMap<>();
    }

    public void addContact(Contact contact) {
        this.contacts.put(contact.getId(), contact);
    }

    public Collection<Contact> getContacts() {
        return this.contacts.values();
    }

    public Contact getContact(int id) {
        return this.contacts.get(id);
    }

    public boolean contactExists(Contact contact) {
        return this.contacts.containsValue(contact) || this.contacts.containsKey(contact.getId());
    }

    public Collection<Contact> getContactsWith(String[] fieldValues) {
        Collection<Contact> relevantContacts = new ArrayList<>();
        for (Contact contact : this.getContacts()) {
            if (contact.hasValues(fieldValues))
                relevantContacts.add(contact);
        }
        return relevantContacts;
    }


    @Override
    public String toString() {
        return "ContactList" + Arrays.toString(this.contacts.values().toArray());
    }

    public void remove(Contact contact) {
        contact.delete();
        this.contacts.remove(contact.getId());
    }
}
