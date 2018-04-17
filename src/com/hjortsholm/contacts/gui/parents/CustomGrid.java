package com.hjortsholm.contacts.gui.parents;

import com.hjortsholm.contacts.gui.style.Style;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * <h1>Start</h1>
 * This is a helper class that makes grids easier and automates adding stylesheets and classes.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @since 10/04/2018
 */
public class CustomGrid extends GridPane {

    private int rows;
    private int columns;

    /**
     * Initialises components.
     */
    public CustomGrid() {
        initialiseComponent();
    }

    /**
     * Initialises components.
     *
     * @param name Name of component.
     */
    public CustomGrid(String name) {
        initialiseComponent(name);
    }

    /**
     * Initialises components with classname as name.
     */
    protected void initialiseComponent() {
        initialiseComponent(getClass().getSimpleName());
    }

    /**
     * Sets style, rows and columns.
     *
     * @param name Style class name.
     */
    protected void initialiseComponent(String name) {
        this.rows = 0;
        this.columns = 0;
        Style.addStyleClass(this, name);
    }

    /**
     * Adds stylesheet named after the classname.
     */
    public void addDefaultStyleSheet() {
        this.addStyleSheet(this.getClass().getSimpleName());
    }

    /**
     * Adds stylesheet with name.
     *
     * @param name Name of stylesheet.
     */
    public void addStyleSheet(String name) {
        Style.addStylesheet(this, name);
    }

    /**
     * Adds node as child.
     *
     * @param nodes Nodes to add.
     */
    public void addChild(Node... nodes) {
        for (Node node : nodes)
            this.add(node, this.columns, /*Anchor.anchorAll(node, 0.0)*/this.rows);
    }

    /**
     * Adds new nodes in a new row.
     *
     * @param nodes Nodes to add.
     */
    public void addRow(Node... nodes) {
        for (Node node : nodes)
            this.addRow(this.rows, node);
        this.rows++;
    }

    /**
     * Adds new nodes in a new column.
     *
     * @param nodes Nodes to add.
     */
    public void addColumn(Node... nodes) {
        for (Node node : nodes)
            this.addColumn(this.columns, /*Anchor.anchorAll(node, 0.0)*/node);
        this.columns++;
    }

    /**
     * Remove child.
     *
     * @param object Child to remove.
     */
    public void remove(Object object) {
        this.getChildren().remove(object);
    }

    /**
     * Clear this of children.
     */
    public void clear() {
        this.getChildren().clear();
    }
}
