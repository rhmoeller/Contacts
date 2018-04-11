package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.models.Field;

public class ContactFieldRow extends CustomGrid {
    private EditableLabel name;
    private EditableLabel value;


    public ContactFieldRow(Field field) {
        initialiseComponent();
        name = new EditableLabel(field.getName(),field.getPrompt());
        value = new EditableLabel(field.getValue(),field.getPrompt());
        this.addColumn(name);
        this.addColumn(value);
    }

    public void toggleEdit() {
        this.name.toggleEdit();
        this.value.toggleEdit();
    }

    public boolean isEmpty() {
        return value.getText().isEmpty();
    }
}
