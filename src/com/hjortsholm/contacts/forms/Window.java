package com.hjortsholm.contacts.forms;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.gui.util.Style;
import javafx.application.Application;
import javafx.scene.layout.AnchorPane;

public abstract class Window extends Application {


    private AnchorPane window;
//    private Stage stage;

    public void initialiseComponents(/*Node... nodes*/) {
        this.window = new AnchorPane();
        Style.addStyleClass(this.window, "Window");
        Style.addStylesheet(this.window, "Window");
//        this.window = new CustomGrid("Window");
//        this.window.initialiseComponent("");
        /*for (Node node : nodes)
            this.window.addRow(node);*/
    }

    public AnchorPane getWindow() {
        return this.window;
    }
// public CustomGrid getWindow() {
//        return this.window;
//    }

//    public Stage getStage() {
//        return this.stage;
//    }

//    public void setStage(Stage stage) {
//        this.stage = stage;
//    }

//    public void onWindowMinimise(MouseEvent mouseEvent) {
//        this.getStage().setIconified(true);
//    }
//
//    public void onWindowExit(MouseEvent mouseEvent) {
//        Platform.exit();
//    }

}