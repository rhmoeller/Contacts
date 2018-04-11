package com.hjortsholm.contacts.gui.panels;

import com.hjortsholm.contacts.Application;
import com.hjortsholm.contacts.gui.controls.CategorisedListView;
import com.hjortsholm.contacts.gui.controls.ContactNavigationTab;
import com.hjortsholm.contacts.gui.controls.Spacer;
import com.hjortsholm.contacts.gui.parents.CompositeControl;
import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.gui.controls.ScrollableView;
import com.hjortsholm.contacts.gui.style.Style;
import com.hjortsholm.contacts.models.Contact;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.*;
import java.util.function.Consumer;

public class ContactNavigation extends CompositeControl {

    private ContactNavigationTab selectedNavigationTab;
    private Consumer<ContactNavigationTab> onTabSelectedEvent;

    private CustomGrid contactNavigation;

    private TextField searchField;
    private Consumer<String[]> onSearch;

    public ContactNavigation() {
        super();

        contactNavigation = new CustomGrid();
        searchField = new TextField();
        ScrollableView scrollContainer = new ScrollableView();

        searchField.setPromptText("Search");
        searchField.setOnKeyReleased(this::onSearch);

        Style.addStylesheet(searchField, "TextFields");
        Style.addStyleClass(searchField, "SearchField");

        contactNavigation.setPrefHeight(Application.getWindowHeight());
        scrollContainer.setPrefHeight(Application.getWindowHeight());


        scrollContainer.setContent(contactNavigation);
        this.addRow(searchField);
        this.addRow(new Spacer(10, 10));
        this.addRow(scrollContainer);
    }

    private void onSearch(KeyEvent event) {
        if (this.onSearch != null) {
            this.onSearch.accept(this.searchField.getText().replaceAll("  "," ").split( " "));
        }
    }

    public void setOnSearch(Consumer<String[]> onSearch) {
        this.onSearch = onSearch;
    }

    public void setContacts(Collection<Contact> contacts) {
        HashMap<Character, ArrayList<Contact>> contactsCategorised = new HashMap<>();
        this.contactNavigation.getChildren().clear();
        for (Contact contact : contacts) {
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
