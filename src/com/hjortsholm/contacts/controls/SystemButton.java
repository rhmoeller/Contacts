package com.hjortsholm.contacts.controls;

import com.hjortsholm.contacts.controls.style.Style;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SystemButton extends Button {
    public SystemButton(String text) {
        this.setText(text);
        Style.addStylesheet(this,"Buttons");
        Style.addStyleClass(this,"system-button");
    }
}
