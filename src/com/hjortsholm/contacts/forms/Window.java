package com.hjortsholm.contacts.forms;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class Window extends Application {


    private CustomGrid window;
    private Stage stage;

    public void init(Node... nodes) {
        this.window = new CustomGrid("Window");
//        this.window.initialiseComponent("");
        for (Node node : nodes)
            this.window.addRow(node);
    }

    public CustomGrid getWindow() {
        return this.window;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void onWindowMinimise(MouseEvent mouseEvent) {
        this.getStage().setIconified(true);
    }

    public void onWindowExit(MouseEvent mouseEvent) {
        Platform.exit();
    }

}