package controller;

import java.io.IOException;
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
import javafx.stage.Stage;
import model.Album;
import model.AlbumDisplay;
import model.Photo;
import model.User;
import util.CommonUtil;
import util.DataUtil;

/**
 * This is responsbile for controlling the scene where a user is looking for searched photos
 * @authors Girish Ranganathan, Rushil Thummaluru
 */
public class SearchDisplayController {
    @FXML
    private ListView<Photo> photoListView;
    @FXML
    private Button backButton;
    @FXML
    private TextField albumNameText;
    
    private User user;
    private List<Photo> photos;

    private Photo currentPhoto;
    
    /**
     * Once the search has been executed, this method stores the user that initiated the search
     * and the photos that fit the search criteria
     * @param user The current user who is trying to search for photos
     * @param photos The list of photos that match the description for search
     */
    public void initUser(User user, List<Photo> photos){
        this.user = user;
        this.photos = photos;
        setPhotoListCellFactory();
        displayPhotos();
        photoListView.setOnMouseClicked((event) ->{
            if(event.getClickCount() == 2){
                
            }
            if(event.getClickCount() == 1){
                
            }
        });
    }
    /**
     * Allows the user to back out of the search photos screen
     */
    @FXML
    private void onBack(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserView.fxml"));
            Parent userRoot = loader.load();
            UserController userController = loader.getController();
            userController.initialize(user.getUsername());
            System.out.println(user.getUsername());
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(userRoot, 600, 500);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            CommonUtil.errorGUI("Back not working");
        }
    }
    /**
     * Displays the photos that were searched for
     */
    @FXML
    private void displayPhotos(){
        ObservableList<Photo> photo= FXCollections.observableArrayList();
        for(Photo p : photos){
            photo.add(p);
        }
        photoListView.setItems(photo);
    }
    /**
     * Sets a custom cell factory for the photoListView, rendering each item with an ImageView
     * and a Label in an HBox layout. Each cell displays a photo and its corresponding caption.
     * 
     * The method sets up a ListCell factory, defining the appearance and behavior of each cell.
     * It configures the cell to display an ImageView alongside a Label in an HBox layout,
     * with specific size settings for the ImageView. The ImageView displays the image
     * corresponding to the photo's path, and the Label displays the caption of the photo.
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
     * Allows the user to create a new album based on the searched photos.
     */
    @FXML
    private void onCreateAlbum()
    {
        String albumName = albumNameText.getText();
        if (albumName.isEmpty() || user.findAlbum(albumName) != null || photos.size() == 0) {
            CommonUtil.errorGUI("Error creating album, no Photos added, album already exists, or albumName is empty");
            return;
        }
        Album newAlbum = new Album(albumName);
        for(Photo p : photos)
        {
            newAlbum.addPhoto(p);
            DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
        }
        user.addAlbum(newAlbum);
        DataUtil.saveObjToFile(user, "data/"+user.getUsername()+".ser");
        albumNameText.clear();
    }
}