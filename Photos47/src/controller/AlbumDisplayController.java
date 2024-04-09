package controller;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

import javax.security.auth.callback.Callback;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.AlbumDisplay;
import model.Photo;
import model.Tag;
import model.User;
import util.CommonUtil;
import util.DataUtil;

public class AlbumDisplayController {
    @FXML
    private ListView<Photo> photoListView;
    @FXML
    private Button backButton;
    @FXML
    private TextField captionTextField;
    @FXML
    private TextField copyTextField;
    @FXML
    private TextField moveTextField;
    @FXML
    private TextField addTagNameTextField;
    @FXML
    private TextField addTagValueTextField;
    @FXML
    private TextField deleteTagNameTextField;
    @FXML
    private TextField deleteTagValueTextField;
    private User user;
    private String albumName;
    

    /**
     * Initializes the user and keeps track of the album that is opened. 
     * The album name is the key in order to get the rest of the album data
     * @param user Takes in the current user that is trying to display an album
     * @param albumName Takes in the albumName that the user is trying to open
     */
    public void initUser(User user, String albumName){
        this.user = user;
        this.albumName = albumName;
        setPhotoListCellFactory();
        displayPhotos();
        photoListView.setOnMouseClicked((event) ->{
            if(event.getClickCount() == 2){
                Photo selectedPhoto = photoListView.getSelectionModel().getSelectedItem();
                if(selectedPhoto != null) {
                    openPhotoDisplay(selectedPhoto);
                }            
            }
        });
    }
    /**
     * Allows the user who is in the album display to go back to the user display
     */
    @FXML
    private void onBack(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserView.fxml"));
            Parent userRoot = loader.load();
            UserController userController = loader.getController();
            userController.initialize(user.getUsername());
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(userRoot, 600, 500);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            CommonUtil.errorGUI("Back not working");
        }
    }
    /**
     * Displays the photos that are contained within the opened album
     */
    @FXML
    private void displayPhotos(){
        ObservableList<Photo> photos = FXCollections.observableArrayList();
        Album album = user.findAlbum(albumName);
        for(Photo photo : album.getPhotos()){
            photos.add(photo);
        }
        photoListView.setItems(photos);
    }
    /**
     * Copies the photo
     */
    @FXML
    private void onCopyPhoto(){
        Photo currentPhoto = photoListView.getSelectionModel().getSelectedItem();
        String copyToAlbumName = copyTextField.getText();
        if(copyToAlbumName != null && user.findAlbum(copyToAlbumName)!=null){
            user.findAlbum(copyToAlbumName).addPhoto(currentPhoto);
            DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
            displayPhotos();
            copyTextField.clear();
        }else{
            CommonUtil.errorGUI("Album doesn't exist!");
        }
    }
    @FXML
    private void onMovePhoto(){
        Photo currentPhoto = photoListView.getSelectionModel().getSelectedItem();
        String moveToAlbumName = moveTextField.getText();
        if(moveToAlbumName != null && user.findAlbum(moveToAlbumName)!=null){
            user.findAlbum(moveToAlbumName).addPhoto(currentPhoto);
            user.findAlbum(albumName).removePhoto(currentPhoto);
            DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
            displayPhotos();
            moveTextField.clear();
        }else{
            CommonUtil.errorGUI("Album doesn't exist!");
        }
    }
    /**
     * Allows user to update captions on photos within the opened album
     */
    @FXML
    private void onUpdateCaption(){
        Photo currentPhoto = photoListView.getSelectionModel().getSelectedItem();
        String newCaption = captionTextField.getText();
        if(currentPhoto != null && newCaption != null){
            user.findAlbum(albumName).findPhoto(currentPhoto).setCaption(newCaption);
            DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
            displayPhotos();
            captionTextField.clear();
        }else{
            CommonUtil.errorGUI("Pick a photo to edit and type in the value");
        }
    }
    /**
     * Allows user to add photos to an opened album
     */
    @FXML
    private void onAddPhoto(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg", "*.bmp"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        try{
            long lastModifiedDate = selectedFile.lastModified();
            LocalDate date = Instant.ofEpochMilli(lastModifiedDate)
                                                .atZone(ZoneId.systemDefault())
                                                .toLocalDate();
            if (selectedFile != null) 
            {
                Photo photo = new Photo(selectedFile.toURI().toString(), "", date);
                user.findAlbum(albumName).addPhoto(photo);
                DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
                displayPhotos();
            }

        }catch(Exception e){
            CommonUtil.errorGUI("One of the fields is null!");
        }
    }
    /**
     * Allows user to delete photos within a given opened album
     */
    @FXML
    private void onDeletePhoto(){
        Photo currentPhoto = photoListView.getSelectionModel().getSelectedItem();
        if(currentPhoto != null){
            user.findAlbum(albumName).removePhoto(currentPhoto);
            DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
            displayPhotos();
        }else{
            CommonUtil.errorGUI("Select a photo to be deleted");
        }
    }
    /**
     * Allows the user to add a tag to any of the photos in an album
     */
    @FXML
    private void onAddTag(){
        Photo currentPhoto = photoListView.getSelectionModel().getSelectedItem();
        String newTagName = addTagNameTextField.getText();
        String newTagValue = addTagValueTextField.getText();
        if(currentPhoto != null && newTagName != null && newTagValue != null){
            user.findAlbum(albumName).findPhoto(currentPhoto).addTag(new Tag(newTagName, newTagValue));
            DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
            displayPhotos();
            addTagNameTextField.clear();
            addTagValueTextField.clear();
        }else{
            CommonUtil.errorGUI("Pick a photo to edit and type in the value");
        } 
    }
    /**
     * Allows user to delete a tag on a photo within the opened album
     */
    @FXML
    private void onDeleteTag(){
        Photo currentPhoto = photoListView.getSelectionModel().getSelectedItem();
        String newTagName = deleteTagNameTextField.getText();
        String newTagValue = deleteTagValueTextField.getText();
        if(currentPhoto != null && newTagName != null && newTagValue != null){
            user.findAlbum(albumName).findPhoto(currentPhoto).removeTag(new Tag(newTagName, newTagValue));
            DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
            displayPhotos();
            deleteTagNameTextField.clear();
            deleteTagValueTextField.clear();
        }else{
            CommonUtil.errorGUI("Pick a photo to edit and type in the value");
        } 
    }
    /** 
     * Sets a custom cell factory for the photoListView, which is a ListView of Photo objects.
     * Each cell in the ListView will display an ImageView and a Label containing the photo's caption.
     * The ImageView displays the photo's image and the Label displays the photo's caption.
     * 
     * The cell factory ensures that each cell is properly populated with the corresponding photo's data.
     * 
     * This method utilizes JavaFX to achieve the desired cell layout and functionality.
     * 
    * @see Photo Photo is a class in model
    * @see ListView
    */
    private void setPhotoListCellFactory(){
        photoListView.setCellFactory(param -> new ListCell<Photo>(){
            private ImageView imageView = new ImageView();
            private Label label = new Label();
            private HBox hBox = new HBox(8, imageView, label);
            {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(5, 0, 5, 10));
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                imageView.setPreserveRatio(true);
            }
            @Override
            public void updateItem(Photo photo, boolean empty){
                super.updateItem(photo, empty);
                if (empty || photo == null)
                    setGraphic(null);
                else{
                    imageView.setImage(new Image(photo.getPath()));
                    label.setText(photo.getCaption());
                    setGraphic(hBox);
                }
            }
        });
    }
    /**
     * Slideshow for the photos in an album
     * Goes to the photodisplay controller which is where the slideshow is
     * @param currPhoto Takes in the current photo on the slideshow
     */
    private void openPhotoDisplay(Photo currPhoto){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PhotoDisplay.fxml"));
            Parent PhotoDisplayroot = loader.load();
            PhotoDisplayController controller = loader.getController();
            controller.initSlideShow(photoListView.getItems(), photoListView.getItems().indexOf(currPhoto), user,albumName);
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(PhotoDisplayroot, 600, 500);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
            CommonUtil.errorGUI("Couldn't open Photo Display");
        }
    }
}
