package com.hjortsholm.contacts.gui.util;

import com.hjortsholm.contacts.util.Resource;
import javafx.scene.layout.Region;

/**
 *
 */
public class Style {
    public static void addGenericStyleClass(Region control) {
        Style.addStyleClass(control, control.getClass().getSimpleName());
    }

    public static void addStyleClass(Region control, String styleClass) {
        control.getStyleClass().add(styleClass);
    }

    public static void removeStyleClass(Region control, String styleClass) {
        control.getStyleClass().removeAll(styleClass);
    }

    public static void addStylesheet(Region control, String styleSheet) {
        try {
            control.getStylesheets().add(new Resource("gui/style/" + styleSheet + ".css").getPath());
        } catch (Exception exception) {
            System.err.println("[ERROR]: File gui/style/" + styleSheet + ".css not fould..");
        }
    }
}
