package com.hjortsholm.contacts.forms;

import com.hjortsholm.contacts.controls.*;
import com.hjortsholm.contacts.controls.style.Style;
import com.hjortsholm.contacts.database.TableField;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.ContactList;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.models.FieldType;
import javafx.application.Application;
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
    public void init() {
//        Contact a = new Contact(),
//                b = new Contact(),
//                c = new Contact();
//
//        a.setFirstName("Rob");
//        b.setFirstName("Dev");
//        c.setFirstName("Remi");

        ContactList contacts = new ContactList();
//        for(int i = 0; i< 100; i++) {
//            Contact contact = new Contact();
//            contact.setFirstName("a");
//            contacts.add(contact);
//        }

        com.hjortsholm.contacts.Application.getDatabase().get(
                "SELECT\n" +
                        "  Contact.id,\n" +
                        "  Field.value,\n" +
                        "  Field.name,\n" +
                        "  FieldType.id type,\n" +
                        "  FieldType.prompt\n" +
                        "FROM\n" +
                        "  (Contact\n" +
                        "INNER JOIN\n" +
                        "  Field\n" +
                        "ON\n" +
                        "  Contact.id = Field.contact)\n" +
                        "INNER JOIN\n" +
                        "  FieldType\n" +
                        "ON\n" +
                        "  FieldType.id = Field.type;",
                result ->  {
                    Contact contact = new Contact((String)result.getColumn("id"));
                    if (contacts.contactExists(contact)) {
                        contact = contacts.getContact(contact.getId());
                    } else {
                        contacts.addContact(contact);
                    }
                    Field field = new Field(
                            contact,
                            FieldType.values()[(int)result.getColumn("type")],
                            (String)result.getColumn("name"),
                            (String)result.getColumn("value"),
                            (String)result.getColumn("prompt")
                            );

                    contact.setField(field);
                    System.out.println(contact.toString());
                });


        CustomGrid container = new CustomGrid();
        contactNavigation = new ContactNavigation();
        titleBar = new WindowTitleBar(this::onWindowExit,this::onWindowMinimise);
        contactCard = new ContactCard();

        contactNavigation.setContacts(contacts);
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
