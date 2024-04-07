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
        if(tags.contains(tag))
            tags.remove(tag);
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || (!(o instanceof Tag))) return false;
        Photo other = (Photo) o;
        return filePath.equals(other.getPath());
    }
    public String toString() {
        return "Photo{" + "filePath='" + filePath + '\'' + ", caption='" + caption + '\'' + ", dateOfPhoto=" + date + ", tags=" + tags + '}';
    }

    public class Tag implements Serializable{
    private String tagName;
    private String tagValue;

    public Tag(String tagName, String tagValue){
        this.tagName = tagName;
        this.tagValue = tagValue;
    }
    public String getName(){
        return tagName;
    }
    public String getValue(){
        return tagValue;
    }
    public void setName(String name){
        tagName = name;
    }
    public void setValue(String value){
        tagValue = value;
    }

    public String toString(){
        return "Tag{ " +"tagName='" + tagName + '\'' +", tagValue='" + tagValue + '\'' + '}';
    }

    public boolean equals(Object o){
        if (this == o) return true;
        if(o == null || (!(o instanceof Tag))) return false;
        Tag other = (Tag) o;
        return  tagName.equals(other.getName()) && tagValue.equals(other.getValue());
    }

    public int hashCode(){
        return Objects.hash(tagName, tagValue);
    }
}
}
