package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.models.Field;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;

public class ContactFieldRow extends CustomGrid {
    private EditableLabel name;
    private EditableLabel value;
    private Button deleteRowButton;
    private Field field;

    public ContactFieldRow(Field field) {
        initialiseComponent();
        this.field = field;
        this.name = new EditableLabel(field.getName(), field.getType().getDefaultName(), false);
        this.value = new EditableLabel(field.getValue(), field.getPrompt());
        this.deleteRowButton = new Button("-");
        this.deleteRowButton.setVisible(false);
        this.value.setOnTextChanged(this::onTextChanged);
        this.name.setOnTextChanged(this::onTextChanged);
        this.name.setPrefWidth(70);
        this.name.setAlignment(Pos.BASELINE_RIGHT);
        this.addColumn(this.deleteRowButton);
        this.addColumn(this.name);
        this.addColumn(this.value);
    }

    public void onTextChanged(String text) {
        this.field.setValue(this.value.getText());
        this.field.setName(this.name.getText());
        System.out.println("editing: "+this);
        this.deleteRowButton.setVisible(!this.value.isEditable() && !this.isEmpty());

    }

    public void setEditable(boolean editable) {
        if (this.name.isEditable() && this.name.getText().isEmpty() && !this.isEmpty()) {
            this.name.setText(this.name.getPromptText());
            if (!this.field.getName().equalsIgnoreCase(this.name.getText()))
                this.field.setName(this.name.getText());
        }

        if (!this.field.getValue().equalsIgnoreCase(this.value.getText()))
            this.field.setValue(this.value.getText());

        this.deleteRowButton.setVisible(!this.value.isEditable() && !this.isEmpty());
        this.value.toggleEdit();
        this.name.toggleEdit();

    }

    public Field getField() {
        return this.field;
    }

    public boolean isEmpty() {
        return this.value.getText().isEmpty();
    }

    public void setOnRowDelete(Consumer<ContactFieldRow> event) {
        this.deleteRowButton.setOnMouseClicked(e -> event.accept(this));
    }

    @Override
    public String toString() {
        return this.field.toString();
    }
}
