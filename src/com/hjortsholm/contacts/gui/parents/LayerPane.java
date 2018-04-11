package com.hjortsholm.contacts.gui.parents;

import com.hjortsholm.contacts.gui.util.Anchor;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class LayerPane extends AnchorPane{
//    public void setBackground(Node node) {
//        this.addChild();
//    }

    public void addLayer(Node... nodes) {
        for (Node node:nodes) {
            this.getChildren().add(Anchor.anchorAll(node,0));
        }
    }
}
