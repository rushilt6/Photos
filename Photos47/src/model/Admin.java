package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Admin class which holds all attributes of a single Admin
 * @authors Girish Ranganathan, Rushil Thummaluru
 */
public class Admin implements Serializable {
    private Map<String, User> users;

    /**
     * Admin constructor
     */
    public Admin(){
        this.users = new HashMap<>();
    }
    /**
     * gets users and returns a map of users
     * @return users
     */
    public Map<String, User> getUsers(){
        return users;
    }
    /**
     * returns true or false if a user exists
     * @param username
     * @return returns a boolean
     */
    public boolean userExists(String username){
        if(users.containsKey(username)) return true;
        return false;
    }
    /**
     * adds a user to a list
     * @param user the user that is going to be added
     */
    public void addUser(User user){
        users.put(user.getUsername(), user);
    }
    /**
     * removes a user
     * @param username the username of the user that is going to be removed
     */
    public void removeUser(String username){
        if(users.containsKey(username))
            users.remove(username);
    }
}
