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
import com.hjortsholm.contacts.gui.util.Anchor;
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
        stage.initStyle(StageStyle.UNIFIED);
        Scene scene = new Scene(this.getWindow(), com.hjortsholm.contacts.Application.getWindowWidth(),
                com.hjortsholm.contacts.Application.getWindowHeight());

        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
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
        this.contactNavigation = new ContactNavigation();
        this.contactNavigation.setContacts(contacts.getContacts());
        this.contactNavigation.setOnTabSelectedEvent(this::onTabChanged);
        this.contactNavigation.setOnSearch(this::onSearch);


        CustomSeperator verticalSeperator = new CustomSeperator();
        verticalSeperator.setOrientation(Orientation.VERTICAL);
        Style.addStyleClass(verticalSeperator, "vr");

        this.contactCard = new ContactCard();
        this.contactCard.setOnNewContact(this.contactCard::setContact);
        this.contactCard.setOnContactDelete(contact -> {
            this.contacts.remove(contact);
            this.contactNavigation.setContacts(this.contacts.getContacts());
        });
        this.contactCard.setOnContactSave(contact -> {
            this.contacts.addContact(contact);
            this.contactNavigation.setContacts(this.contacts.getContacts());
            if (contact.isNewContact()) {
                this.contactNavigation.setSelected(contact);
            }
        });

        Anchor.setBottomAnchor(this.contactNavigation, 0.);
        Anchor.setTopAnchor(this.contactNavigation, 0.);
        Anchor.setLeftAnchor(this.contactNavigation, 0.);

        Anchor.setBottomAnchor(this.contactCard, 0.);
        Anchor.setTopAnchor(this.contactCard, 0.);
        Anchor.setRightAnchor(this.contactCard, 0.);
        Anchor.setLeftAnchor(this.contactCard, 180.);

        Anchor.setTopAnchor(verticalSeperator, 0.);
        Anchor.setBottomAnchor(verticalSeperator, 0.);
        Anchor.setLeftAnchor(verticalSeperator, 180.);


        this.getWindow().getChildren().add(this.contactNavigation);
        this.getWindow().getChildren().add(verticalSeperator);
        this.getWindow().getChildren().add(this.contactCard);
    }

    private void onSearch(String[] keyWords) {
        this.contactNavigation.setContacts(contacts.getContactsWith(keyWords));
        if (this.contactNavigation.hasContact(this.contactCard.getContact())) {
            this.contactNavigation.setSelected(this.contactCard.getContact());
        }
    }


    private void onTabChanged(ContactNavigationTab navigationTab) {
        if (this.contactCard.isEmpty()) {
            this.contactCard.setContact(navigationTab.getContact());
            this.contactNavigation.setSelected(navigationTab);
        } else if (!this.contactCard.getContact().equals(navigationTab.getContact()) && (this.contactCard.isValid() || this.contactCard.getContact().isNewContact())) {
            if (this.contactCard.getContact().isNewContact() && !this.contactCard.isValid()) {
                this.contactCard.setEditable(false);

            }

            this.contactCard.setContact(navigationTab.getContact());
            this.contactNavigation.setSelected(navigationTab);
            this.contactCard.setEditable(!this.contactCard.isValid() || (this.contactCard.isEditable() && !this.contactCard.getContact().isNewContact()));
        }
    }
}

