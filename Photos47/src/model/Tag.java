package model;
import java.io.Serializable;
import java.util.*;

public class Tag implements Serializable{
    private String tagName;
    private String tagValue;
    /**
     * Constructor for tag
     * @param tagName Takes in the tag name
     * @param tagValue Takes in the tag value
     */
    public Tag(String tagName, String tagValue){
        this.tagName = tagName;
        this.tagValue = tagValue;
    }
    /**
     * Gets the name of the tag(Person, Location, etc.)
     * @return tag name
     */
    public String getName(){
        return tagName;
    }
    /*
     * Gets the value of the tag(Susan, Princeton, etc.)
     * Returns the tag value
     */
    public String getValue(){
        return tagValue;
    }
    /**
     * Sets the name of the tag as something else
     * @param name takes in the new name 
     */
    public void setName(String name){
        tagName = name;
    }
    /**
     * Sets the value of the tag as something else
     * @param value takes in the new value
     */
    public void setValue(String value){
        tagValue = value;
    }

    public String toString(){
        return "Name: " + tagName +", Value: " + tagValue;
    }
    /**
     * Equals method that compares to see if a tag is equal to another
     */
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