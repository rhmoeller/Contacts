package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.*;

import java.util.ArrayList;

@TableField(name = "id", type = "INTEGER", primaryKey = true)
public class Contact implements Comparable {

    private boolean newContact;
    private int id;

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


    public int getId() {
        return this.id;
    }

    public boolean isNewContact() {
        return this.newContact;
    }

    public boolean hasValue(String value) {
        return Database.get(new Query()
                .select("id")
                .from(Field.class)
                .where("contact = " + this.getId())
                .and("value LIKE \"%" + value + "%\"")
        ).size() > 0;
    }

    public boolean hasValues(String... values) {
        for (String value : values) {
            if (!this.hasValue(value)) {
                return false;
            }
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
        return this.getField(FieldType.NAME, "nickname");
    }

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

    public boolean exists() {
        return Database.get(new Query()
                .select()
                .from("Contact")
                .where("id = " + this.getId())
        ).size() > 0;
    }

    public String getDisplayTitle() {
        String displayTitle = null;
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

    public void delete() {
        Database.post(new Query().deleteFrom("Field").where("contact = " + this.getId()));
        Database.post(new Query().deleteFrom("Contact").where("id = " + this.getId()));
        this.id = -1;
    }

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

    @Override
    public int compareTo(Object contact) {
        String contactTitle = ((Contact) contact).getDisplayTitle();
        return this.getDisplayTitle().compareTo(contactTitle);
    }

    @Override
    public String toString() {
        return "Contact[" + this.getId() + "]";
    }

}
