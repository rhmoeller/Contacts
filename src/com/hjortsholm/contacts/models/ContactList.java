package com.hjortsholm.contacts.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class ContactList {
    private HashMap<String, Contact> contacts;

    public ContactList() {
        this.contacts = new HashMap<>();
    }

    public void addContact(Contact contact) {
        this.contacts.put(contact.getId(), contact);
    }

    public Collection<Contact> getContacts() {
//        ArrayList<Contact> contactsBuffer = new ArrayList<>();
//        contactsBuffer.addAll(this.contacts.values());
        return this.contacts.values();/*contactsBuffer;*/
    }

    public Contact getContact(String id) {
        return this.contacts.get(id);
    }

    public boolean contactExists(Contact contact) {
        return this.contacts.containsValue(contact) || this.contacts.containsKey(contact.getId());
    }

    public Collection<String> getContactIds() {
        return contacts.keySet();
    }

    public Collection<Contact> getContactsWith(String[] fieldValues) {
        Collection<Contact> relevantContacts = new ArrayList<>();
        relevantContacts.addAll(this.getContacts());
        if (fieldValues.length > 0) {
            for (String fieldValue : fieldValues) {
                for (Contact contact : relevantContacts) {
                    if (!contact.hasValue(fieldValue)) {
                        relevantContacts.remove(contact);
                        break;
                    }
                }
            }
        }
        return relevantContacts;
    }


    @Override
    public String toString() {
        return "ContactList" + Arrays.toString(this.contacts.values().toArray());
    }

//    public Addresee searchForContact(String searchWord) {
//        // Just a random list for illustration
//        ArrayList<Addresee> Addresees = new ArrayList<>();
//        Addresees.add(new Addresee());
//        Addresees.add(new Addresee());
//
//        // Search
//        for (Addresee addresee:  Addresees) {
//            if (addresee.hasValue(searchWord)) {
//                return addresee;
//            }
//        }
//        return null;
//    }
}
