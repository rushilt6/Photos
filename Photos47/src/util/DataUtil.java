package util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
public class DataUtil {
    public static void saveObjToFile(Object object, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(object);
        } catch (IOException e) {
            System.err.println("Error saving object to file: " + e.getMessage());
        }
    }
    public static Object loadObjFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading object from file: " + e.getMessage());
            return null;
        }
    }
    public static String generateFilenameForUser(String username) {
        return username + ".ser";
    }
    public static boolean deleteFile(String username){
        String filename = generateFilenameForUser(username);
        File file = new File("data" + File.separator + filename);
        if(file.exists()){
            return file.delete();
        }
        else{
            System.err.println("File does not exist for user: " + username);
            return false;
        }
    }
}
