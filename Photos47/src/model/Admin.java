package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
     * @return
     */
    public Map<String, User> getUsers(){
        return users;
    }
    /**
     * returns true or false if a user exists
     * @param username
     * @return
     */
    public boolean userExists(String username){
        if(users.containsKey(username)) return true;
        return false;
    }
    /**
     * adds a user to a list
     * @param user
     */
    public void addUser(User user){
        users.put(user.getUsername(), user);
    }
    /**
     * removes a user
     * @param username
     */
    public void removeUser(String username){
        if(users.containsKey(username))
            users.remove(username);
    }
}
