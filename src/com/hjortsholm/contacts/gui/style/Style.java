package com.hjortsholm.contacts.gui.style;

import com.hjortsholm.contacts.util.Resource;
import javafx.scene.layout.Region;

/**
 * <h1>Style</h1>
 * A helper class that links stylesheets and style classes to Javafx nodes
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @since 10/04/2018
 */
public class Style {
    /**
     * Links a style class of a nodes class name to a specific node.
     *
     * @param control Control to link style class to.
     */
    public static void addGenericStyleClass(Region control) {
        Style.addStyleClass(control, control.getClass().getSimpleName());
    }

    /**
     * Links a specific style class to a specific node.
     *
     * @param styleClass Style class to link to control.
     * @param control Control to link style class to.
     */
    public static void addStyleClass(Region control, String styleClass) {
        control.getStyleClass().add(styleClass);
    }

    /**
     * Removes a specific style class from a node.
     *
     * @param control Control to remove style class from.
     * @param styleClass Style class to remove from node.
     */
    public static void removeStyleClass(Region control, String styleClass) {
        control.getStyleClass().removeAll(styleClass);
    }

    /**
     * Link a stylesheet to a control.
     *
     * @param control Control to link the stylesheet to.
     * @param styleSheet Stylesheet to link to control.
     */
    public static void addStylesheet(Region control, String styleSheet) {
        try {
            control.getStylesheets().add(new Resource("gui/style/" + styleSheet + ".css").getPath());
        } catch (Exception exception) {
            System.err.println("[ERROR]: File gui/style/" + styleSheet + ".css not fould..");
        }
    }
}
