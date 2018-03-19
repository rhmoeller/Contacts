package com.hjortsholm.contacts.controls;

import javafx.scene.control.Label;

public class CategoryHeader extends CustomPane {
    public CategoryHeader(String title) {
        this.addChild(new Label(title.toUpperCase())); // Todo: fix children
    }
}
