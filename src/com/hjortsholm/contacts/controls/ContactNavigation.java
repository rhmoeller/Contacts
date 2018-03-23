package com.hjortsholm.contacts.controls;

import com.hjortsholm.contacts.Application;
import com.hjortsholm.contacts.controls.style.Style;
import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.ContactList;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ContactNavigation extends CompositeControl {

    private ContactNavigationTab selectedNavigationTab;
    private Consumer<ContactNavigationTab> onTabSelectedEvent;

    private CustomGrid contactNavigation;

    public ContactNavigation() {
        super();

        contactNavigation = new CustomGrid();
        TextField searchField = new TextField();
        ScrollableView scrollContainer = new ScrollableView();

        searchField.setPromptText("Search");
        Style.addStylesheet(searchField,"TextFields");
        Style.addStyleClass(searchField,"SearchField");

        contactNavigation.setPrefHeight(Application.getWindowHeight());
        scrollContainer.setPrefHeight(Application.getWindowHeight());


        scrollContainer.setContent(contactNavigation);
        this.addRow(searchField);
        this.addRow(new Spacer(10,10));
        this.addRow(scrollContainer);
    }

    public void setContacts(ContactList contacts) {
        HashMap<Character, ArrayList<Contact>> contactsCategorised = new HashMap<>();
        for (Contact contact : contacts.getContacts()) {
            char initialLetter = contact.getFirstName().charAt(0);
            if (!contactsCategorised.keySet().contains(initialLetter)) {
                ArrayList<Contact> contactsByInitialLetter = new ArrayList<>();
                contactsByInitialLetter.add(contact);
                contactsCategorised.put(initialLetter, contactsByInitialLetter);
            } else {
                contactsCategorised.get(initialLetter).add(contact);
            }
        }

        for (Map.Entry category : contactsCategorised.entrySet()) {
            this.contactNavigation.addRow(
                    new CategorisedListView(
                            category.getKey().toString(),
                            (ArrayList<Contact>) category.getValue(),
                            this::onTabSelected));
        }
    }

    public void onTabSelected(ContactNavigationTab navigationTab) {
        if (navigationTab.equals(selectedNavigationTab))
            return;
        else if (this.selectedNavigationTab != null)
            this.selectedNavigationTab.toggleSelected();
        this.selectedNavigationTab = navigationTab;
        this.selectedNavigationTab.toggleSelected();
        if (this.onTabSelectedEvent != null)
            this.onTabSelectedEvent.accept(this.selectedNavigationTab);
    }

    public void setOnTabSelectedEvent(Consumer<ContactNavigationTab> onTabSelectedEvent) {
        this.onTabSelectedEvent = onTabSelectedEvent;
    }
}
