package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.TableField;

import java.util.*;

@TableField(name = "id", type = "VARCHAR", primaryKey = true)
public class Contact {

    private String id;

    private HashMap<FieldType,HashMap<String,Field>> fields;

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
            this.fields.put(type,new HashMap<>());
        return fields.get(type);
    }

    public boolean hasField(String name, FieldType type) {
        if (this.fields.containsKey(type))
            if (this.getFieldsByType(type).containsKey(name))
                return true;
        return false;
    }

    public boolean hasValue(String value) {
        for (Field field : this.getAllFields()) {
            if (field.getValue().toLowerCase().contains(value.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public Field getField(String name, FieldType type) {

        return this.hasField(name,type)? this.getFieldsByType(type).get(name.toLowerCase()): null;
    }

    public Collection<Field> getAllFields() {
        ArrayList<Field> fields = new ArrayList<>();
        for (HashMap<String,Field> type: this.fields.values()) {
            for (Field field: type.values()) {
                fields.add(field);
            }
        }
        return fields;
    }

    public void setField(Field field) {
        this.getFieldsByType(field.getType()).put(field.getName(),field);
    }

    public String getFirstName() {
        return this.hasField("first", FieldType.NAME)?this.getField("first",FieldType.NAME).getValue():"unknown";
    }

    public String getLastName() {
        return this.hasField("last", FieldType.NAME)?this.getField("last",FieldType.NAME).getValue():"unknown";
    }

    public String getNickName() {
        return this.hasField("nick", FieldType.NAME)?this.getField("nick",FieldType.NAME).getValue():"unknown";
    }

    public HashMap<String,Field> getAddresses() {
        return this.getFieldsByType(FieldType.ADDRESS);
    }

    public HashMap<String,Field> getDates() {
        return this.getFieldsByType(FieldType.DATE);
    }

    public HashMap<String,Field> getEmails() {
        return this.getFieldsByType(FieldType.EMAIL);
    }

    public HashMap<String,Field> getNumbers() {
        return this.getFieldsByType(FieldType.NUMBER);
    }

    public String getNotes() {
        return this.getField("notes",FieldType.NOTE).getValue();
    }

    public void setFirstName(String firstName) {
       this.setField(new Field(this, FieldType.NAME,"first",firstName,"first"));
    }

    public void setLastName(String lastName) {
        this.setField(new Field(this, FieldType.NAME,"last",lastName,"last"));
    }

    public void setNickName(String nickName) {
        this.setField(new Field(this, FieldType.NAME,"nick",nickName,"nick"));
    }

    public void setAddress(Field address) {
        this.setField(address);
    }

    public void setDates(Field dates) {
        this.setField(dates);
    }

    public void setEmails(Field emails) {
        this.setField(emails);
    }

    public void setNumbers(Field numbers) {
        this.setField(numbers);
    }

    public void setNotes(String notes) {
        this.setField(new Field(this,FieldType.NOTE,"note",notes,"notes"));
    }

    @Override
    public String toString() {
        return "Contact"+ Arrays.toString(this.getAllFields().toArray());
    }
}
