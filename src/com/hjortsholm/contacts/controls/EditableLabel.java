package com.hjortsholm.contacts.controls;

import com.hjortsholm.contacts.controls.style.Style;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class EditableLabel extends TextField {

    public EditableLabel() {
        this("");
    }

    public EditableLabel(String text) {
        this.textProperty().addListener(observable -> this.setToMinimumWidth());
        this.setEditable(false);
        this.setText(text);
        Style.addGenericStyleClass(this);
    }

    private void setToMinimumWidth() {
        Text textHelper = new Text();
        textHelper.setText(this.getText());
        textHelper.setFont(this.getFont());
        textHelper.setWrappingWidth(0);
        textHelper.setLineSpacing(0);
        double minimumWidth = Math.min(textHelper.prefWidth(-1), 0);
        textHelper.setWrappingWidth(Math.ceil(minimumWidth));
        this.setPrefWidth(Math.ceil(textHelper.getLayoutBounds().getWidth()) + 20);
    }
}
