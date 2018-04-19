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

/**
 * <h1>Contact Field Row</h1>
 * This is a row used in the {@link ContactFieldListType contact field list}.
 * Displays the name and value of a field.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @see Field
 * @see EditableLabel
 * @since 10/04/2018
 */
public class ContactFieldRow extends CustomGrid {
    private EditableLabel name;
    private EditableLabel value;
    private Button deleteRowButton;
    private Field field;
    private boolean isEditable;

    /**
     * Creates fields, binds events and styles controls.
     *
     * @param field Field to create textfields of.
     */
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
        if (field.getValue().equals("::focus")) {
            this.value.setText(" ");
            this.value.setFocus();
        }
        this.value.setOnMouseClicked(this::onTextFieldCopy);
        this.value.setOnTextChanged(this::onTextChanged);

        this.deleteRowButton = new Button();
        this.deleteRowButton.setVisible(false);

        this.addColumn(this.deleteRowButton);
        this.addColumn(this.name);
        this.addColumn(this.value);

        Style.addStyleClass(this.value, "value");
        Style.addStyleClass(this.name, "name");
        Style.addStyleClass(this.deleteRowButton, "row-delete");

    }

    /**
     * Copies the value of the field.
     *
     * @param mouseEvent Mouse click event.
     */
    private void onTextFieldCopy(MouseEvent mouseEvent) {
        if (!this.isEditable) {
            Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
            Clipboard systemClipboard = defaultToolkit.getSystemClipboard();
            systemClipboard.setContents(new StringSelection(this.value.getText()), null);
        }
    }

    /**
     * Sets updates the fields name and value to the textfields values.
     *
     * @param text Text from textfield clicked.
     */
    public void onTextChanged(String text) {
        this.field.setValue(this.value.getText());
        this.field.setName(this.name.getText());
        this.deleteRowButton.setVisible(this.value.isEditable() && !this.isEmpty());

    }

    /**
     * Sets textfields to editable or not.
     *
     * @param editable Editable or not.
     */
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

    /**
     * Gets the field associated with this row.
     *
     * @return Row's field.
     * @see Field
     */
    public Field getField() {
        return this.field;
    }

    /**
     * Checks if the value textfield is empty.
     *
     * @return Is the value textfield empty.
     */
    public boolean isEmpty() {
        return this.value.getText().isEmpty();
    }

    /**
     * Set the row delete event.
     *
     * @param event Row delete event.
     */
    public void setOnRowDelete(Consumer<ContactFieldRow> event) {
        this.deleteRowButton.setOnMouseClicked(e -> event.accept(this));
    }
}
