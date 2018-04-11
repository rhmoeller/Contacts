package com.hjortsholm.contacts.gui.util;

import com.hjortsholm.contacts.Resource;
import javafx.scene.layout.Region;

public class Style {
    public static void addGenericStyleClass(Region control) {
        Style.addStyleClass(control,control.getClass().getSimpleName());
    }

    public static void addStyleClass(Region control, String styleClass) {
        control.getStyleClass().add(styleClass);
    }

    public static void removeStyleClass(Region control, String styleClass) {
        control.getStyleClass().removeAll(styleClass);
    }

    public static void addStylesheet(Region control, String styleSheet) {
        control.getStylesheets().add(Resource.get("gui/style/"+styleSheet+".css"));
    }
}
