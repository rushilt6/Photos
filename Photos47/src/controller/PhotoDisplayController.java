package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Photo;
import model.Tag;
import model.User;
import util.CommonUtil;

public class PhotoDisplayController {
    @FXML
    private ImageView imageView;
    @FXML
    private Label captionLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private ListView<Tag> tagListView;
    @FXML
    private Button backButton;

    private List<Photo> photos;
    private int currIndex;
    private User user;
    private String albumName;

    public void initSlideShow(List<Photo> photos, int index, User user, String albumName){
        this.user = user;
        this.photos = photos;
        this.albumName = albumName;
        currIndex = index;
        showPhoto(index);
    }
    private void showPhoto(int index){
        Photo photo = photos.get(index);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(photo.getPath()));
        captionLabel.setText(photo.getCaption());
        dateLabel.setText(photo.getDate().toString());
        tagListView.setItems(FXCollections.observableArrayList(new ArrayList<>(photo.getTags())));
    }
    @FXML
    private void goNext(){
        if(currIndex < photos.size() - 1){
            currIndex++;
            showPhoto(currIndex);
        }
    }
    @FXML
    private void goPrevious(){
        if(currIndex > 0){
            currIndex--;
            showPhoto(currIndex);
        }
    }
    @FXML
    private void onBack(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumDisplayView.fxml"));
            Parent userRoot = loader.load();
            AlbumDisplayController albumController = loader.getController();
            albumController.initUser(user,albumName);
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(userRoot, 600, 500);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            CommonUtil.errorGUI("Back not working");
        }
    }
}
