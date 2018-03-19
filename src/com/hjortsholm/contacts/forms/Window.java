package com.hjortsholm.contacts.forms;

import com.hjortsholm.contacts.controls.CompositeControl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class Window extends Application {


    private CompositeControl window;
    private Stage stage;

    public void init(Node... nodes) {
        this.window = new CompositeControl("Window", nodes);

    }

    public CompositeControl getWindow() {
        return this.window;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void onWindowMinimise(MouseEvent mouseEvent) {
        this.getStage().setIconified(true);
    }

    public void onWindowExit(MouseEvent o) {
        Platform.exit();
    }

}