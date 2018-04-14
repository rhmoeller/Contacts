package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.*;

import java.util.ArrayList;

@TableField(name = "id", type = "INTEGER", primaryKey = true)
public class Contact {

    private int id;

    public Contact(int id) {
        if (id != -1) {
            this.id = id;
        } else {
            Database.post(new Query()
                    .insertInto("Contact")
                    .defaultValues()
                    .toString());
            this.id = (int) Database.get(new Query()
                    .select("id")
                    .from("Contact")
                    .toString())
                    .last()
                    .getColumn("id");
        }
    }


    public int getId() {
        return this.id;
    }

    public boolean hasValue(String value) {
        boolean foo = Database.get(new Query()
                .select("id")
                .from(Field.class)
                .where("contact = " + this.getId())
                .and("value LIKE \"%" + value + "%\"")
                .toString()).size() > 0;
        System.out.println(this + " have" + (foo ? "" : "n't ") + " got " + value);
        return foo;
    }

    public boolean hasValues(String... values) {
        for (String value : values) {
            if (!this.hasValue(value))
                return false;
        }
        return true;
    }

    public Field getFirstName() {
        return this.getField(FieldType.NAME, "first");
    }


    public Field getLastName() {
        return this.getField(FieldType.NAME, "last");
    }


    public Field getNickName() {
        return this.getField(FieldType.NAME, "nick");
    }

    public Field getField(FieldType type, String name) {
        QueryRow result = Database.get(new Query()
                .select("*")
                .from(Field.class)
                .where("contact = \"" + this.getId() + "\"")
                .and("type = " + type.getIndex())
                .and("name = \"" + name + "\"")
                .toString()).first();


        return result != null ?
                new Field(
                        (int) result.getColumn("id"),
                        (int) result.getColumn("contact"),
                        FieldType.valueOf((int) result.getColumn("type")),
                        (String) result.getColumn("name"),
                        (String) result.getColumn("value")) :
                new Field(-1, this.id, type, name, "");
    }


    public ArrayList<Field> getFieldsOfType(FieldType type) {
        ArrayList<Field> fields = new ArrayList<>();
        QuerySet result = Database.get(new Query()
                .select("id, name, value")
                .from(Field.class)
                .where("contact = \"" + getId() + "\"")
                .and("type = " + type.getIndex())
                .toString());
        for (QueryRow row : result)
            fields.add(new Field((int) row.getColumn("id"), this.id, type, (String) row.getColumn("name"), (String) row.getColumn("value")));

        return fields;
    }

    public boolean exists() {
        return Database.get(new Query().select("*").from("Contact").where("id = " + this.getId()).toString()).size() > 0;
    }

    public String getDisplayTitle() {
        String displayTitle = null;
        if (!this.getFirstName().isEmpty())
            displayTitle = this.getFirstName().getValue();
        else if (!this.getLastName().isEmpty())
            displayTitle = this.getLastName().getValue();
        else if (!this.getNickName().isEmpty())
            displayTitle = this.getNickName().getValue();
        return displayTitle;
    }

    public void delete() {
        Database.post(new Query().deleteFrom("Field").where("contact = " + this.getId()).toString());
        Database.post(new Query().deleteFrom("Contact").where("id = " + this.getId()).toString());
        this.id = -1;
    }

    @Override
    public String toString() {
        return "Contact[" + this.getId() + "]";
    }
}
