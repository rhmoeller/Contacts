package com.hjortsholm.contacts.gui;

import com.hjortsholm.contacts.database.Database;
import com.hjortsholm.contacts.database.Query;
import com.hjortsholm.contacts.database.QueryRow;
import com.hjortsholm.contacts.database.QuerySet;
import com.hjortsholm.contacts.gui.controls.ContactNavigationTab;
import com.hjortsholm.contacts.gui.panels.ContactCard;
import com.hjortsholm.contacts.gui.panels.ContactNavigation;
import com.hjortsholm.contacts.gui.style.Style;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.ContactList;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * <h1>Start</h1>
 * Initialises and starts gui.
 * Handles contact searches and selection.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @see ContactNavigation
 * @see ContactCard
 * @see Contact
 * @see Database
 * @see QuerySet
 * @see QueryRow
 * @since 10/04/2018
 */
public class Start extends Application {

    private ContactNavigation contactNavigation;
    private ContactCard contactCard;
    private ContactList contacts;
    private Separator verticalSeperator;
    private AnchorPane window;

    /**
     * Start the JavaFX gui.
     */
    public static void show() {
        launch();
    }

    /**
     * Sets up gui window.
     *
     * @param stage Application window.
     */
    @Override
    public void start(Stage stage) {

        stage.setTitle(com.hjortsholm.contacts.Application.getTitle());
        stage.initStyle(StageStyle.UNIFIED);
        stage.setMinWidth(600);
        stage.setMinHeight(300);

        this.window = new AnchorPane();
        this.window.getChildren().add(this.contactNavigation);
        this.window.getChildren().add(this.verticalSeperator);
        this.window.getChildren().add(this.contactCard);
        Scene scene = new Scene(this.window, com.hjortsholm.contacts.Application.getWindowWidth(),
                com.hjortsholm.contacts.Application.getWindowHeight());


        Style.addStyleClass(this.window, "Window");
        Style.addStylesheet(this.window, "Window");

        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Setting up all controls and fetching contacts from database.
     */
    @Override
    public void init() {
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

        this.verticalSeperator = new Separator();
        this.verticalSeperator.setOrientation(Orientation.VERTICAL);

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

        Style.addStyleClass(this.verticalSeperator, "vr");

        AnchorPane.setBottomAnchor(this.contactNavigation, 0.);
        AnchorPane.setTopAnchor(this.contactNavigation, 0.);
        AnchorPane.setLeftAnchor(this.contactNavigation, 0.);

        AnchorPane.setBottomAnchor(this.contactCard, 0.);
        AnchorPane.setTopAnchor(this.contactCard, 0.);
        AnchorPane.setRightAnchor(this.contactCard, 0.);
        AnchorPane.setLeftAnchor(this.contactCard, 180.);

        AnchorPane.setTopAnchor(this.verticalSeperator, 0.);
        AnchorPane.setBottomAnchor(this.verticalSeperator, 0.);
        AnchorPane.setLeftAnchor(this.verticalSeperator, 180.);
    }

    /**
     * Searchfield key pressed event searches for entered keywords.
     *
     * @param keyWords Keywords to search for in contacts.
     */
    private void onSearch(String[] keyWords) {
        this.contactNavigation.setContacts(this.contacts.getContactsWith(keyWords));
        if (this.contactNavigation.hasContact(this.contactCard.getContact())) {
            this.contactNavigation.setSelected(this.contactCard.getContact());
        }
    }

    /**
     * Contact selected in navigation panel.
     *
     * @param navigationTab Selected tab.
     */
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

