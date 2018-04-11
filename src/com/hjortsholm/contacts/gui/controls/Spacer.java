package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.style.Style;
import javafx.scene.layout.Region;

public class Spacer extends Region {
    public Spacer (int width, int height) {
        this.setMinWidth(width);
        this.setPrefWidth(width);
        this.setMaxWidth(width);

        this.setMinHeight(height);
        this.setPrefHeight(height);
        this.setMaxHeight(height);

        Style.addStyleClass(this,"hidden");
    }
}
