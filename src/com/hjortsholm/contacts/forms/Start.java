package com.hjortsholm.contacts.forms;

import com.hjortsholm.contacts.controls.*;
import com.hjortsholm.contacts.controls.style.Style;
import com.hjortsholm.contacts.models.Contact;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class Start extends Window {

    private ContactNavigation contactNavigation;
    private WindowTitleBar titleBar;
    private ContactCard contactCard;

    public static void show() {
        launch();
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle(com.hjortsholm.contacts.Application.getTitle());
        stage.initStyle(StageStyle.TRANSPARENT);

        DraggablePane root = new DraggablePane(stage,this.getWindow());
        Style.addStylesheet(root,"Window");
        Scene scene = new Scene(root, com.hjortsholm.contacts.Application.getWindowWidth(),
                                        com.hjortsholm.contacts.Application.getWindowHeight());

        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        this.setStage(stage);
    }

    @Override
    public void init() throws Exception {
        Contact a = new Contact(),
                b = new Contact(),
                c = new Contact();

        a.setFirstName("Rob");
        b.setFirstName("Dev");
        c.setFirstName("Remi");

        ArrayList<Contact> contacts = new ArrayList<>();
        for(int i = 0; i< 100; i++) {
            Contact contact = new Contact();
            contact.setFirstName("a");
            contacts.add(contact);
        }

        contacts.add(a);
        contacts.add(b);
        contacts.add(c);


        CustomGrid container = new CustomGrid();

        contactNavigation = new ContactNavigation(contacts);
        titleBar = new WindowTitleBar(this::onWindowExit,this::onWindowMinimise);
        contactCard = new ContactCard();

        contactNavigation.setOnTabSelectedEvent(this::onTabChanged);
        container.addRow(titleBar);
        container.addColumn(contactNavigation);
        container.addColumn(contactCard);
        super.init(container);
    }




    private void onTabChanged(ContactNavigationTab navigationTab)  {
        contactCard.setContact(navigationTab.getContact());
//        System.out.println(navigationTab.getContact().getFirstName() + " selected..");
    }


}
