package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.util.Style;
import javafx.scene.control.Button;

public class SystemButton extends Button {
    public SystemButton(String text) {
        this.setText(text);
        Style.addStylesheet(this,"Buttons");
        Style.addStyleClass(this,"system-button");
    }
}
