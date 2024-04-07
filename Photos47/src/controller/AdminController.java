package controller;
import java.io.File;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Admin;
import model.User;
import util.DataUtil;
public class AdminController {
    private Admin admin;

    @FXML
    private TextField addUser;
    @FXML
    private TextField deleteUser;
    @FXML
    private ListView<String> listUsers;


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

    @FXML
    private void onAddUser(){
        String username = addUser.getText();
        if(!username.isEmpty() && !admin.userExists(username)){
            User newUser = new User(username);
            admin.addUser(newUser);
            DataUtil.saveObjToFile(admin, "data/admin.ser");
            DataUtil.saveObjToFile(newUser, "data/"+username+".ser");
        }
    }

    @FXML
    private void onDeleteUser(){
        String username = deleteUser.getText();
        if(!username.isEmpty() && admin.userExists(username)){
            admin.removeUser(username);
            DataUtil.saveObjToFile(admin, "data/admin.ser");
            DataUtil.deleteFile(username);
        }
    }

    @FXML
    private void onListUsers(){
        listUsers.getItems().clear();
        for (Map.Entry<String,User> entry : admin.getUsers().entrySet()) {
            listUsers.getItems().add(entry.getKey());
        }
    }

}