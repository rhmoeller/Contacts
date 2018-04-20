package com.hjortsholm.contacts.gui.controls;

import com.hjortsholm.contacts.models.Contact;
import com.hjortsholm.contacts.models.Field;
import com.hjortsholm.contacts.util.MD5;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * <h1>Profile Picture</h1>
 * Circle shaped picture that allows for picture uploading and deletion.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @since 10/04/2018
 */
public class ProfilePicture extends AnchorPane {

    private Circle pictureContainer;
    private Circle pictureCover;
    private Label alternativeText;
    private FileChooser fileChooser;
    private Button editPicture;

    private Field profilePictureField;
    private File image;
    private boolean isEditable;

    /**
     * Instantiates all the needed variables to function.
     */
    public ProfilePicture() {
        FileChooser.ExtensionFilter pictureFileFilter = new FileChooser.ExtensionFilter("Pictures", "*.png", "*.jpg", "*.gif", "*.jpeg");
        this.fileChooser = new FileChooser();
        this.fileChooser.setTitle("Select a profile picture");
        this.fileChooser.getExtensionFilters().add(pictureFileFilter);

        this.alternativeText = new Label();
        this.alternativeText.setAlignment(Pos.CENTER);
        this.alternativeText.getStyleClass().add("alt-text");

        this.pictureContainer = new Circle(40);
        this.pictureContainer.getStyleClass().add("profile-picture");

        this.pictureCover = new Circle(40);
        this.pictureCover.setFill(Color.rgb(74, 168, 142, .7));
        this.pictureCover.setVisible(false);

        this.editPicture = new Button();
        this.editPicture.setBackground(Background.EMPTY);
        this.editPicture.setAlignment(Pos.CENTER);
        this.editPicture.setOnMouseClicked(value -> {
            if (this.image == null) {
                this.selectPicture();
            } else {
                this.deletePicture();
            }
        });
        this.editPicture.getStyleClass().add("edit-picture-button");

        this.setOnMouseClicked(value -> {
            if (this.isEditable) {
                this.editPicture.fire();
            }
        });
        AnchorPane.setTopAnchor(this.alternativeText, 7.);
        AnchorPane.setBottomAnchor(this.alternativeText, 0.);
        AnchorPane.setRightAnchor(this.alternativeText, 0.);
        AnchorPane.setLeftAnchor(this.alternativeText, 0.);

        AnchorPane.setTopAnchor(this.editPicture, 7.);
        AnchorPane.setBottomAnchor(this.editPicture, 0.);
        AnchorPane.setRightAnchor(this.editPicture, 0.);
        AnchorPane.setLeftAnchor(this.editPicture, 0.);

        AnchorPane.setTopAnchor(this.pictureContainer, 7.);
        AnchorPane.setBottomAnchor(this.pictureContainer, 0.);
        AnchorPane.setRightAnchor(this.pictureContainer, 0.);
        AnchorPane.setLeftAnchor(this.pictureContainer, 0.);

        AnchorPane.setTopAnchor(this.pictureCover, 7.);
        AnchorPane.setBottomAnchor(this.pictureCover, 0.);
        AnchorPane.setRightAnchor(this.pictureCover, 0.);
        AnchorPane.setLeftAnchor(this.pictureCover, 0.);

        this.getChildren().addAll(this.pictureContainer, this.pictureCover, this.alternativeText, this.editPicture);
        this.refresh();
    }

    /**
     * Refreshes the visibility and all state dependant text.
     */
    private void refresh() {
        if (this.profilePictureField == null) {
            this.setVisible(false);
        } else if (this.profilePictureField.isEmpty() || this.image == null || !this.image.exists()) {
            this.setVisible(true);
            this.pictureContainer.setFill(Color.valueOf("#4AA88E"));
            this.alternativeText.setVisible(!this.isEditable);
            this.editPicture.setVisible(this.isEditable);
            this.editPicture.setText("add");
            this.pictureCover.setVisible(false);
        } else {
            this.setVisible(true);
            this.alternativeText.setVisible(false);
            this.pictureCover.setVisible(this.isEditable);
            this.editPicture.setVisible(this.isEditable);
            this.editPicture.setText("remove");
        }
    }

    /**
     * Loads the image on to the circle.
     */
    private void refreshImage() {
        if (this.image != null && this.image.exists()) {
            this.pictureContainer.setFill(new ImagePattern(new Image("file:" + this.image.getPath())));
        }
    }

    /**
     * Sets the image to editable.
     *
     * @param editable Should image be editable.
     */
    public void setEditable(boolean editable) {
        this.isEditable = editable;
        this.refresh();
    }

    /**
     * Gets a file from the file picker and saves that as profile picture.
     */
    private void selectPicture() {
        if (this.isEditable) {
            File source = this.fileChooser.showOpenDialog(new Stage());
            if (source != null && source.exists()) {
                String filepath = "data/profile-pictures/",
                        filename = new MD5().getDigest(source.getName());
                File destination = new File(filepath + filename);
                for (int i = 1; destination.exists(); i++) {
                    destination = new File(filepath + new MD5().getDigest(source.getName() + i));
                }
                if (!destination.exists()) {
                    try {
                        Files.copy(source.toPath(), destination.toPath());
                    } catch (IOException e) {
                        System.err.println("[ERROR]: Could not save profile picture..");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Could not upload picture.\nTry again.");
                        alert.showAndWait();
                        this.selectPicture();
                        return;
                    }
                }
                this.image = destination;
                this.profilePictureField.setValue(this.image.toPath().toAbsolutePath().toString());
            }
            this.refresh();
            this.refreshImage();
        }
    }

    /**
     * Deletes the picture from store and datbase.
     */
    private void deletePicture() {
        if (this.image != null && this.image.exists()) {
            this.image.delete();
        }
        this.image = null;
        this.profilePictureField.setValue("");
        this.refresh();
        this.refreshImage();
    }

    /**
     * Sets the contact to get the profile picture from.
     *
     * @param contact Contact to get picture from.
     */
    public void setContact(Contact contact) {
        if (contact != null) {
            this.profilePictureField = contact.getProfilePicture();
            this.alternativeText.setText(contact.getInitials());
            if (!this.profilePictureField.isEmpty()) {
                this.image = new File(this.profilePictureField.getValue());
            }
        }
        this.refresh();
        this.refreshImage();
    }


}
