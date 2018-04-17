package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.gui.style.Style;
import com.hjortsholm.contacts.models.Field;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.function.Consumer;

public class ContactFieldRow extends CustomGrid {
    private EditableLabel name;
    private EditableLabel value;
    private Button deleteRowButton;
    private Field field;
    private boolean isEditable;

    public ContactFieldRow(Field field) {
        initialiseComponent();
        this.isEditable = false;
        this.field = field;

        this.name = new EditableLabel(field.getName(), field.getType().getDefaultName(), false);
        this.name.setPrefWidth(70);
        this.name.setAlignment(Pos.BASELINE_RIGHT);
        this.name.setOnMouseClicked(this::onTextFieldCopy);
        this.name.setOnTextChanged(this::onTextChanged);

        this.value = new EditableLabel(field);
        this.value.setOnMouseClicked(this::onTextFieldCopy);
        this.value.setOnTextChanged(this::onTextChanged);
        if (field.getValue().equals("¿?")) {
            this.value.setText(field.getType().name().toLowerCase());
            this.value.setFocus();
        }

        this.deleteRowButton = new Button();
        this.deleteRowButton.setVisible(false);

        this.addColumn(this.deleteRowButton);
        this.addColumn(this.name);
        this.addColumn(this.value);

        Style.addStyleClass(this.value, "value");
        Style.addStyleClass(this.name, "name");
        Style.addStyleClass(this.deleteRowButton, "row-delete");

    }

    private void onTextFieldCopy(MouseEvent mouseEvent) {
        if (!this.isEditable) {
            Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
            Clipboard systemClipboard = defaultToolkit.getSystemClipboard();
            systemClipboard.setContents(new StringSelection(this.value.getText()), null);
        }
    }

    public void onTextChanged(String text) {
        this.field.setValue(this.value.getText());
        this.field.setName(this.name.getText());
        this.deleteRowButton.setVisible(this.value.isEditable() && !this.isEmpty());

    }

    public void setEditable(boolean editable) {
        this.isEditable = editable;
        if (!editable && this.name.getText().isEmpty() && !this.isEmpty()) {
            this.name.setText(this.name.getPromptText());
            if (!this.field.getName().equalsIgnoreCase(this.name.getText()))
                this.field.setName(this.name.getText());
        }

        if (!this.field.getValue().equalsIgnoreCase(this.value.getText()))
            this.field.setValue(this.value.getText());

        if (editable) {
            this.name.setTooltip(new Tooltip("Click to edit"));
            this.value.setTooltip(new Tooltip("Click to edit"));
            this.deleteRowButton.setTooltip(new Tooltip("Delete row"));
        } else {
            this.name.setTooltip(new Tooltip("Click to copy"));
            this.value.setTooltip(new Tooltip("Click to copy"));
        }

        this.deleteRowButton.setVisible(editable && !this.isEmpty());
        this.value.setEdit(editable);
        this.name.setEdit(editable);

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
}
