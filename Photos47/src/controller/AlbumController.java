package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.*;

public class AlbumController 
{
    private Album album;
    @FXML
    private TextField photoCaption;
    @FXML
    private Button addPhotoButton;
    @FXML
    private Button choosePhotoButton;
    
    public void setAlbum(Album album) 
    {
        this.album = album;
    }
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
    private void onAddPhoto()
    {
        File selectedFile = onChoosePhoto();
        String caption = photoCaption.getText();
        LocalDate currentDate = LocalDate.now();
        Photo photo = new Photo(selectedFile.getPath(), caption, currentDate);
        album.addPhoto(photo);
        DataUtil.saveObjToFile(album, "data/"+album.getName()+".ser");
        photoCaption.clear();
    }

}
