package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.database.Query;
import com.hjortsholm.contacts.database.TableField;

/**
 * <h1>Field</h1>
 * A field stores a one line of information about a {@link Contact contact}.
 * It functions as a data model for the database and the application.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @see Database
 * @see DataModel
 * @see TableField
 * @since 10/04/2018
 */
@TableField(name = "id", type = "INTEGER", primaryKey = true, autoincrement = true)
@TableField(name = "contact", type = "INTEGER", isNullable = false)
@TableField(name = "type", type = "INTEGER", isNullable = false)
@TableField(name = "name", type = "VARCHAR", isNullable = false)
@TableField(name = "value", type = "VARCHAR", isNullable = false)
public class Field extends DataModel {


    private int id;
    private int contact;
    private FieldType type;
    private String name;
    private String value;

    /**
     * Creates a new field with a set {@link FieldType field type} associated with a {@link Contact contact}.
     *
     * @param contact {@link Contact contact} to associate field with.
     * @param type    {@link FieldType Field type} to create field of.
     * @see Contact
     * @see FieldType
     */
    public Field(int contact, FieldType type) {
        this(contact, type, "");
    }

    /**
     * Creates a new field with a set {@link FieldType field type} and name associated with a {@link Contact contact}.
     *
     * @param contact {@link Contact contact} to associate field with.
     * @param type    {@link FieldType Field type} to create field of.
     * @param name    Name to create field with.
     * @see Contact
     * @see FieldType
     */
    public Field(int contact, FieldType type, String name) {
        this(-1, contact, type, name, "");
    }

    /**
     * Creates a field with an id, set {@link FieldType field type}, name and value associated with a {@link Contact contact}.
     *
     * @param id      Id of the this field instance.
     * @param contact {@link Contact contact} to associate field with.
     * @param type    {@link FieldType Field type} to create field of.
     * @param name    Name to create field with.
     * @param value   Value to create field with.
     */
    public Field(int id, int contact, FieldType type, String name, String value) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.value = value;
        this.contact = contact;
    }

    /**
     * Checks if field with this id exists in the database.
     *
     * @return If field exists in database.
     */
    public boolean exists() {
        if (this.id > -1)
            return Database.get(new Query().select("id").from(Field.class).where("id = " + this.id)).size() > 0;
        else
            return false;
    }

    /**
     * Create a new entry in the database with this field.
     */
    private void create() {
        if (!this.isEmpty()) {
            Object[] fieldData = new Object[]{
                    this.getContact(),
                    this.getType().getIndex(),
                    this.getName().isEmpty() ? this.getType().getDefaultName() : this.getName(),
                    this.getValue()
            };

            if (this.id > -1) {
                Database.post(new Query()
                        .insertInto("Field")
                        .fields("id","contact", "type", "name", "value")
                        .values(this.id,fieldData));
            } else {
                Database.post(new Query()
                        .insertInto("Field")
                        .fields("contact", "type", "name", "value")
                        .values(fieldData));


                this.id = (int) Database.get(new Query()
                        .select("id")
                        .from("Field")
                        .where("contact = " + fieldData[0],
                                "type = " + fieldData[1],
                                "name = \"" + fieldData[2] + "\"",
                                "value = \"" + fieldData[3] + "\"")
                ).last().getColumn("id");
            }
        }
    }

    /**
     * Updates this fields database entry to match this instance.
     */
    private void update() {
        if (this.isEmpty()) {
            Database.post(new Query()
                    .deleteFrom("Field")
                    .where("id = " + this.getId()));
        } else {
            Database.post(new Query()
                    .update("Field")
                    .set("name = \"" + this.getName() + "\"",
                            "value = \"" + this.getValue() + "\"")
                    .where("id = " + this.getId()));
        }
    }

    /**
     * Gets the name of this field.
     *
     * @return Name of field.
     */
    public String getName() {
        return this.name.toLowerCase();
    }

    /**
     * Sets the name of this field.
     *
     * @param name Name to set.
     */
    public void setName(String name) {
        this.name = name;
        this.push();
    }

    /**
     * Gets the value of this field.
     *
     * @return Value of field.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of this field.
     *
     * @param value Value to set.
     */
    public void setValue(String value) {
        this.value = value;
        this.push();

    }

    /**
     * Gets the type of the field.
     *
     * @return Type of field.
     * @see FieldType
     */
    public FieldType getType() {
        return type;
    }

    /**
     * Gets the prompt text of field.
     *
     * @return Prompt text
     */
    public String getPrompt() {
        return this.type.name().toLowerCase();
    }

    /**
     * Gets the contact id associated with this field.
     *
     * @return Associated contact's id.
     */
    public int getContact() {
        return contact;
    }

    /**
     * Gets this fields id.
     *
     * @return Field id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Checks if this field's value is empty.
     *
     * @return Is field value empty.
     */
    public boolean isEmpty() {
        return this.getValue().isEmpty();
    }

    /**
     * Submits this field's values to the database.
     *
     * @see Database
     */
    public void push() {
        if (this.exists()) {
            this.update();
        } else {
            this.create();
        }
    }

    /**
     * Deletes this field from the database.
     *
     * @see Database
     */
    public void delete() {
        if (this.exists())
            Database.post(new Query()
                    .deleteFrom("Field")
                    .where("id = " + id));
    }

    /**
     * Gets all values from this field to submit to databse.
     *
     * @return List of all values this data model contains.
     * @see DataModel
     */
    public Object[] getValues() {
        return new Object[]{this.getId(), this.getContact(), this.getType().getIndex(), this.getName(), this.getValue()};
    }
}

