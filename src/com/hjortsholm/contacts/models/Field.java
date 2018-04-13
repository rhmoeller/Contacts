package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.database.Query;
import com.hjortsholm.contacts.database.TableField;

@TableField(name = "id", type = "VARCHAR", primaryKey = true)
@TableField(name = "contact", type = "VARCHAR", isNullable = false)
@TableField(name = "type", type = "INTEGER", isNullable = false)
@TableField(name = "name", type = "INTEGER", isNullable = false)
@TableField(name = "value", type = "VARCHAR", isNullable = false)
public class Field {


    private int id;
    private int contact;
    private FieldType type;
    private String name;
    private String value;

    public Field(int contact, FieldType type) {
        this(contact, type, "");
    }

    public Field(int contact, FieldType type, String name) {
        this(-1, contact, type, name, "");
    }

    public Field(int id, int contact, FieldType type, String name, String value) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.value = value;
        this.contact = contact;
    }

    public boolean exists() {
        if (this.id != -1)
            return Database.get(new Query().select("id").from("Field").where("id = " + this.id).toString()).size() > 0;
        else
            return false;
    }

    public void create() {
        Object[] fieldData = new Object[]{
                this.getContact(),
                this.getType().getIndex(),
                this.getName().isEmpty() ? this.getType().getDefaultName() : this.getName(),
                this.getValue()
        };

        Database.post(new Query()
                .insertInto("Field")
                .fields("contact", "type", "name", "value")
                .values(fieldData)
                .toString());

        this.id = (int) Database.get(new Query()
                .select("id")
                .from("Field")
                .where("contact = " + fieldData[0],
                        "type = " + fieldData[1],
                        "name = \"" + fieldData[2] + "\"",
                        "value = \"" + fieldData[3] + "\"")
                .toString())
                .last().getColumn("id");
    }

    public boolean update() {
        return Database.post(new Query()
                .update("Field")
                .set("name = \"" + this.getName() + "\"",
                        "value = \"" + this.getValue() + "\"")
                .where("id = " + this.getId())
                .toString());
    }

    public String getName() {
        return this.name.toLowerCase();
    }

    public void setName(String name) {
        this.name = name;
        this.push();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        this.push();

    }

    public FieldType getType() {
        return type;
    }

    public String getPrompt() {
        return this.type.name().toLowerCase();
    }

    public int getContact() {
        return contact;
    }

    public int getId() {
//        if (id == -1) {
//            return this.getNewId();
//        } else {
        return this.id;
//        }
    }

    public boolean isEmpty() {
        return this.getValue().isEmpty();
    }

    private void push() {
        if (this.exists()) {
            this.update();
        } else {
            this.create();
        }
    }

    public void delete() {
        if (this.exists())
            Database.post(new Query()
                    .deleteFrom("Field")
                    .where("id = " + id)
                    .toString());
    }

    @Override
    public String toString() {
        return "Field[" + type + "," + name + "," + value + /*"," + prompt +*/ "]";
    }
}

