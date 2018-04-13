package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.gui.util.Style;
import com.hjortsholm.contacts.models.Field;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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
        this.value = new EditableLabel(field.getValue(), field.getPrompt());
        Style.addStyleClass(this.name, "name");
        Style.addStyleClass(this.value, "value");
        this.deleteRowButton = new Button("-");
        this.deleteRowButton.setVisible(false);
        this.value.setOnTextChanged(this::onTextChanged);
        this.name.setOnTextChanged(this::onTextChanged);
        this.name.setPrefWidth(70);
        this.name.setAlignment(Pos.BASELINE_RIGHT);
        this.addColumn(this.deleteRowButton);
        this.addColumn(this.name);
        this.addColumn(this.value);
        this.setOnMouseClicked(event -> {
            if(this.isEditable)
                this.value.requestFocus();
        });

        this.name.setOnMouseClicked(this::onTextFieldCopy);
        this.value.setOnMouseClicked(this::onTextFieldCopy);
    }

    private void onTextFieldCopy(MouseEvent mouseEvent) {
        if (!this.isEditable) {
            Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
            Clipboard systemClipboard = defaultToolkit.getSystemClipboard();
            if (mouseEvent.getTarget().getClass().equals(EditableLabel.class)) {
                systemClipboard.setContents(new StringSelection(((EditableLabel) mouseEvent.getTarget()).getText()), null);
            } else if (mouseEvent.getTarget().getClass().equals(Text.class)) {
                systemClipboard.setContents(new StringSelection(((Text) mouseEvent.getTarget()).getText()), null);
            }
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
            this.name.setTooltip(null);
            this.value.setTooltip(null);
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

    @Override
    public String toString() {
        return this.field.toString();
    }
}
