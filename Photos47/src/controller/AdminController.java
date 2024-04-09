package controller;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Admin;
import model.User;
import util.CommonUtil;
import util.DataUtil;
public class AdminController {
    private Admin admin;

    @FXML
    private TextField addUser;
    @FXML
    private TextField deleteUser;
    @FXML
    private ListView<String> listUsers;
    @FXML
    private Button logoutButton;

    /**
     * Creates one singular adimin
     */
    public AdminController(){
        File file = new File("data/admin.ser");
        if(file.exists()){
            this.admin = (Admin)DataUtil.loadObjFromFile("data/admin.ser");
        }
        else{
            this.admin = new Admin();
            DataUtil.saveObjToFile(admin, "data/admin.ser");
        }
    }
    /**
     * Logs the admin out and returns to the login screen
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
     * Admin is able to add users, and thats what this method does
     */
    @FXML
    private void onAddUser(){
        String username = addUser.getText();
        if(!username.isEmpty() && !admin.userExists(username)){
            User newUser = new User(username);
            admin.addUser(newUser);
            DataUtil.saveObjToFile(admin, "data/admin.ser");
            DataUtil.saveObjToFile(newUser, "data/"+username+".ser");
        }
        else{
            CommonUtil.errorGUI("User already exists/null value!");
        }
        
    }
    /**
     * Admin is able to delete a user
     */
    @FXML
    private void onDeleteUser(){
        String username = deleteUser.getText();
        if(!username.isEmpty() && admin.userExists(username)){
            admin.removeUser(username);
            DataUtil.saveObjToFile(admin, "data/admin.ser");
            DataUtil.deleteFile(username);
        }
        else{
            CommonUtil.errorGUI("User doesn't exist!");
        }
    }
    /**
     * Displays the whole list of users that were created in a session.
     * Saves users, even if the app is closed
     */
    @FXML
    private void onListUsers(){
        listUsers.getItems().clear();
        for (Map.Entry<String,User> entry : admin.getUsers().entrySet()) {
            listUsers.getItems().add(entry.getKey());
        }
    }

}
