package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.*;

import java.util.ArrayList;
import java.util.Arrays;

@TableField(name = "id", type = "INTEGER", primaryKey = true)
public class Contact {

    private int id;

    public Contact(int id) {
        this.id = id;
//        this.setFirstName(this.getField(FieldType.NAME, "first"));
//        this.setLastName(this.getField(FieldType.NAME, "last"));
    }


    public int getId() {
        return this.id;
    }

//    public boolean hasField(FieldType type, String name) {
//        return Database.get(new Query()
//                .select("id")
//                .from(Field.class)
//                .where("contact = \"" + getId() + "\"")
//                .and("value = \"" + value + "\"")
//                .toString()).size() > 0;
//    }

    public boolean hasValue(String value) {
        return Database.get(new Query()
                .select("id")
                .from(Field.class)
                .where("contact = \"" + getId() + "\"")
                .and("value LIKE \"%" + value + "%\" --case-insensitive")
                .toString()).size() > 0;
    }

//    public void valueMatch(String... values) {
//        Query query = new Query();
////                .select("COUNT(contact)")
////                .from(Field.class)
////                .where("contact = \""+getId()+"\"");
//////                .and();
//    }


    public void setField(Field field) {
//        this.getFieldsByType(field.getType()).put(field.getName(), field);
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
                .where("contact = \"" + getId() + "\"")
                .and("type = " + type.getIndex())
                .and("name = \"" + name + "\"")
                .toString()).first();


        return result != null ?
                new Field(
                        (int) result.getColumn("id"),
                        (int) result.getColumn("contact"),
                        FieldType.valueOf((int) result.getColumn("type")),
                        (String) result.getColumn("name"),
                        (String) result.getColumn("value")):
                new Field(id,this.id,type,name,"");
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

//    public ArrayList<FieldType> getFieldTypes() {
//        ArrayList<FieldType> fieldTypes = new ArrayList<>();
//        QuerySet result = Database.get(new Query()
//                .select("DISTINCT type")
//                .from("Field")
//                .where("contact = \"" + getId() + "\"")
//                .toString());
//        for (QueryRow row : result) {
//            fieldTypes.add(FieldType.valueOf((int) row.getColumn("type")));
//        }
//        return fieldTypes;
//    }

    public ArrayList<FieldType> getAllFieldTypes() {
        ArrayList<FieldType> fieldTypes = new ArrayList<>();
        QuerySet result = Database.get(new Query()
                .select("*")
                .from("FieldType")
                .toString());

        for (QueryRow row : result)
            fieldTypes.add(FieldType.valueOf((int) row.getColumn("id")));
        return fieldTypes;
    }

    public ArrayList<Field> getAllFields() {
        ArrayList<Field> fields = new ArrayList<>();
        for (FieldType type : this.getAllFieldTypes()) {
            fields.addAll(this.getFieldsOfType(type));
        }
        return fields;
    }

    @Override
    public String toString() {
        return "Contact" + Arrays.toString(this.getAllFields().toArray());
    }
}
