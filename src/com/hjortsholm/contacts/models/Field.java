package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.TableField;

public class Field {

    @TableField(name = "id", type = "INTEGER", primaryKey = true)
    private int id;
    @TableField(name = "contact", type = "INTEGER")
    private Contact contact;
    @TableField(name = "type", type = "INTEGER")
    private FieldType type;
    @TableField(name = "name", type = "INTEGER")
    private String name;
    @TableField(name = "value", type = "INTEGER")
    private String value;


    public Field(Contact contact, FieldType type, String name, String value) {
        this.contact = contact;
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public FieldType getType() {
        return type;
    }
}

