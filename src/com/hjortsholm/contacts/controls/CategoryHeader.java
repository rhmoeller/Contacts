package com.hjortsholm.contacts.controls;

import javafx.scene.control.Label;

public class CategoryHeader extends CustomGrid {
    public CategoryHeader(String title) {
        this.addColumn(new Spacer(8,5));
        this.addColumn(new Label(title.toUpperCase())); // Todo: fix children
    }
}
