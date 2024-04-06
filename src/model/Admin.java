package src.model;

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
    public void addUser(User user){
        users.put(user.getUsername(), user);
    }
    public void removeUser(User user){
        if(users.containsKey(user.getUsername()))
            users.remove(user.getUsername());
    }
}
