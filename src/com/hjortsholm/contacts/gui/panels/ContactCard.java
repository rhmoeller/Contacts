package com.hjortsholm.contacts.gui.panels;

import com.hjortsholm.contacts.Application;
import com.hjortsholm.contacts.gui.controls.*;
import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.gui.parents.LayerPane;
import com.hjortsholm.contacts.gui.util.Anchor;
import com.hjortsholm.contacts.gui.util.Style;
import com.hjortsholm.contacts.models.Contact;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ContactCard extends CustomGrid {
    private Contact contact;

    private Label emptyCard;
    private LayerPane container;
    private EditableLabel firstName;
    private EditableLabel lastName;

    private Button edit;

    private LayerPane backgroundLayer;
    private LayerPane middlegroundLayer;

    private ScrollableView scrollContainer;
    private ContactFieldsList contactFieldsList;
    private ContactInfoHeader contactInfoHeader;
    private CustomGrid contactInfoPanel;
    private boolean editing;

    public ContactCard() {
        initialiseComponent();
        addDefaultStyleSheet();

        this.editing = false;

        AnchorPane container = new AnchorPane();
//        container.setPrefHeight(Application.getWindowHeight()-200);
//        Anchor.setRightAnchor(container,0.);

//        ScrollableView contactInformationPanel = ;
//        Anchor.setRightAnchor(contactInformationPanel,0.);
//        contactInformationPanel.setPrefWidth(Application.getWindowWidth()-220);
//        Style.addStyleClass(contactInformationPanel,"ContactFieldScroll");

        this.contactInfoPanel = new CustomGrid();
        this.contactInfoHeader = new ContactInfoHeader();
        this.contactFieldsList = new ContactFieldsList();
        this.scrollContainer = new ScrollableView(contactFieldsList);

        this.contactInfoPanel.addRow(this.contactInfoHeader);
        this.contactInfoPanel.addRow(this.scrollContainer);
        this.scrollContainer.setPrefHeight(Application.getWindowHeight()-100);
//        Style.addStyleClass(this.scrollContainer,"ContactScrollPanel");




        this.edit = new Button();
        this.edit.setOnMouseClicked(this::edit);
        Anchor.setRightAnchor(edit,0.);
        Anchor.setBottomAnchor(edit, 0.);



        container.getChildren().addAll(this.contactInfoPanel,this.edit);
        this.addRow(container);
        refresh();
    }


    public void refresh() {
        this.edit.setText(this.editing?"Save":"Edit");
        if (this.contact != null) {
            scrollContainer.setVisible(true);
            edit.setVisible(true);
            this.contactFieldsList.setEditable(this.editing);
//            this.middlegroundLayer.setVisible(false);
//            this.firstName.setText(this.contact.getFirstName());
//            this.lastName.setText(this.contact.getLastName());

            if (this.editing) {

            } else {

            }

        } else {
            scrollContainer.setVisible(false);
            edit.setVisible(false);
        }
    }

    public void edit(MouseEvent mouseEvent) {
        this.editing = !this.editing;
        this.refresh();
    }

    public void setContact(Contact contact) {
        this.contact = contact;
        this.contactFieldsList.setContact(contact);
        this.contactInfoHeader.setContact(contact);
        this.refresh();
    }
}
