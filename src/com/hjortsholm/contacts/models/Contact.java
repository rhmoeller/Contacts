package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.Query;
import com.hjortsholm.contacts.database.QueryRow;
import com.hjortsholm.contacts.database.TableField;
import com.hjortsholm.contacts.util.Future;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@TableField(name = "id", type = "VARCHAR", primaryKey = true)
public class Contact {

    private String id;

    private HashMap<FieldType, HashMap<String, Field>> fields;

    public Contact(String id) {
        this.id = id;
        this.fields = new HashMap<>();
//
//        this.emails = new ArrayList<>();
//        this.numbers = new ArrayList<>();
//        this.dates = new ArrayList<>();
//        this.addresses = new ArrayList<>();
    }


    public String getId() {
        return this.id;
    }

    public HashMap<String, Field> getFieldsByType(FieldType type) {
        if (!this.fields.containsKey(type))
            this.fields.put(type, new HashMap<>());
        return fields.get(type);
    }

    public boolean hasField(String name, FieldType type) {
        if (this.fields.containsKey(type))
            if (this.getFieldsByType(type).containsKey(name))
                return true;
        return false;
    }

    public boolean hasValue(String value) {
        for (HashMap<String, Field> entry : this.getAllFields().values()) {
            for (Field field : entry.values()) {
                if (field.getValue().toLowerCase().contains(value.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public Field getField(String name, FieldType type) {

        return this.hasField(name, type) ? this.getFieldsByType(type).get(name.toLowerCase()) : null;
    }

    public Collection<FieldType> getAllFieldTypes() {

        return this.fields.keySet();
    }

    public HashMap<FieldType, HashMap<String, Field>> getAllFields() {
//        ArrayList<Field> fields = new ArrayList<>();
//        for (HashMap<String,Field> type: this.fields.values()) {
//            for (Field field: type.values()) {
//                fields.add(field);
//            }
//        }
//        return fields;
        return this.fields;
    }

    public void setField(Field field) {
        this.getFieldsByType(field.getType()).put(field.getName(), field);
    }

    public String getFirstName() {
        return this.hasField("first", FieldType.NAME) ? this.getField("first", FieldType.NAME).getValue() : "unknown";
    }

    public void setFirstName(String firstName) {
        this.setField(new Field(this, FieldType.NAME, "first", firstName));
    }

    public String getLastName() {
        return this.hasField("last", FieldType.NAME) ? this.getField("last", FieldType.NAME).getValue() : "unknown";
    }

    public void setLastName(String lastName) {
        this.setField(new Field(this, FieldType.NAME, "last", lastName));
    }

    public String getNickName() {
        return this.hasField("nick", FieldType.NAME) ? this.getField("nick", FieldType.NAME).getValue() : "unknown";
    }

    public void setNickName(String nickName) {
        this.setField(new Field(this, FieldType.NAME, "nick", nickName));
    }

    public HashMap<String, Field> getAddresses() {
        return this.getFieldsByType(FieldType.ADDRESS);
    }

    public HashMap<String, Field> getDates() {
        return this.getFieldsByType(FieldType.DATE);
    }

    public void setDates(Field dates) {
        this.setField(dates);
    }

    public HashMap<String, Field> getEmails() {
        return this.getFieldsByType(FieldType.EMAIL);
    }

    public void setEmails(Field emails) {
        this.setField(emails);
    }

    public HashMap<String, Field> getNumbers() {
        return this.getFieldsByType(FieldType.NUMBER);
    }

    public void setNumbers(Field numbers) {
        this.setField(numbers);
    }

    public String getNotes() {
        return this.getField("notes", FieldType.NOTE).getValue();
    }

    public void setNotes(String notes) {
        this.setField(new Field(this, FieldType.NOTE, "note", notes/*, "notes"*/));
    }

    public void setAddress(Field address) {
        this.setField(address);
    }

//    public Future<Field> getField() {
//        return new Future<>(() -> {
//            QueryRow row = db.getPresent("SELECT * FROM Field WHERE contact = "+this.id).first();
//            return new Field(null, FieldType.NAME, row.getColumn("name").toString(), row.getColumn("value").toString());
//        });
//    }

    public Future<Field> getField(FieldType type, String name) {
        return new Future<>(()->{

            return new Field(type,name);
        });
    }

    public Future<ArrayList<FieldType>> getFieldTypes() {
        return new Future<>(()->{
            ArrayList<FieldType> fieldTypes = new ArrayList<>();
            String query = "SELECT id FROM FieldType";
            return fieldTypes;
        });
    }




    @Override
    public String toString() {
        return "Contact" + /*Arrays.toString(*/this.getAllFields()/*.toArray())*/;
    }
}
