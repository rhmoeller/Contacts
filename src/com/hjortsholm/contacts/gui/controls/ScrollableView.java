package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.util.Style;
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
