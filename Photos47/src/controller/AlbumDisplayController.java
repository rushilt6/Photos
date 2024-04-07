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

public class AlbumDisplayController {
    @FXML
    private ListView<Photo> photoListView;
    @FXML
    private Button backButton;
    @FXML
    private TextField captionTextField;
    
    private User user;
    private String albumName;

    private Photo currentPhoto;
    
    public void initUser(User user, String albumName){
        this.user = user;
        this.albumName = albumName;
        setPhotoListCellFactory();
        displayPhotos();
        photoListView.setOnMouseClicked((event) ->{
            if(event.getClickCount() == 2){
                
            }
            if(event.getClickCount() == 1){
                
            }
        });
    }
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
    @FXML
    private void displayPhotos(){
        ObservableList<Photo> photos = FXCollections.observableArrayList();
        Album album = user.findAlbum(albumName);
        for(Photo photo : album.getPhotos()){
            photos.add(photo);
        }
        photoListView.setItems(photos);
    }
    @FXML
    private void onUpdateCaption(){

    }
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
}
