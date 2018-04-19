package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.gui.style.Style;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

/**
 * <h1>Scrollable view</h1>
 * A helper class for making a scrollable view pane.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @since 10/04/2018
 */
public class ScrollableView extends ScrollPane {

    /**
     * Creates a scrollable view without any children.
     */
    public ScrollableView() {
        this(null);
    }

    /**
     * Creates a scrollable view with passed content.
     *
     * @param content Content to add as children.
     */
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
