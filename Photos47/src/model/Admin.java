package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Admin implements Serializable {
    private Map<String, User> users;

    public Admin(){
        this.users = new HashMap<>();
    }
    public Map<String, User> getUsers(){
        return users;
    }
    public boolean userExists(String username){
        if(users.containsKey(username)) return true;
        return false;
    }
    public void addUser(User user){
        users.put(user.getUsername(), user);
    }
    public void removeUser(String username){
        if(users.containsKey(username))
            users.remove(username);
    }
}
