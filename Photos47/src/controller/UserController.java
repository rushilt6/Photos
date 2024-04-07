package controller;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Admin;
import model.Album;
import model.AlbumDisplay;
import model.Photo;
import model.Tag;
import model.User;
import util.CommonUtil;
import util.DataUtil;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.*;

public class UserController 
{
    @FXML
    private TextField albumNameField;
    @FXML
    private TextField photoCaption;
    @FXML
    private TextField deletedAlbumText;
    @FXML
    private TextField renameAlbumText;
    @FXML
    private TextField newNameAlbumText;
    @FXML
    private TextField openAlbumText;
    @FXML
    private ListView<AlbumDisplay> albumListView;
    @FXML
    private ComboBox<String> presetTagsComboBox;
    @FXML
    private ComboBox<String> deletePresetTagsComboBox;
    @FXML
    private TextField customTagNameField, customTagValueField;
    @FXML
    private Button logoutButton;


    private List<Photo> addedPhotos = new ArrayList<>();
    private User user;
    private File selectedFile;
    private Set<Tag> tagList = new HashSet<>();

    @FXML
    private void logout() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
        Parent rootView = loader.load();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Scene scene = new Scene(rootView,600, 500);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void initialize(String username){
        user = (User)DataUtil.loadObjFromFile("data/"+DataUtil.generateFilenameForUser(username));
        displayPresetTags();
        displayAlbums();
        albumListView.setOnMouseClicked((event) ->{
            if(event.getClickCount() == 2){
                AlbumDisplay currAlbum = albumListView.getSelectionModel().getSelectedItem();
                openAlbum(currAlbum.getAlbum());
            }
        });
    }
    private void openAlbum(Album album){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumDisplayView.fxml"));
            Parent root = loader.load();
            AlbumDisplayController controller = loader.getController();
            controller.initUser(user, album.getName());
            Scene scene = new Scene(root);
            Stage stage = (Stage) albumListView.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }catch(Exception e){
            e.printStackTrace();
            CommonUtil.errorGUI("Couldn't open album");
        }
    }

    @FXML
    public void displayPresetTags(){
        ObservableList<String> presetTags = FXCollections.observableArrayList();
        for(String tagname: user.getPresetTags()){
            presetTags.add(tagname);
        }
        presetTagsComboBox.setItems(presetTags);
        deletePresetTagsComboBox.setItems(presetTags);
    }
    @FXML
    public void displayAlbums() {
        ObservableList<AlbumDisplay> obsList = FXCollections.observableArrayList();
        Map<String, Album> albums = user.getAlbums();
        albums.forEach((name, album) -> {
            int numPics = album.getPhotos().size();
            String dateRange = calculateDateRange(album);
            obsList.add(new AlbumDisplay(album, name, numPics, dateRange));
        });
        albumListView.setItems(obsList);
    }

    private String calculateDateRange(Album album) 
    {
        // Get the list of photos in the album
        Set<Photo> photos = album.getPhotos();
        
        
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
    private void onRemoveFromPresets(){
        String tag = deletePresetTagsComboBox.getSelectionModel().getSelectedItem();
        if(tag != null){
            user.getPresetTags().remove(tag);
            DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
            displayPresetTags();
        }
        else{
            CommonUtil.errorGUI("Tag is null");
        }
    }

    @FXML
    private void onCreateAlbum() {
        String albumName = albumNameField.getText();
        if (albumName.isEmpty() || user.findAlbum(albumName) != null || addedPhotos.size() == 0) {
            CommonUtil.errorGUI("Error creating album, no Photos added, album already exists, or albumName is empty");
            return;
        }
        Album newAlbum = new Album(albumName);
        for(Photo p : addedPhotos)
            newAlbum.addPhoto(p);
        addedPhotos.clear();
        user.addAlbum(newAlbum);
        DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
        
        displayPresetTags();
        displayAlbums();
        albumNameField.clear();

    }
    @FXML
    private void onChoosePhoto()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        selectedFile = fileChooser.showOpenDialog(new Stage());
    }
    @FXML
    private void onAddTag(){
        String presetTag = presetTagsComboBox.getSelectionModel().getSelectedItem();
        String tagName = customTagNameField.getText();
        String tagValue = customTagValueField.getText();
        if(presetTag != null) tagName = presetTag;
        if(tagName != null && tagValue !=null)
            tagList.add(new Tag(tagName, tagValue));
        customTagNameField.clear();
        customTagValueField.clear();
    }
    @FXML
    private void onAddPhoto()
    {
        try{
            String caption = photoCaption.getText();
            long lastModifiedDate = selectedFile.lastModified();
            LocalDate date = Instant.ofEpochMilli(lastModifiedDate)
                                                .atZone(ZoneId.systemDefault())
                                                .toLocalDate();
            if (selectedFile != null && caption != null) 
            {
                Photo photo = new Photo(selectedFile.toURI().toString(), caption, date);
                for(Tag t: tagList){
                    photo.addTag(t);
                    user.getPresetTags().add(t.getName());
                }
                tagList.clear();
                addedPhotos.add(photo);
            }
            photoCaption.clear();
            selectedFile = null;
        }catch(Exception e){
            CommonUtil.errorGUI("One of the fields is null!");
        }
    }
    @FXML
    private void onClearSelection() {
        presetTagsComboBox.getSelectionModel().clearSelection();
    }
    @FXML
    private void onDeleteAlbum()
    {
        try
        {
            String deletedAlbumName = deletedAlbumText.getText();
            user.removeAlbum(deletedAlbumName);
            DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
            displayAlbums();
        }
        catch(Exception e)
        {
            CommonUtil.errorGUI("Album not found or text field empty!");
        }
        deletedAlbumText.clear();
    }
    @FXML
    private void onRenameAlbum()
    {
        try
        {
            String oldName = renameAlbumText.getText();
            String newName = newNameAlbumText.getText();
            if(user.findAlbum(newName) == null){
                Album newAlbum = user.findAlbum(oldName);
                newAlbum.setName(newName);
                user.getAlbums().remove(oldName);
                user.getAlbums().put(newName, newAlbum);
                DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
                displayAlbums();
            }
            else{
                CommonUtil.errorGUI("Album already exists!");
            }
        }
        catch(Exception e)
        {
            CommonUtil.errorGUI("Album not found!");
        }
        renameAlbumText.clear();
        newNameAlbumText.clear();
    }

}