package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CommonUtil {
    /**
     * This is a way to print out error messgaes in the GUI if a user encounters an error, 
     * such as adding a photo when there is no photo file selected
     * @param error Takes in a string error to display
     */
    public static void errorGUI(String error){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(error);
        alert.showAndWait();
    }
}
