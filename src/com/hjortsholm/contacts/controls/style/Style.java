package com.hjortsholm.contacts.controls.style;

import com.hjortsholm.contacts.Resource;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

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
        control.getStylesheets().add(Resource.get("controls/style/"+styleSheet+".css"));
    }
}
