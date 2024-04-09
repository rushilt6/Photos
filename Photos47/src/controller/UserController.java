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
import model.SearchDisplay;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private ComboBox<String> searchTagsComboBox1;
    @FXML
    private ComboBox<String> searchTagsComboBox2;
    @FXML
    private TextField tagNameField1, tagValueField1;
    @FXML
    private TextField tagNameField2, tagValueField2;
    @FXML
    private TextField andOr;
    @FXML
    private TextField customTagNameField, customTagValueField;
    @FXML
    private TextField startDateField, endDateField;
    @FXML
    private Button logoutButton;


    private List<Photo> addedPhotos = new ArrayList<>();
    private User user;
    private File selectedFile;
    private Set<Tag> tagList = new HashSet<>();
    /**
     * User is allowed to logout and return to the login screen
     * @throws IOException
     */
    @FXML
    private void logout() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
        Parent rootView = loader.load();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Scene scene = new Scene(rootView,600, 500);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * User is initalized based on their username
     * @param username the username of the current user 
     */
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
    /**
     * Allows the user to open any album of their choice. Takes in an album aprameter
     * Helper method 
     * @param album The album that the user wants to open
     */
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
    /**
     * Displays the preset tags(Location and person)
     */
    @FXML
    public void displayPresetTags(){
        ObservableList<String> presetTags = FXCollections.observableArrayList();
        for(String tagname: user.getPresetTags()){
            presetTags.add(tagname);
        }
        presetTagsComboBox.setItems(presetTags);
        searchTagsComboBox1.setItems(presetTags);
        searchTagsComboBox2.setItems(presetTags);
        deletePresetTagsComboBox.setItems(presetTags);
    }
    /**
     * Displays all the albums that the current user has
     */
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
    /**
     * Helper method to calculate the range of dates within an album.
     * Helps print out the earliest and latest dates of photos within an album
     * @param album Takes an album and calculates its date range within the photos
     * @return
     */
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
    /**
     * Removes a tag from the presets
     */
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
    /**
     * Method to create an album. Can only be executed once pictures have been added.
     */
    @FXML
    private void onCreateAlbum() {
        String albumName = albumNameField.getText();
        if (albumName.isEmpty() || user.findAlbum(albumName) != null || addedPhotos.size() == 0) {
            CommonUtil.errorGUI("Error creating album, no Photos added, album already exists, or albumName is empty");
            return;
        }
        Album newAlbum = new Album(albumName);
        for(Photo p : addedPhotos)
        {
            newAlbum.addPhoto(p);
            DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
        }
        user.addAlbum(newAlbum);
        DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
        
        displayPresetTags();
        displayAlbums();
        albumNameField.clear();
        addedPhotos.clear();

    }
    /**
     * User is alloed to choose what photo they want to put into an album
     * Photos cannot exist outside of an album
     */
    @FXML
    private void onChoosePhoto()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg", "*.bmp"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        selectedFile = fileChooser.showOpenDialog(new Stage());
        
    }
    /**
     * User is allowed to add a tag to their photo based on the presets, or their own created tag
     */
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
    /**
     * User can click addPhoto once they have chosen a photo to add and a caption/tag if they want
     */
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
    /**
     * User can clear whatever preset tag they have selected
     */
    @FXML
    private void onClearSelection() {
        presetTagsComboBox.getSelectionModel().clearSelection();
    }
    /**
     * User can click delete album, which will only run if they have entered a valid 
     * album name in the area next to this button
     */
    @FXML
    private void onDeleteAlbum()
    {
        try
        {
            String deletedAlbumName = deletedAlbumText.getText();
            Album delAlbum = user.findAlbum(deletedAlbumName);
            if (delAlbum != null) 
            {            
                user.removeAlbum(deletedAlbumName);
                DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
                displayAlbums();  
            }
        }
        catch(Exception e)
        {
            CommonUtil.errorGUI("Album not found or text field empty!");
        }
        deletedAlbumText.clear();
    }
    /**
     * Allows user to rename an album.
     * They are only allowed to click this button if they have provided a current album name
     * and a new album name to rename the album
     */
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
    /**
     * Allows user to search for photos based on their tags or the date range
     * Users can search for photos with up to two tags and they can search
     * Conjunctively or Disjunctively. Users cannot search with tags and a date range
     */
    @FXML
    private void onSearchPhoto(){
       String tag1 = searchTagsComboBox1.getSelectionModel().getSelectedItem();
       String tag2 = searchTagsComboBox2.getSelectionModel().getSelectedItem();
       String tagName1 = tagNameField1.getText();
       String tagValue1 = tagValueField1.getText();
       String tagName2 = tagNameField2.getText();
       String tagValue2 = tagValueField2.getText();
       String beginDate = startDateField.getText();
       String endingDate = endDateField.getText();
       String conjunction = andOr.getText();
       if(tag1 != null) tagName1 = tag1;
       if(tag2 != null) tagName2 = tag2;
       Set<Photo> photos = user.getPhotos();
       List<Photo> selectedPhotos = new ArrayList<>();
       
       System.out.println(photos.size());
       if(!tagName1.equals("") && !tagValue1.equals("") && !tagName2.equals("") && !tagValue2.equals(""))
       {
            for(Photo p : photos)
            {
                if(conjunction.equalsIgnoreCase("and"))
                {
                    boolean hasTag1 = false;
                    boolean hasTag2 = false;
                    for(Tag t : p.getTags())
                    {
                        if(t.getName().equalsIgnoreCase(tagName1) && t.getValue().equalsIgnoreCase(tagValue1))
                        {
                            hasTag1 = true;
                        }
                        else if(t.getName().equalsIgnoreCase(tagName2) && t.getValue().equalsIgnoreCase(tagValue2))
                        {
                            hasTag2 = true;
                        }
                        if(hasTag1 && hasTag2)
                        {
                            selectedPhotos.add(p);
                            break; 
                        }
                    }
                }
                else if(conjunction.equalsIgnoreCase("or"))
                {
                    for(Tag t : p.getTags())
                    {
                        if(t.getName().equalsIgnoreCase(tagName1) && t.getValue().equalsIgnoreCase(tagValue1))
                        {
                            selectedPhotos.add(p);
                        }
                        if(t.getName().equalsIgnoreCase(tagName2) && t.getValue().equalsIgnoreCase(tagValue2))
                        {
                            selectedPhotos.add(p);
                        }
                    }
                }
            }
            if(selectedPhotos.isEmpty())
            {
                CommonUtil.errorGUI("No Photos In Range!");
            }
            else
            {
               searchPhotos(selectedPhotos);
            }
       }
       else if((!tagName1.equals("") && !tagValue1.equals("")) || (!tagName2.equals("") && !tagValue2.equals("")))
       {
            System.out.println("WE HERE BOYS");
            for(Photo p : photos)
            {
                for(Tag t : p.getTags())
                {
                    if((!tagName1.equals("") && !tagValue1.equals("") && t.getName().equalsIgnoreCase(tagName1) && t.getValue().equalsIgnoreCase(tagValue1))
                    || (!tagName2.equals("") && !tagValue2.equals("") && t.getName().equalsIgnoreCase(tagName2) && t.getValue().equalsIgnoreCase(tagValue2)))
                    {
                        selectedPhotos.add(p);
                    }
                }
            }
            if(selectedPhotos.isEmpty())
            {
                CommonUtil.errorGUI("No Photos In Range!");
            }
            else
            {
               searchPhotos(selectedPhotos);
            }
       }
       if(beginDate!=null && endingDate!=null)
       {
            LocalDate sd = LocalDate.parse(beginDate);
            LocalDate ed = LocalDate.parse(endingDate);
            for(Photo p : photos)
            {
                LocalDate photoDate = p.getDate();
                if ((photoDate.isEqual(sd) || photoDate.isAfter(sd)) && (photoDate.isEqual(ed) || photoDate.isBefore(ed)))
                {
                    selectedPhotos.add(p); 
                }
            }
            if(selectedPhotos.isEmpty())
            {
                CommonUtil.errorGUI("No Photos In Range!");
            }
            else
            {
                searchPhotos(selectedPhotos);
            }
        }
       tagNameField1.clear();
       tagNameField2.clear();
       tagValueField1.clear();
       tagValueField2.clear();
       startDateField.clear();
       endDateField.clear();
       andOr.clear();

   }
   /**
    * Helper method for searchPhotos which switches the screen to the screen 
    * to the search screen. 
    * @param photos List of photos that match the search criteria and need to be displayed on a different screen
    */
   private void searchPhotos(List<Photo> photos)
   {
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SearchDisplayView.fxml"));
        Parent root = loader.load();
        SearchDisplayController controller = loader.getController();
        controller.initUser(user, photos);
        Scene scene = new Scene(root);
        Stage stage = (Stage) albumListView.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        }catch(Exception e){
            e.printStackTrace();
            CommonUtil.errorGUI("Couldn't open photos");
        }
   }

    



}