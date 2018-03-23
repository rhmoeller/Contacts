package com.hjortsholm.contacts.controls;

import com.hjortsholm.contacts.controls.style.Style;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;

public class ScrollableView extends ScrollPane {
    public ScrollableView() {
        Style.addStyleClass(this, getClass().getSimpleName());
        Style.addStylesheet(this, getClass().getSimpleName());
        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.setFitToWidth(true);
        this.setFitToHeight(true);

    }
}
