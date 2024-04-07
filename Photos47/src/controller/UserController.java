package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Admin;
import model.Album;
import model.Photo;
import model.User;
import util.DataUtil;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.*;

public class UserController 
{
    @FXML
    private TextField albumNameField;
    @FXML
    private TextField photoCaption;
    @FXML
    private Button createAlbumButton;
    @FXML
    private Button choosePhotoButton;
    @FXML
    private Button addPhotoButton;

    private List<Photo> addedPhotos = new ArrayList<>();;
    private User user;
    private File selectedFile;

<<<<<<< HEAD
    

=======

    @FXML
    public void initialize(String username){
        user = (User)DataUtil.loadObjFromFile("data/"+DataUtil.generateFilenameForUser(username));
    }
>>>>>>> 06e9342d280e9ff3f1e3876cf9e17c70796fcca0
    @FXML
    public void displayAlbums() {
        Map<String, Album> albums = user.getAlbums();
        
        for (Map.Entry<String, Album> entry : albums.entrySet()) {
            String albumName = entry.getKey();
            Album album = entry.getValue();
            int photoCount = album.getPhotos().size();
            String dateRange = calculateDateRange(album);

            HBox albumInfoBox = new HBox(10); 
            
            Label albumLabel = new Label("Album: " + albumName);
            Label photoCountLabel = new Label("Number of Photos: " + photoCount);
            Label dateRangeLabel = new Label("Date Range: " + dateRange);
            
            albumInfoBox.getChildren().addAll(albumLabel, photoCountLabel, dateRangeLabel);
            
        }
    }
    private String calculateDateRange(Album album) 
    {
        // Get the list of photos in the album
        Set<Photo> photos = album.getPhotos();
        
        if (photos.isEmpty()) {
            return "No photos";
        }
        
        // Initialize earliest and latest dates to the first photo's date
        LocalDate earliestDate = null;
        LocalDate latestDate = null;
        boolean firstPhoto = true;
        for(Photo p : photos)
        {
            LocalDate dateTaken = p.getDate();
            if (firstPhoto) {
                earliestDate = dateTaken;
                latestDate = dateTaken;
                firstPhoto = false;
            } else {
                // Update earliest and latest dates if necessary
                if (dateTaken.isBefore(earliestDate)) {
                    earliestDate = dateTaken;
                }
                if (dateTaken.isAfter(latestDate)) {
                    latestDate = dateTaken;
                }
            }
        }      
        return earliestDate + " - " + latestDate;
    }
    @FXML
    private void onCreateAlbum() {
        String albumName = albumNameField.getText();
        if (albumName.isEmpty()) {
            return;
        }
        Album newAlbum = new Album(albumName);
        for(Photo p : addedPhotos)
            newAlbum.addPhoto(p);
        addedPhotos.clear();
        user.addAlbum(newAlbum);
        displayAlbums();
        albumNameField.clear();

    }
    @FXML
    private void onChoosePhoto()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        selectedFile = fileChooser.showOpenDialog(new Stage());
        
    }
    @FXML
    private void onAddPhoto()
    {
        String caption = photoCaption.getText();
        long lastModifiedDate = selectedFile.lastModified();
        LocalDate date = Instant.ofEpochMilli(lastModifiedDate)
                                             .atZone(ZoneId.systemDefault())
                                             .toLocalDate();
        if (selectedFile != null && caption != null) 
        {
            Photo photo = new Photo(selectedFile.getPath(), caption, date);
            addedPhotos.add(photo);
        }
        photoCaption.clear();
        selectedFile = null;
    }


}