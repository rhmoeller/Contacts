package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.util.Style;
import com.hjortsholm.contacts.models.Field;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.function.Consumer;

public class EditableLabel extends TextField {

    private MyEventHandler<String, Field> onTextFieldChanged;
    private Consumer<String> onTextChanged;
    private String promptText;
    private Field field;

//    public EditableLabel() {
//        this("", "", true);
//    }
//
//    public EditableLabel(String text) {
//        this(text, "", true);
//    }

    public EditableLabel(String text, String prompt) {
        this(text, prompt, true);
    }

    public EditableLabel(String text, String prompt, boolean responsive) {
        this.setResponsive(responsive);
        this.setEditable(false);
        this.setPrompt(prompt);
        this.setText(text);

        this.textProperty().addListener(observable -> {
            if (this.onTextChanged != null)
                this.onTextChanged.accept(this.getText());
            if (this.onTextFieldChanged != null && this.field != null)
                this.onTextFieldChanged.accept(this.getText(), this.field);
        });

        Style.addStylesheet(this, "TextFields");
        Style.addGenericStyleClass(this);
    }

    public void setResponsive(boolean responsive) {
        if (responsive) {
            this.textProperty().addListener(this::setToMinimumWidth);
        } else {
            this.textProperty().removeListener(this::setToMinimumWidth);
        }
    }

//    public void toggleEdit() {
//        this.setEdit(!this.isEditable());
//    }

    public void setEdit(boolean editable) {
        this.setEditable(editable);
        if (this.isEditable()) {
            Style.addStyleClass(this, "editable");
        } else {
            Style.removeStyleClass(this, "editable");
        }
    }

    public void setPrompt(String promptText) {
        this.promptText = promptText;
        this.enablePromptText(true);
    }

    public void enablePromptText(boolean enabled) {
        if (enabled) {
            this.setPromptText(this.promptText);
        } else {
            this.setPromptText("");
        }
    }

    public void setToMinimumWidth(Observable observable) {
        Text helper = new Text();
        helper.setText(this.getText().isEmpty() || this.getText().length() < this.getPromptText().length() ? this.getPromptText() : this.getText());
        helper.setFont(this.getFont());
        helper.setWrappingWidth(0);
        helper.setLineSpacing(0);
        helper.setWrappingWidth(Math.ceil(Math.min(helper.prefWidth(-1), 0)));
        this.setPrefWidth(Math.ceil(helper.getLayoutBounds().getWidth()) + 20);
    }

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
        this.field = field;
        this.setPrompt(field.getName().isEmpty() ? field.getType().name().toLowerCase() : field.getName());
        this.setText(field.getValue());
//        this.setOnTextChanged(text -> {
//            if (this.onTextFieldChanged != null)
//                this.onTextFieldChanged.accept(text, this.field);
//        });
    }

    public void setOnTextChanged(Consumer<String> onTextChanged) {
        this.onTextChanged = onTextChanged;
    }

    public void setOnTextFieldChanged(MyEventHandler<String, Field> onTextFieldChanged) {
        this.onTextFieldChanged = onTextFieldChanged;
    }

    public void setFocus() {
        Platform.runLater(() -> {
            if (!this.isFocused()) {
                this.requestFocus();
                this.setFocus();
            }
        });
    }

    @Override
    public String toString() {
        return this.getText().isEmpty() ? this.getPromptText() : this.getText();
    }

    @FunctionalInterface
    public interface MyEventHandler<A, B> {
        void accept(A a, B b);
    }
}
