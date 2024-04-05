package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
public class Photo implements Serializable {

    private String filePath;
    private String caption;
    private LocalDate date;
    private Set<Tag> tags;

    public Photo(String filePath, String caption, LocalDate date){
        this.filePath = filePath;
        this.caption = caption;
        this.date = date;
        this.tags = new HashSet<>();
    }

    public String getPath(){
        return filePath;
    }
    public void setPath(String newPath){
        filePath = newPath;
    }
    public String getCaption(){
        return caption;
    }
    public void setCaption(String newCaption){
        caption = newCaption;
    }
    public LocalDate getDate(){
        return date;
    }
    public void setDate(LocalDate newDate){
        date = newDate;
    }
    public void addTag(Tag tag){
        tags.add(tag);
    }
    public void removeTag(Tag tag){
        tags.remove(tag);
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || (!(o instanceof Tag))) return false;
        Photo other = (Photo) o;
        return filePath.equals(other.getPath());
    }
}
