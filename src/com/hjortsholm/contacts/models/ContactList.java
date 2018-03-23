package com.hjortsholm.contacts.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.sql.*;

public class ContactList {
   private HashMap<String,Contact> contacts;

   public ContactList() {
      this.contacts = new HashMap<>();
   }

   public void addContact(Contact contact) {
       this.contacts.put(contact.getId(),contact);
   }

    public Collection<Contact> getContacts() {
        return contacts.values();
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
}
