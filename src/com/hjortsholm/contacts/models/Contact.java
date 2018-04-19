package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.*;

import java.util.ArrayList;

/**
 * <h1>Contact</h1>
 * A data model that is used as a reference to a contact in the database.
 * Can get all fields related to that contact.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @see Database
 * @see Field
 * @see DataModel
 * @see TableField
 * @since 10/04/2018
 */
@TableField(name = "id", type = "INTEGER", primaryKey = true)
public class Contact extends DataModel implements Comparable<Object> {

    private boolean newContact;
    private int id;

    /**
     * Creates a new contact with a set id.
     *
     * @param id Identifier used in database.
     */
    public Contact(int id) {
        if (id != -1) {
            this.id = id;
            this.newContact = false;
        } else {
            Database.post(new Query()
                    .insertInto("Contact")
                    .defaultValues());
            this.id = (int) Database.get(new Query()
                    .select("id")
                    .from("Contact")
            ).last().getColumn("id");
            this.newContact = true;
        }
    }

    /**
     * Gets the contact's id.
     *
     * @return Contact identifier.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Checks if the contact was created in the database this session.
     *
     * @return Is the contact a new contact.
     */
    public boolean isNewContact() {
        return this.newContact;
    }

    /**
     * Checks if the contact has a field with a specific value.
     *
     * @param value Value to look for in contact.
     * @return Is the contact associated with value.
     */
    private boolean hasValue(String value) {
        return Database.get(new Query()
                .select("id")
                .from(Field.class)
                .where("contact = " + this.getId())
                .and("value LIKE \"%" + value + "%\"")
        ).size() > 0;
    }

    /**
     * Checks if the contact has fields with a specific values.
     *
     * @param values Values to look for in contact.
     * @return Is the contact associated with values.
     */
    public boolean hasValues(String... values) {
        for (String value : values) {
            if (!this.hasValue(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the contacts first name.
     *
     * @return First name.
     * @see Field
     */
    public Field getFirstName() {
        return this.getField(FieldType.NAME, "first");
    }

    /**
     * Gets the contacts last name.
     *
     * @return Last name.
     * @see Field
     */
    public Field getLastName() {
        return this.getField(FieldType.NAME, "last");
    }

    /**
     * Gets the contacts nickname.
     *
     * @return Nick name.
     * @see Field
     */
    public Field getNickName() {
        return this.getField(FieldType.NAME, "nickname");
    }

    /**
     * Gets a specific field from the database that are associated with this contact.
     * Specified by type and name of the field.
     *
     * @param name Name of field to get.
     * @param type Type of field to get.
     * @return Field associated with this contact.
     * @see Field
     */
    public Field getField(FieldType type, String name) {
        QueryRow result = Database.get(new Query()
                .select("*")
                .from(Field.class)
                .where("contact = \"" + this.getId() + "\"")
                .and("type = " + type.getIndex())
                .and("name = \"" + name + "\"")
        ).first();
        Field field;
        if (result != null) {
            field = new Field(
                    (int) result.getColumn("id"),
                    (int) result.getColumn("contact"),
                    FieldType.valueOf((int) result.getColumn("type")),
                    result.getColumn("name").toString(),
                    type == FieldType.NAME && result.getColumn("value").toString().length() > 1 ?
                            result.getColumn("value").toString().substring(0, 1).toUpperCase() + result.getColumn("value").toString().substring(1).toLowerCase() :
                            result.getColumn("value").toString());
        } else {
            field = new Field(-1, this.id, type, name, "");
        }

        return field;
    }

    /**
     * Gets all fields from the database that are associated with this contact and
     * are of a type specified.
     *
     * @param type Type of fields to return.
     * @return Fields of type associated with this contact.
     * @see Field
     */
    public ArrayList<Field> getFieldsOfType(FieldType type) {
        ArrayList<Field> fields = new ArrayList<>();
        QuerySet result = Database.get(new Query()
                .select("id, name, value")
                .from(Field.class)
                .where("contact = \"" + getId() + "\"")
                .and("type = " + type.getIndex()));
        for (QueryRow row : result)
            fields.add(new Field((int) row.getColumn("id"), this.id, type, (String) row.getColumn("name"), (String) row.getColumn("value")));

        return fields;
    }

    /**
     * Check if this contact exists in the database.
     *
     * @return Exists in database.
     */
    public boolean exists() {
        int ID = this.getId();
        return Database.get(new Query()
                .select()
                .from("Contact")
                .where("id = " + ID)
        ).size() > 0;
    }

    /**
     * Gets the contacts display title.
     * If any name is set
     *
     * @return Title the contact is to be displayed with.
     */
    public String getDisplayTitle() {
        String displayTitle = "No name";
        if (!this.getFirstName().isEmpty()) {
            displayTitle = this.getFirstName().getValue();
            if (!this.getLastName().isEmpty()) {
                displayTitle += " " + this.getLastName().getValue();
            }
        } else if (!this.getLastName().isEmpty()) {
            displayTitle = this.getLastName().getValue();
        } else if (!this.getNickName().isEmpty()) {
            displayTitle = this.getNickName().getValue();
        }

        return displayTitle;
    }

    /**
     * Deletes this contact from the database.
     */
    public void delete() {
        Database.post(new Query().deleteFrom("Field").where("contact = " + this.getId()));
        Database.post(new Query().deleteFrom("Contact").where("id = " + this.getId()));
        this.id = -1;
    }

    /**
     * Checks if this contact has the neccessary fields to be handled correctly
     * in this program.
     *
     * @return Is this contact valid.
     */
    public boolean isValid() {
        if (this.exists()) {
            return Database.get(new Query()
                    .select()
                    .from(Field.class)
                    .where("contact = " + this.getId(),
                            "type = " + FieldType.NAME.getIndex(),
                            "name = \"" + FieldType.NAME.getDefaultName() + "\"",
                            "value IS NOT NULL")
            ).size() > 0;
        } else {
            return false;
        }
    }

    /**
     * Compares display title of two contacts to allow sorting by names.
     *
     * @param contact Contact to compare to.
     * @return Position in list compared to each other.
     */
    @Override
    public int compareTo(Object contact) {
        String contactTitle1 = this.getDisplayTitle();
        String contactTitle2 = ((Contact) contact).getDisplayTitle();
        if (contactTitle1 != null && contactTitle2 != null)
            return contactTitle1.compareTo(contactTitle2);
        else
            return -1;
    }

    /**
     * Gets all values for the fields used in the data model's table.
     *
     * @return Data to be saved in database.
     */
    public Object[] getValues() {
        return new Object[]{this.getId()};
    }

    /**
     * Gets the contacts profile picture.
     *
     * @return Profile picture field.
     */
    public Field getProfilePicture() {
        return this.getField(FieldType.PICTURE,"profile");
    }

    /**
     * Gets the contacts first and last name initials.
     *
     * @return Contacts initials.
     */
    public String getInitials() {
        Field firstname = this.getFirstName(),
                lastname = this.getLastName();

        return ((firstname.isEmpty() ? "": Character.toString(firstname.getValue().charAt(0))) + (lastname.isEmpty() ? "": Character.toString(lastname.getValue().charAt(0)))).toUpperCase();
    }
}
