package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.TableField;

@TableField(name = "id", type = "INTEGER", primaryKey = true)
@TableField(name = "contact", type = "INTEGER", isNullable = false)
@TableField(name = "type", type = "INTEGER", isNullable = false)
@TableField(name = "name", type = "INTEGER", isNullable = false)
@TableField(name = "value", type = "VARCHAR", isNullable = false)
public class Field {


    private int id;
    private int contact;
    private FieldType type;
    private String name;
    private String value;

    private boolean changed;

    public Field(FieldType type, String name) {
        this(-1, type, name, "");
    }

    public Field(int id, FieldType type, String name, String value/*, String prompt*/) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.value = value;
        this.changed = false;
    }

    public boolean hasChanged() {
        return changed;
    }

    public void save() {
        String query = "UPDATE Field" +
                "SET name = ?, value = ?" +
                "WHERE id = ?";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.changed = true;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        this.changed = true;
    }

    public FieldType getType() {
        return type;
    }

    public String getPrompt() {
        return this.type.name();
    }

    public int getContact() {
        return contact;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Field[" + type + "," + name + "," + value + /*"," + prompt +*/ "]";
    }
}

