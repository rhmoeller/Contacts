package com.hjortsholm.contacts.controls;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class WindowTitleBar extends CompositeControl {
    public WindowTitleBar(EventHandler<? super MouseEvent> onWindowExit, EventHandler<? super MouseEvent> onWindowMinimise) {
        SystemButton exitButton = new SystemButton("x");
        SystemButton minimiseButton = new SystemButton("-");

        exitButton.setOnMouseClicked(onWindowExit);
        minimiseButton.setOnMouseClicked(onWindowMinimise);

        super.init(new Spacer(8, 20), exitButton, new Spacer(7, 20), minimiseButton);
    }
}
