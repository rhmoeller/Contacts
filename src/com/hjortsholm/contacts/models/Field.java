package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.TableField;

@TableField(name = "id", type = "INTEGER", primaryKey = true)
@TableField(name = "contact", type = "INTEGER", isNullable = false)
@TableField(name = "type", type = "INTEGER", isNullable = false)
@TableField(name = "name", type = "INTEGER", isNullable = false)
@TableField(name = "value", type = "VARCHAR", isNullable = false)
public class Field {

    private int id;
    private Contact contact;
    private FieldType type;
    private String name;
    private String value;
    private String prompt;

    public Field(Contact contact, FieldType type, String name, String value, String prompt) {
        this.contact = contact;
        this.type = type;
        this.name = name;
        this.value = value;
        this.prompt = prompt;
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

    @Override
    public String toString() {
        return "Field[" + type + "," + name + "," + value + "," + prompt + "]";
    }
}

