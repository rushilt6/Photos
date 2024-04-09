package model;

import java.time.LocalDate;

public class AlbumDisplay {
    private String albumName;
    private int numPhotos;
    private String dateRange;
    private Album album;
    /**
     * Constructor for albumdisplay, used in the AlbumDisplayController.
     * @param album
     * @param albumName
     * @param numPhotos
     * @param dateRange
     */
    public AlbumDisplay(Album album, String albumName, int numPhotos, String dateRange){
        this.album = album;
        this.albumName = albumName;
        this.numPhotos = numPhotos;
        this.dateRange = dateRange;
    }
    /**
     * Get method for album
     * @return
     */
    public Album getAlbum(){
        return album;
    }
    /**
     * get method for getNumphotos
     * @return
     */
    public int getNumPhotos(){
        return numPhotos;
    }
    /**
     * getter method for album name
     * @return
     */
    public String getAlbumName(){
        return albumName;
    }
    /**
     * getter method for dataRange
     * @return
     */
    public String getDateRange(){
        return dateRange;
    }
    /**
     * toString method to print the attributes
     */
    public String toString(){
        return "Name: "+albumName+", Number of Pictures: "+numPhotos+", Dates: "+dateRange;
    }
}
