package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.TableField;

import java.util.ArrayList;
import java.util.Comparator;

@TableField(name = "id", type = "INTEGER", primaryKey = true)
public class Contact {
    private Field id;

    private ArrayList<Field> emails;
    private ArrayList<Field> numbers;
    private ArrayList<Field> dates;
    private ArrayList<Field> addresses;

    private Field firstName;
    private Field lastName;
    private Field nickName;

    private Field notes;

    public Field getId() {
        return this.id;
    }

    public void setFirstName(String firstName) {
        this.firstName = new Field(this, FieldType.NAME, "first",firstName);
    }

    public String getFirstName() {
        return firstName.getValue();
    }

    public Field getLastName() {
        return lastName;
    }

    public Field getNickName() {
        return nickName;
    }

    public ArrayList<Field> getAddresses() {
        return addresses;
    }

    public ArrayList<Field> getDates() {
        return dates;
    }

    public ArrayList<Field> getEmails() {
        return emails;
    }

    public ArrayList<Field> getNumbers() {
        return numbers;
    }

    public Field getNotes() {
        return notes;
    }

    public void setFirstName(Field firstName) {
        this.firstName = firstName;
    }

    public void setLastName(Field lastName) {
        this.lastName = lastName;
    }

    public void setNickName(Field nickName) {
        this.nickName = nickName;
    }

    public void setAddresses(ArrayList<Field> addresses) {
        this.addresses = addresses;
    }

    public void setDates(ArrayList<Field> dates) {
        this.dates = dates;
    }

    public void setEmails(ArrayList<Field> emails) {
        this.emails = emails;
    }

    public void setNumbers(ArrayList<Field> numbers) {
        this.numbers = numbers;
    }

    public void setNotes(Field notes) {
        this.notes = notes;
    }

    //    public static Comparator<Contact> compareBy(FieldType field){
//        return (contact0, contact1) -> {
//            String comparable0, comparable1;
//
//            switch (field) {
//                FieldType.NAME:
//                    return
//            }
//            String StudentName1 = s1.getFirstName();
//            String StudentName2 = s2.getFirstName();
//
//            //ascending order
//            return StudentName1.compareTo(StudentName2);
//
//            //descending order
//            //return StudentName2.compareTo(StudentName1);
//        };
//    }
}
