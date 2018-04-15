package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.util.Style;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class ScrollableView extends ScrollPane {

    public ScrollableView() {
        this(null);
    }

    public ScrollableView(Node content) {
        Style.addStyleClass(this, getClass().getSimpleName());
        Style.addStylesheet(this, getClass().getSimpleName());
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
        this.setFitToWidth(true);
        this.setFitToHeight(true);
        this.setContent(content);
    }

}
