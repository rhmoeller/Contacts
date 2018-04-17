package com.hjortsholm.contacts.models;

/**
 * <h1>Data Model</h1>
 * A interface that helps automate insertion of objects in to the database.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @see Contact
 * @see Field
 * @since 10/04/2018
 */
public abstract class DataModel {
    /**
     * Return all values needed to insert or update data from instance.
     *
     * @return Array of objects specified in respective class.
     */
    public abstract Object[] getValues();
}
