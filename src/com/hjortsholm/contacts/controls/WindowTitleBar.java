package com.hjortsholm.contacts.controls;

import com.hjortsholm.contacts.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class WindowTitleBar extends CompositeControl {
    public WindowTitleBar(EventHandler<? super MouseEvent> onWindowExit, EventHandler<? super MouseEvent> onWindowMinimise) {
        SystemButton exitButton = new SystemButton("x");
        SystemButton minimiseButton = new SystemButton("-");
        Label title = new Label(Application.getTitle());

        exitButton.setOnMouseClicked(onWindowExit);
        minimiseButton.setOnMouseClicked(onWindowMinimise);

        super.init(
                    new Spacer(8, 20),
                    exitButton,
                    new Spacer(7, 20),
                    minimiseButton,
                    new Spacer(30,1),
                    title
        );
    }
}
