package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.Application;
import com.hjortsholm.contacts.gui.parents.CustomGrid;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class WindowTitleBar extends CustomGrid {

    public WindowTitleBar(EventHandler<? super MouseEvent> onWindowExit, EventHandler<? super MouseEvent> onWindowMinimise) {
        initialiseComponent();
        addDefaultStyleSheet();

        SystemButton exitButton = new SystemButton("x");
        SystemButton minimiseButton = new SystemButton("-");

        exitButton.setOnMouseClicked(onWindowExit);
        minimiseButton.setOnMouseClicked(onWindowMinimise);

        this.addColumn(new Spacer(8, 20));
        this.addColumn(exitButton);
        this.addColumn(new Spacer(7, 20));
        this.addColumn(minimiseButton);
        this.addColumn(new Spacer(30, 1));
        this.addColumn(new Label(Application.getTitle()));
    }
}
