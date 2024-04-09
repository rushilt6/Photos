package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Photo implements Serializable {

    private String filePath;
    private String caption;
    private LocalDate date;
    private Set<model.Tag> tags;

    /**
     * Constructor for Photo
     * @param filePath Takes in the filePath for the photo
     * @param caption Takes in the caption for the photo
     * @param date Takes in the date the photo was added
     */
    public Photo(String filePath, String caption, LocalDate date){
        this.filePath = filePath;
        this.caption = caption;
        this.date = date;
        this.tags = new HashSet<>();
    }
    /**
     * Gets the path of the photo
     * @return Returns the path of the photo as a String
     */
    public String getPath(){
        return filePath;
    }
    /**
     * Sets the path of a photo
     * @param newPath Takes in the new path of the photo
     */
    public void setPath(String newPath){
        filePath = newPath;
    }
    /**
     * Gets the caption of a photo
     * @return caption
     */
    public String getCaption(){
        return caption;
    }
    /**
     * Sets the caption of the phoot
     * @param newCaption Takes in the new caption of the photo
     */
    public void setCaption(String newCaption){
        caption = newCaption;
    }
    /**
     * Gets the tags of a photo
     * @return the set of tags on that photo
     */
    public Set<Tag> getTags(){
        return tags;
    }
    /**
     * Gets the LocalDate of a photo
     * @return LocalDate(date that the photo was first added)
     */
    public LocalDate getDate(){
        return date;
    }
    /**
     * Sets the photo as a new date
     * @param newDate takes in a new date
     */
    public void setDate(LocalDate newDate){
        date = newDate;
    }
    /**
     * Adds a tag to a photo
     * @param tag Takes in the tag that needs to be added
     */
    public void addTag(Tag tag){
        tags.add(tag);
    }
    /**
     * Removes a tag from a photo
     * @param tag Takes in the tag that needs to be removed
     */
    public void removeTag(Tag tag){
        if(tags.contains(tag))
            tags.remove(tag);
    }
    /**
     * Checks to see if two photos are equal to each other
     */
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || (!(o instanceof Tag))) return false;
        Photo other = (Photo) o;
        return filePath.equals(other.getPath());
    }
    public String toString() {
        return "Photo{" + "filePath='" + filePath + '\'' + ", caption='" + caption + '\'' + ", dateOfPhoto=" + date + ", tags=" + tags + '}';
    }
}
