package com.hjortsholm.contacts.controls;

import com.hjortsholm.contacts.controls.style.Style;
import javafx.scene.Node;

public class CompositeControl extends CustomGrid {
    public CompositeControl(String name, Node... nodes) {
        this.init(name,nodes);
    }

    public CompositeControl(Node... nodes) {
        this.init(nodes);
    }

    public void init(String name, Node... nodes) {
        this.addChild(nodes);
        Style.addStylesheet(this, name);
    }

    public void init(Node... nodes) {
        this.init(getClass().getSimpleName().toString(),nodes);
    }
}
