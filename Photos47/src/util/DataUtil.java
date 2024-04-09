package util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
/**
 * Helps store and delete data so the user can close the application and still have their data saved
 * @authors Girish Ranganathan, Rushil Thummaluru
 */
public class DataUtil {
    /**
     * Saves the user object to file so that it can be accessed and still have all of its
     * attributes saved, like albums and photos
     * @param object Takes in an object, in this case user
     * @param filePath Takes the filePath to store the object to
     */
    public static void saveObjToFile(Object object, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(object);
        } catch (IOException e) {
            System.err.println("Error saving object to file: " + e.getMessage());
        }
    }
    /**
     * Loads the object(user) from file to restore the saved data
     * @param filePath Takes the filepath that the object data is stored in
     * @return Returns the object, or null if object isn't found
     */
    public static Object loadObjFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading object from file: " + e.getMessage());
            return null;
        }
    }
    /**
     * Generates a filename for a user to have
     * @param username generates the filename based on their username
     * @return Returns the String filename with a .ser at the end
     */
    public static String generateFilenameForUser(String username) {
        return username + ".ser";
    }
    /**
     * Deletes a file from existence along with all the saved data
     * @param username Takes in the usernmae so the program knows which file data to delete
     * @return Returns true or false based on if the file was able to be deleted
     */
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
