package com.hjortsholm.contacts.gui.parents;

import com.hjortsholm.contacts.Application;
import com.hjortsholm.contacts.gui.style.Style;
import com.hjortsholm.contacts.gui.util.Anchor;
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

    public CustomGrid(Node... nodes) {
        this.rows = 0;
        this.columns = 0;
        this.setMaxHeight(Application.getWindowHeight());
        this.setMaxWidth(Application.getWindowWidth());
        Style.addGenericStyleClass(this);
        Anchor.anchorAll(this, 0.0);
        for (Node node:nodes) {
            this.addColumn(node);
        }
    }

    public void addChild(Node... nodes) {
        for (Node node: nodes)
            this.add(node,this.columns, /*Anchor.anchorAll(node, 0.0)*/this.rows);
    }

    public void addRow(Node... nodes) {
        for (Node node : nodes)
            this.addRow(this.rows, /*Anchor.anchorAll(node, 0.0)*/node);
        this.rows++;
    }

    public void addColumn(Node... nodes) {
        for (Node node: nodes)
            this.addColumn(this.columns, /*Anchor.anchorAll(node, 0.0)*/node);
        this.columns++;
    }
}
