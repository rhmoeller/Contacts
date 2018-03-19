package com.hjortsholm.contacts.controls;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class Anchor extends AnchorPane {
    public static Node anchorAll(Node node, double value) {
        AnchorPane.setTopAnchor(node,value);
        AnchorPane.setBottomAnchor(node,value);
        AnchorPane.setRightAnchor(node,value);
        AnchorPane.setLeftAnchor(node,value);
        return node;
    }
}
