package com.hjortsholm.contacts.gui.parents;

import com.hjortsholm.contacts.Application;
import com.hjortsholm.contacts.gui.util.Style;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class CustomGrid extends GridPane {
    private int rows;
    private int columns;

    //    public CustomGrid() {
//        this(null);
////        this.rows = 0;
////        this.columns = 0;
////        this.setMaxHeight(Application.getWindowHeight());
////        this.setMaxWidth(Application.getWindowWidth());
////        Style.addGenericStyleClass(this);
////        Anchor.anchorAll(this, 0.0);
//    }
//
//    public CustomGrid(Node... nodes) {
//
////        for (Node node:nodes) {
////            this.addColumn(node);
////        }
//
////        initialiseComponents();
//    }

    public CustomGrid() {
        initialiseComponent();
    }

    public CustomGrid(String name) {
        initialiseComponent(name);
    }


    protected void initialiseComponent() {
        initialiseComponent(getClass().getSimpleName());
    }

    protected void initialiseComponent(String name) {
        this.rows = 0;
        this.columns = 0;
        this.setMaxHeight(Application.getWindowHeight());
        this.setMaxWidth(Application.getWindowWidth());
        Style.addStyleClass(this,name);
//        Anchor.anchorAll(this, 0.0);
    }

    public void addDefaultStyleSheet() {
        this.addStyleSheet(this.getClass().getSimpleName());
    }

    public void addStyleSheet(String name) {
        Style.addStylesheet(this, name);
    }

    public void addChild(Node... nodes) {
        for (Node node : nodes)
            this.add(node, this.columns, /*Anchor.anchorAll(node, 0.0)*/this.rows);
    }

    public void addRow(Node... nodes) {
        for (Node node : nodes)
            this.addRow(this.rows, /*Anchor.anchorAll(node, 0.0)*/node);
        this.rows++;
    }

    public void addColumn(Node... nodes) {
        for (Node node : nodes)
            this.addColumn(this.columns, /*Anchor.anchorAll(node, 0.0)*/node);
        this.columns++;
    }

    public void remove(Object object) {
        this.getChildren().remove(object);
    }

    public void clear() {
        this.getChildren().clear();
    }
}
