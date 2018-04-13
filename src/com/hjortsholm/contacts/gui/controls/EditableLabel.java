package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.util.Style;
import com.hjortsholm.contacts.models.Field;
import javafx.beans.Observable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.function.Consumer;

public class EditableLabel extends TextField {

    //    private String promptText;
    private Consumer<String> onTextChanged;

    public EditableLabel() {
        this("", "", true);
    }

    public EditableLabel(String text) {
        this(text, "", true);
    }

    public EditableLabel(String text, String prompt) {
        this(text, prompt, true);
    }

    public EditableLabel(String text, String prompt, boolean responsive) {
        this.setResponsive(responsive);
        this.setEditable(false);
        this.setPromptText(prompt);
        this.setText(text);
        this.textProperty().addListener(observable -> {
            if (this.onTextChanged != null)
                this.onTextChanged.accept(this.getText());
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

    public void toggleEdit() {
        this.setEdit(!this.isEditable());
    }

    public void setEdit(boolean editable) {
        this.setEditable(editable);
        if (this.isEditable()) {
            Style.addStyleClass(this, "editable");
        } else {
            Style.removeStyleClass(this, "editable");
        }
    }


    private void setToMinimumWidth(Observable observable) {
        Text textHelper = new Text();
        textHelper.setText(this.getText().isEmpty() || this.getText().length() < this.getPromptText().length() ? this.getPromptText() : this.getText());
        textHelper.setFont(this.getFont());
        textHelper.setWrappingWidth(0);
        textHelper.setLineSpacing(0);
        double minimumWidth = Math.min(textHelper.prefWidth(-1), 0);
        textHelper.setWrappingWidth(Math.ceil(minimumWidth));
        this.setPrefWidth(Math.ceil(textHelper.getLayoutBounds().getWidth()) + 20);
    }

//    public void setPrompt(String promptText) {
//        this.setPromptText(promptText);
//        this.promptText = promptText;
//    }

    public void setField(Field field) {
        this.setText(field.getValue());
        this.setPromptText(field.getName());
    }

    public void setOnTextChanged(Consumer<String> onTextChanged) {
        this.onTextChanged = onTextChanged;
    }

    @Override
    public String toString() {
        return this.getText().isEmpty() ? this.getPromptText() : this.getText();
    }
}
