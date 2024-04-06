package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainViewController {

    @FXML
    private TextField usernameTextField;

    @FXML
    protected void handleSubmitAction() {
        String username = usernameTextField.getText();
        System.out.println("Username entered: " + username);
    }
}