package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * User class which holds attributes specific to the User, like their name and albums. There can be many users
 * @authors Girish Ranganathan, Rushil Thummaluru
 */
public class User implements Serializable{
    private String username;
    private Map<String, Album> albums;
    private Set<Photo> photos;
    private Set<String> presetTags;
    /**
     * Constructor for user
     * @param username Takes in the username of the user
     */
    public User(String username){
        this.username = username;
        this.albums = new HashMap<>();
        photos = new HashSet<>();
        presetTags = new HashSet<>();
        presetTags.add("person");
        presetTags.add("location");
    }
    /**
     * gets the username of the user
     * @return username
     */
    public String getUsername(){
        return username;
    }
    /**
     * Gets the albums that the user has
     * @return albums of the user as a Map<String, Album>
     */
    public Map<String, Album> getAlbums(){
        return albums;
    }
    /**
     * Adds an album to the user
     * @param album takes in the album that needs to be added
     */
    public void addAlbum(Album album){
        albums.put(album.getName(), album);
    }
    /**
     * Removes an album from the user
     * @param albumName Takes in the album name that the user wants to delete
     */
    public void removeAlbum(String albumName){
        if(albums.containsKey(albumName)){
            albums.remove(albumName);
        }
    }
    /**
     * Finds an album based on the name of an album
     * @param albumName Takes in the album name that we want to find
     * @return the album if it is found
     */
    public Album findAlbum(String albumName){
        return albums.get(albumName);
    }
    /**
     * Gets the photos that a user has
     * @return all the photos the user has
     */
    public Set<Photo> getPhotos(){
        photos = new HashSet<>();
        for(Map.Entry<String, Album> entry : albums.entrySet()){
            for(Photo photo : entry.getValue().getPhotos()){
                photos.add(photo);
            }
        }
        return photos;
    }
    /**
     * Gets the preset tags that the user has access to
     * @return the preset tags
     */
    public Set<String> getPresetTags(){
        return presetTags;
    }
    /**
     * Allows user to add their own preset tags
     * @param tagname takes in the name of the preset tag the user wants to add
     */
    public void addPresetTag(String tagname){
        presetTags.add(tagname);
    }
    /**
     * Allows user to remove a preset tag they have created
     * @param tagname Takes in the name of the preset tag they want to remove
     */
    public void removePresetTag(String tagname){
        presetTags.remove(tagname);
    }
    /**
     * Checks to see if a user is equal to another user
     */
    public boolean equals(Object o){
        if(this==o) return true;
        if (o == null || (!(o instanceof User))) return false;
        User other = (User) o;
        return this.username.equals(other.getUsername());
    }
    public int hashCode(){
        return Objects.hash(username);
    }
    public String toString() {
        return "User{" + "username='" + username + '\'' + ", albums=" + albums.keySet() +'}';
    }
}
