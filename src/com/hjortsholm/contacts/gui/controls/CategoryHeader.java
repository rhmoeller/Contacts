package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.controls.Spacer;
import com.hjortsholm.contacts.gui.parents.CustomGrid;
import javafx.scene.control.Label;

public class CategoryHeader extends CustomGrid {
    public CategoryHeader(String title) {
        this.addColumn(new Spacer(8,5));
        this.addColumn(new Label(title.toUpperCase())); // Todo: fix children
    }
}
