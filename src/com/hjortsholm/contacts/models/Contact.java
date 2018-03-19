package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.TableField;

import java.util.ArrayList;
import java.util.Comparator;

public class Contact {
    @TableField(name = "id", type = "INTEGER")
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
