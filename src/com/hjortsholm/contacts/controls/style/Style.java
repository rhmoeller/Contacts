package com.hjortsholm.contacts.controls.style;

import com.hjortsholm.contacts.Resource;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Style {
    public static void addGenericStyleClass(Region control) {
        System.out.println("Class: "+control.getClass().getSimpleName());
        Style.addStyleClass(control,control.getClass().getSimpleName());
    }

    public static void addStyleClass(Region control, String styleClass) {
        System.out.println("Class: "+styleClass);
        control.getStyleClass().add(styleClass);
    }

    public static void addStylesheet(Region control, String styleSheet) {
        control.getStylesheets().add(Resource.get("controls/style/"+styleSheet+".css"));
    }

//    public static void addStyleClass(Stage stage, String styleClass) {
//
//    }
}
