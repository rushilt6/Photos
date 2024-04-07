package util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CommonUtil {
    public static void errorGUI(String error){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(error);
        alert.showAndWait();
    }
}
