package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Album implements Serializable{
    private String name;
    private Set<Photo> photos;
    /**
     * Album constructor that takes in a name
     * @param name name of the album
     */
    public Album(String name){
        this.name = name;
        this.photos = new HashSet<>();
    }
    /**
     * returns the name of the album
     * @return name of the album
     */
    public String getName(){
        return name;
    }
    /**
     * sets the name of an album
     * @param newName new name of album
     */
    public void setName(String newName){
        name = newName;
    }
    /**
     * Gets the photos within an album
     * @return
     */
    public Set<Photo> getPhotos(){
        return photos;
    }
    public Photo findPhoto(Photo photo){
        for(Photo p : photos){
            if(p.equals(photo)) return p;
        }
        return null;
    }
    public void addPhoto(Photo photo){
        photos.add(photo);
    }
    public void removePhoto(Photo photo){
        if(photos.contains(photo))
            photos.remove(photo);
    }
    public boolean containsPhoto(Photo photo)
    {
        return photos.contains(photo);
    }
    public boolean equals(Object o){
        if (this==o) return true;
        if(o == null || (!(o instanceof Album))) return false;
        Album other = (Album) o;
        return name.equals(other.getName());
    }
    public int hashCode(){
        return Objects.hash(name);
    }
    public String toString() {
        return "Album{" + "name='" + name + '\'' + ", photos=" + photos +'}';
    }
}
