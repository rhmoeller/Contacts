package com.hjortsholm.contacts.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * <h1>Contact List</h1>
 * A list of contacts with tailored interaction.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @see Contact
 * @since 10/04/2018
 */
public class ContactList {
    private HashMap<Integer, Contact> contacts;

    /**
     * Initialises the needed variable.
     */
    public ContactList() {
        this.contacts = new HashMap<>();
    }

    /**
     * Adds a contact to the list.
     *
     * @param contact Contact to add.
     * @see Contact
     */
    public void addContact(Contact contact) {
        this.contacts.put(contact.getId(), contact);
    }

    /**
     * Gets all contacts from the list.
     *
     * @return A list of contacts
     * @see Contact
     */
    public Collection<Contact> getContacts() {
        return this.contacts.values();
    }

    /**
     * Gets one contact from the list specified by id.
     *
     * @param id The contacts id you want to get.
     * @return Contact you are searching for.
     * @see Contact
     */
    public Contact getContact(int id) {
        return this.contacts.get(id);
    }

    /**
     * Checks if a specific contact exists in the list.
     *
     * @param contact Contact to look for.
     * @return Contact exists.
     * @see Contact
     */
    public boolean contactExists(Contact contact) {
        return this.contacts.containsValue(contact) || this.contacts.containsKey(contact.getId());
    }

    /**
     * Gets all contacts with a specific set of values associated with them.
     *
     * @param fieldValues Set of values to look for in a contact.
     * @return A list of contacts that has mentioned values.
     * @see Contact
     */
    public Collection<Contact> getContactsWith(String[] fieldValues) {
        Collection<Contact> relevantContacts = new ArrayList<>();
        for (Contact contact : this.getContacts()) {
            if (contact.hasValues(fieldValues))
                relevantContacts.add(contact);
        }
        return relevantContacts;
    }

    /**
     * Removes one specific contact from the list.
     *
     * @param contact Contact to remove.
     * @see Contact
     */
    public void remove(Contact contact) {
        this.contacts.remove(contact.getId());
        contact.delete();
    }
}
