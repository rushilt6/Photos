package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.DataUtil;

public class MainViewController {

    @FXML
    private TextField usernameTextField;

    @FXML
    protected void submitUserName(ActionEvent event) throws IOException {
        try{
            String username = usernameTextField.getText();
            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            System.out.println("Username entered: " + username);
            if(username.trim().equalsIgnoreCase("admin")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminView.fxml"));
                Parent adminRoot = loader.load();
                AdminController adminController = loader.getController();
                Scene adminScene = new Scene(adminRoot,600, 500);
                stage.setScene(adminScene);
                stage.show();
            }
            else if(username.trim().equalsIgnoreCase("user"))
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserView.fxml"));
                Parent userRoot = loader.load();
                UserController userController = loader.getController();
                Scene userScene = new Scene(userRoot,600, 500);
                stage.setScene(userScene);
                stage.show();
            }
        } catch(Exception e){
            System.out.println("File not found!");
        }
        
    }
}