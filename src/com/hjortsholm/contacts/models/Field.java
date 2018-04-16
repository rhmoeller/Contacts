package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.database.Query;
import com.hjortsholm.contacts.database.TableField;

import javax.xml.crypto.Data;

@TableField(name = "id", type = "INTEGER", primaryKey = true, autoincrement = true)
@TableField(name = "contact", type = "INTEGER", isNullable = false)
@TableField(name = "type", type = "INTEGER", isNullable = false)
@TableField(name = "name", type = "VARCHAR", isNullable = false)
@TableField(name = "value", type = "VARCHAR", isNullable = false)
public class Field extends TableModel {


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
//        System.out.println(this);
    }

    private boolean exists() {
        if (this.id != -1)
            return Database.get(new Query().select("id").from(Field.class).where("id = " + this.id)).size() > 0;
        else
            return false;
    }

    private void create() {
        if (!this.isEmpty()) {
            Object[] fieldData = new Object[]{
                    this.getContact(),
                    this.getType().getIndex(),
                    this.getName().isEmpty() ? this.getType().getDefaultName() : this.getName(),
                    this.getValue()
            };

//            Database.insert(this);
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
        return this.id;
    }

    public boolean isEmpty() {
        return this.getValue().isEmpty();
    }

    public void push() {
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
                    .where("id = " + id));
    }

    @Override
    public String toString() {
        return "Field[" + type + ",\"" + name + "\", \"" + value + "\"]";
    }

    public Object[] getValues() {
        return new Object[]{this.getId(), this.getContact(), this.getType().getIndex(), this.getName(), this.getValue()};
    }
}

