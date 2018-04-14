package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.Application;
import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.gui.util.Anchor;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class WindowTitleBar extends CustomGrid {

    public WindowTitleBar(EventHandler<? super MouseEvent> onWindowExit, EventHandler<? super MouseEvent> onWindowMinimise) {
        initialiseComponent();
        addDefaultStyleSheet();
        AnchorPane container = new AnchorPane();
        CustomGrid systemButtons = new CustomGrid();
        SystemButton exitButton = new SystemButton("x");
        SystemButton minimiseButton = new SystemButton("-");
        Label appTitle = new Label(Application.getTitle());

        appTitle.setAlignment(Pos.BOTTOM_CENTER);
        appTitle.setPrefWidth(Application.getWindowWidth());
        exitButton.setOnMouseClicked(onWindowExit);
        minimiseButton.setOnMouseClicked(onWindowMinimise);

        systemButtons.addColumn(new Spacer(8, 20));
        systemButtons.addColumn(exitButton);
        systemButtons.addColumn(new Spacer(7, 20));
        systemButtons.addColumn(minimiseButton);
        systemButtons.addColumn(new Spacer(30, 1));

        container.getChildren().add(appTitle);
        container.getChildren().add(systemButtons);
        this.addRow(container);
    }
}
