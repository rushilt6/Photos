package controller;
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
import java.time.LocalDate;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.*;

public class UserController 
{
    private User user;
    private VBox albumVBox;
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

    private List<Photo> addedPhotos;
    

    public UserController(){
        try{
            this.user = (User)DataUtil.loadObjFromFile("data/user.ser");
        }
        catch(Exception e){
            this.user = new User(user.getUsername());
        }
    }
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
            
            albumVBox.getChildren().add(albumInfoBox);
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
    private File onChoosePhoto()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        return selectedFile;
    }
    @FXML
    private void onAddPhoto(File selectedFile)
    {
        String caption = photoCaption.getText();
        LocalDate currentDate = LocalDate.now();
        if (selectedFile != null) 
        {
            Photo photo = new Photo(selectedFile.getPath(), caption, currentDate);
            addedPhotos.add(photo);
        }
        photoCaption.clear();
    }


}