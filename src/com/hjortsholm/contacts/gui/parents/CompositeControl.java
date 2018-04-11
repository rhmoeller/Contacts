package com.hjortsholm.contacts.gui.parents;

import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.gui.style.Style;
import javafx.scene.Node;

public class CompositeControl extends CustomGrid {
    public CompositeControl(String name, Node... nodes) {
        this.init(name,nodes);
    }

    public CompositeControl(Node... nodes) {
        this.init(nodes);
    }

    public CompositeControl() {
        this.init();
    }

    public void init(String name, Node... nodes) {
        this.addColumn(nodes);
        Style.addStylesheet(this, name);
    }

    public void init(Node... nodes) {
        this.init(getClass().getSimpleName().toString(),nodes);
    }
}
