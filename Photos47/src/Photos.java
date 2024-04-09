import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the application which is meant to be run to start the whole process
 * @authors Girish Ranganathan, Rushil Thummaluru
 */
public class Photos extends Application {
    
    /**
     * Start method 
     * @primaryStage Takes in the current stage at which the application starts at
     * In this case, the application will always start at the mainView
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml")); 
        primaryStage.setTitle("Photo Application");
        primaryStage.setScene(new Scene(root,600,500));
        primaryStage.show();
    }
    /**
     * Launches the program
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
