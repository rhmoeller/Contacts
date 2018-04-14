package com.hjortsholm.contacts.forms;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.database.Query;
import com.hjortsholm.contacts.database.QueryRow;
import com.hjortsholm.contacts.database.QuerySet;
import com.hjortsholm.contacts.gui.controls.ContactNavigationTab;
import com.hjortsholm.contacts.gui.controls.CustomSeperator;
import com.hjortsholm.contacts.gui.controls.WindowTitleBar;
import com.hjortsholm.contacts.gui.panels.ContactCard;
import com.hjortsholm.contacts.gui.panels.ContactNavigation;
import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.gui.parents.DraggablePane;
import com.hjortsholm.contacts.gui.util.Style;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.ContactList;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Start extends Window {

    private ContactNavigation contactNavigation;
    private WindowTitleBar titleBar;
    private ContactCard contactCard;
    private ContactList contacts;

    public static void show() {
        launch();
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle(com.hjortsholm.contacts.Application.getTitle());
        stage.initStyle(StageStyle.TRANSPARENT);

        DraggablePane root = new DraggablePane(stage, this.getWindow());
        Style.addStylesheet(root, "Window");
        Scene scene = new Scene(root, com.hjortsholm.contacts.Application.getWindowWidth(),
                com.hjortsholm.contacts.Application.getWindowHeight());

        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        this.setStage(stage);
    }

    @Override
    public void init() {
        initialiseComponents();
        this.contacts = new ContactList();
        QuerySet set = Database.get(new Query()
                .select("id")
                .from("Contact")
                .toString());

        for (QueryRow row : set) {
            Contact contact = new Contact((int) row.getColumn("id"));
            if (!this.contacts.contactExists(contact)) {
                this.contacts.addContact(contact);
            }
        }

        CustomGrid container = new CustomGrid();
        this.contactNavigation = new ContactNavigation();
        this.titleBar = new WindowTitleBar(this::onWindowExit, this::onWindowMinimise);
        this.contactCard = new ContactCard();
        CustomSeperator verticalSeperator = new CustomSeperator();
        verticalSeperator.setOrientation(Orientation.VERTICAL);
        Style.addStyleClass(verticalSeperator, "vr");

        this.contactCard.setOnNewContact(contact -> this.contactCard.setContact(contact));
        this.contactCard.setOnContactDelete(contact -> {
            this.contacts.remove(contact);
            this.contactNavigation.setContacts(this.contacts.getContacts());
        });
        this.contactCard.setOnContactSave(contact -> {
            this.contacts.addContact(contact);
            this.contactNavigation.setContacts(this.contacts.getContacts());
        });

        this.contactNavigation.setContacts(contacts.getContacts());
        this.contactNavigation.setOnTabSelectedEvent(this::onTabChanged);
        this.contactNavigation.setOnSearch(this::onSearch);
        container.addColumn(this.contactNavigation);
        container.addColumn(verticalSeperator);
        container.addColumn(this.contactCard);
//        super.init(this.titleBar, container);
        this.getWindow().addRow(this.titleBar);
        this.getWindow().addRow(container);
    }

    private void onSearch(String[] keyWords) {
        System.out.println(keyWords);
        this.contactNavigation.setContacts(contacts.getContactsWith(keyWords));
    }


    private void onTabChanged(ContactNavigationTab navigationTab) {
        if (this.contactCard.isEmpty()) {
            this.contactCard.setContact(navigationTab.getContact());
            this.contactNavigation.setSelected(navigationTab);
        } else if (!this.contactCard.getContact().equals(navigationTab.getContact()) && this.contactCard.isValid()) {
            this.contactCard.setContact(navigationTab.getContact());
            this.contactNavigation.setSelected(navigationTab);
            this.contactCard.setEditable(!this.contactCard.isValid() || this.contactCard.isEditable());
        }
    }
}

