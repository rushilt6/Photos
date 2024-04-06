package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class User implements Serializable{
    private String username;
    private Map<String, Album> albums;
    private Set<Photo> photos;
    private Set<Tag> presetTags;

    public User(String username){
        this.username = username;
        this.albums = new HashMap<>();
        photos = new HashSet<>();
        presetTags = new HashSet<>();
    }
    public String getUsername(){
        return username;
    }
    public Map<String, Album> getAlbums(){
        return albums;
    }
    public void addAlbum(Album album){
        albums.put(album.getName(), album);
    }
    public void removeAlbum(String albumName){
        if(albums.containsKey(albumName)){
            albums.remove(albumName);
        }
    }
    public Album findAlbum(String albumName){
        return albums.get(albumName);
    }
    public Set<Photo> getPhotos(){
        return photos;
    }
    public void addPhoto(Photo photo){
        photos.add(photo);
    }
    public void removePhoto(Photo photo){
        if(photos.contains(photo))
            photos.remove(photo);
    }
    public Set<Tag> getPresetTags(){
        return presetTags;
    }
    public void addPresetTag(Tag tag){
        presetTags.add(tag);
    }
    public void removePresetTag(Tag tag){
        presetTags.remove(tag);
    }
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
