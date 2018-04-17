package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.style.Style;
import com.hjortsholm.contacts.models.Field;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.function.Consumer;

/**
 * <h1>Editable Label</h1>
 * Text field that works as both a label and text field.
 *
 * @author robertmoller
 * @version 1.0
 * @see Field
 * @since 2018-04-14
 */
public class EditableLabel extends TextField {

    private MyEventHandler<String, Field> onTextFieldChanged;
    private Consumer<String> onTextChanged;
    private String promptText;
    private Field field;

    /**
     * Creates EditableLabel from the values of a field.
     *
     * @param field Field to base text field on.
     */
    public EditableLabel(Field field) {
        this(field.getValue(), field.getPrompt());
        this.field = field;
    }

    /**
     * Creates EditableLabel with a set text and prompt text.
     *
     * @param text   Textfields text.
     * @param prompt Textfields prompt text.
     */
    public EditableLabel(String text, String prompt) {
        this(text, prompt, true);
    }

    /**
     * Creates EditableLabel with a set text and prompt text.
     *
     * @param text       Textfields text.
     * @param prompt     Textfields prompt text.
     * @param responsive Should the textfield grow and shrink in width with it's contents.
     */
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

    /**
     * Sets the textfields ability to grow and shrink with it's contents.
     *
     * @param responsive Should the textfield grow and shrink in width with it's contents.
     */
    public void setResponsive(boolean responsive) {
        if (responsive) {
            this.textProperty().addListener(this::setToMinimumWidth);
        } else {
            this.textProperty().removeListener(this::setToMinimumWidth);
        }
    }

    /**
     * Sets the texfield to editable or not.
     *
     * @param editable Should the textfield be editable.
     */
    public void setEdit(boolean editable) {
        this.setEditable(editable);
        if (this.isEditable()) {
            Style.addStyleClass(this, "editable");
        } else {
            Style.removeStyleClass(this, "editable");
        }
    }

    /**
     * Sets the prompt text of the textfield.
     *
     * @param promptText Text to set as prompt text.
     */
    public void setPrompt(String promptText) {
        this.promptText = promptText;
        this.enablePromptText(true);
    }

    /**
     * Enables or disables the display of the prompt text.
     *
     * @param enabled Should prompt text be enabled.
     */
    public void enablePromptText(boolean enabled) {
        if (enabled) {
            this.setPromptText(this.promptText);
        } else {
            this.setPromptText("");
        }
    }

    /**
     * Sets the textfield to the smallest width it can have without hiding content.
     *
     * @param observable Textfields observable text property.
     */
    public void setToMinimumWidth(Observable observable) {
        Text helper = new Text();
        if (this.getText().isEmpty() || this.getText().length() < this.getPromptText().length()) {
            helper.setText(this.getPromptText());
        } else {
            helper.setText(this.getText());
        }
        helper.setFont(this.getFont());
        helper.setWrappingWidth(0);
        helper.setLineSpacing(0);
        helper.setWrappingWidth(Math.ceil(Math.min(helper.prefWidth(-1), 0)));
        this.setPrefWidth(Math.ceil(helper.getLayoutBounds().getWidth()) + 20);
    }

    /**
     * Gets the associated field.
     *
     * @return Associated field.
     * @see Field
     */
    public Field getField() {
        return this.field;
    }

    /**
     * Sets the associated field.
     *
     * @param field Field to associate with this textfield.
     * @see Field
     */
    public void setField(Field field) {
        this.field = field;
        this.setPrompt(field.getName().isEmpty() ? field.getType().name().toLowerCase() : field.getName());
        this.setText(field.getValue());
    }

    /**
     * Sets the text changed event.
     *
     * @param onTextChanged Text changed event.
     */
    public void setOnTextChanged(Consumer<String> onTextChanged) {
        this.onTextChanged = onTextChanged;
    }

    /**
     * Sets the field text changed event.
     *
     * @param onTextFieldChanged Field text changed event.
     */
    public void setOnTextFieldChanged(MyEventHandler<String, Field> onTextFieldChanged) {
        this.onTextFieldChanged = onTextFieldChanged;
    }

    /**
     * Sets the window focus to this control.
     */
    public void setFocus() {
        Platform.runLater(() -> {
            if (!this.isFocused()) {
                this.requestFocus();
                this.setFocus();
            }
        });
    }

    /**
     * Functional interface used for anonymous functions with two parameters.
     *
     * @param <A> Event parameter type 1.
     * @param <B> Event parameter type 2.
     */
    @FunctionalInterface
    public interface MyEventHandler<A, B> {
        /**
         * Runs the function with given parameters.
         *
         * @param a Parameter of type 1.
         * @param b Parameter of type 2.
         */
        void accept(A a, B b);
    }
}
