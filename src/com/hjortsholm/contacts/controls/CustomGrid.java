package com.hjortsholm.contacts.controls;

import com.hjortsholm.contacts.Application;
import com.hjortsholm.contacts.controls.style.Style;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class CustomGrid extends GridPane {
    private int rows;
    private int columns;

    public CustomGrid() {
        this.rows = 0;
        this.columns = 0;
        this.setMaxHeight(Application.getWindowHeight());
        this.setMaxWidth(Application.getWindowWidth());
        Style.addGenericStyleClass(this);
        Anchor.anchorAll(this, 0.0);
    }

    public void addChild(Node... nodes) {
        for (Node node: nodes)
            this.addRow(this.rows, Anchor.anchorAll(node, 0.0));
    }

    public void addRow(Node node) {
        this.addRow(this.rows++, Anchor.anchorAll(node, 0.0));
    }

    public void addColumn(Node node) {
        this.addColumn(this.columns++, Anchor.anchorAll(node, 0.0));
    }
}
