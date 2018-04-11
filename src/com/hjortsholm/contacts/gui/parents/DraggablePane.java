package com.hjortsholm.contacts.gui.parents;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DraggablePane extends CustomGrid {
    private double xPos;
    private double yPos;
    private Stage stage;
    public DraggablePane(Stage stage, Node... nodes) {
        this.stage = stage;
        this.xPos = 0;
        this.yPos = 0;

        this.addChild(nodes);
        this.setOnMousePressed(this::onMousePressed);
        this.setOnMouseDragged(this::onMouseDragged);
    }

    private void onMouseDragged(MouseEvent mouseEvent) {
        this.stage.setX(mouseEvent.getScreenX() + this.xPos);
        this.stage.setY(mouseEvent.getScreenY() + this.yPos);
    }

    private void onMousePressed(MouseEvent mouseDragEvent) {
        this.xPos = this.stage.getX() - mouseDragEvent.getScreenX();
        this.yPos = this.stage.getY() - mouseDragEvent.getScreenY();
    }



}
