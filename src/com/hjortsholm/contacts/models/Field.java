package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.database.Query;
import com.hjortsholm.contacts.database.TableField;
import com.hjortsholm.contacts.util.StringUtils;

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

    private boolean changed;

    public Field(int contact, FieldType type) {
        this(contact, type, "");
    }

    public Field(int contact, FieldType type, String name) {
        this(-1, contact, type, name, "");
    }

    public Field(int id, int contact, FieldType type, String name, String value) {
        this.type = type;
        this.name = StringUtils.formalise(name);
        this.value = StringUtils.formalise(value);
        this.id = id;

    }

//    public boolean hasChanged() {
//        return changed;
//    }

    public boolean exists() {
        if (this.id == -1)
            return Database.get(new Query().select("id").from("Field").where("id = " + this.id).toString()).size() > 0;
        else
            return true;
    }

    public boolean create() {
//        if (!this.exists()) {
        int id = (int) Database.get(new Query()
                .select("COUNT(id)+1 id")
                .from("Field")
                .toString())
                .first()
                .getColumn("id");
        if (id != -1) {
            if (Database.post(new Query()
                    .insert("Field")
                    .values(id,
                            this.getContact(),
                            this.getType().getIndex(),
                            this.getName(),
                            this.getValue())
                    .toString())) {
                this.id = id;
                return true;
            }
        }
//        }
        return false;
    }

    public boolean update() {
        return Database.post("UPDATE Field SET name = \"" + this.name + "\" AND value = \"" + this.value + "\" WHERE id = " + this.id);
    }

    public String getName() {
        return name;
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
        return this.type.getName();
    }

    public int getContact() {
        return contact;
    }

    public int getId() {
        return id;
    }

    public boolean isEmpty() {
        return this.getValue().isEmpty();
    }

    private void push() {
        if (this.exists()) {
            System.out.println("Update " + (this.update() ? "successfull" : "failed"));
        } else {
            System.out.println("Create " + (this.create() ? "successfull" : "failed"));

        }
    }

    public void delete() {
        if (this.exists())
            return;
        // Delete from db
    }

    @Override
    public String toString() {
        return "Field[" + type + "," + name + "," + value + /*"," + prompt +*/ "]";
    }
}

