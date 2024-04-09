package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Album class which stores all necessary attributes
 * @authors Girish Ranganathan, Rushil Thummaluru
 */
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
     * @return photos of an album
     */
    public Set<Photo> getPhotos(){
        return photos;
    }
    /**
     * Finds a specific photo in the album
     * @param photo Photo that needs to be found
     * @return returns null if the photo isn't there, or returns the photo if it is found
     */
    public Photo findPhoto(Photo photo){
        for(Photo p : photos){
            if(p.equals(photo)) return p;
        }
        return null;
    }
    /**
     * Adds the photo to the list of photos in an album
     * @param photo takes in the photo that is going to be added
     */
    public void addPhoto(Photo photo){
        photos.add(photo);
    }
    /**
     * Removes a photo from the album
     * @param photo Takes in a photo that is going to be removed
     */
    public void removePhoto(Photo photo){
        if(photos.contains(photo))
            photos.remove(photo);
    }
    /*
     * Returns a boolean if a photo is present in the album
     */
    public boolean containsPhoto(Photo photo)
    {
        return photos.contains(photo);
    }
    /*
     * Checks to see if an album is equal to another
     */
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
