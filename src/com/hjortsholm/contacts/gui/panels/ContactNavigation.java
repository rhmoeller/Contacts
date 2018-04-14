package com.hjortsholm.contacts.gui.panels;

import com.hjortsholm.contacts.Application;
import com.hjortsholm.contacts.gui.controls.CategorisedListView;
import com.hjortsholm.contacts.gui.controls.ContactNavigationTab;
import com.hjortsholm.contacts.gui.controls.ScrollableView;
import com.hjortsholm.contacts.gui.controls.Spacer;
import com.hjortsholm.contacts.gui.parents.CustomGrid;
import com.hjortsholm.contacts.gui.util.Style;
import com.hjortsholm.contacts.models.Contact;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.*;
import java.util.function.Consumer;

public class ContactNavigation extends CustomGrid {

    private ContactNavigationTab selectedNavigationTab;
    private Consumer<ContactNavigationTab> onTabSelectedEvent;

    private CustomGrid contactNavigation;

    private TextField searchField;
    private Consumer<String[]> onSearch;

    public ContactNavigation() {
        initialiseComponent();
        addDefaultStyleSheet();
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
            this.onSearch.accept(this.searchField.getText().replaceAll("  ", " ").split(" "));
        }
    }

    public void setOnSearch(Consumer<String[]> onSearch) {
        this.onSearch = onSearch;
    }

    public void setContacts(Collection<Contact> contacts) {
        HashMap<Character, ArrayList<Contact>> contactsCategorised = new HashMap<>();
        this.contactNavigation.clear();
        for (Contact contact : contacts) {
            if (contact.exists()) {
                char initialLetter = contact.getDisplayTitle().charAt(0);
                if (!contactsCategorised.keySet().contains(initialLetter)) {
                    ArrayList<Contact> contactsByInitialLetter = new ArrayList<>();
                    contactsByInitialLetter.add(contact);
                    contactsCategorised.put(initialLetter, contactsByInitialLetter);
                } else {
                    contactsCategorised.get(initialLetter).add(contact);
                }
            }
        }
        List<Character> indexSorted = new ArrayList<>(contactsCategorised.keySet());
        Collections.sort(indexSorted, Character::compareTo);
        for (char index : indexSorted) {
            this.contactNavigation.addRow(
                    new CategorisedListView(
                            Character.toString(index),
                            contactsCategorised.get(index),
                            this::onTabSelected));
        }
    }

    public void onTabSelected(ContactNavigationTab navigationTab) {
        if (this.onTabSelectedEvent != null && navigationTab != null)
            this.onTabSelectedEvent.accept(navigationTab);
    }

//    public void setSelected(Contact contact) {
//        for ()
//    }

    public void setSelected(ContactNavigationTab navigationTab) {
        if (navigationTab.equals(this.selectedNavigationTab)) {
            return;
        }

        if (this.selectedNavigationTab != null) {
            this.selectedNavigationTab.toggleSelected();
        }
        this.selectedNavigationTab = navigationTab;
        this.selectedNavigationTab.toggleSelected();

    }

    public void setOnTabSelectedEvent(Consumer<ContactNavigationTab> onTabSelectedEvent) {
        this.onTabSelectedEvent = onTabSelectedEvent;
    }
}
