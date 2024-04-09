package model;

import java.time.LocalDate;

public class AlbumDisplay {
    private String albumName;
    private int numPhotos;
    private String dateRange;
    private Album album;
    /**
     * Constructor for albumdisplay, used in the AlbumDisplayController.
     * @param album Takes in the album that is going to be displayed
     * @param albumName Takes in the album name
     * @param numPhotos Takes in the number of photos that are going to be displayed
     * @param dateRange Takes in the date range of the photos in the album
     */
    public AlbumDisplay(Album album, String albumName, int numPhotos, String dateRange){
        this.album = album;
        this.albumName = albumName;
        this.numPhotos = numPhotos;
        this.dateRange = dateRange;
    }
    /**
     * Get method for album
     * @return Returns the album
     */
    public Album getAlbum(){
        return album;
    }
    /**
     * get method for getNumphotos
     * @return Returns the number of photos
     */
    public int getNumPhotos(){
        return numPhotos;
    }
    /**
     * getter method for album name
     * @return Returns the album name
     */
    public String getAlbumName(){
        return albumName;
    }
    /**
     * getter method for dataRange
     * @return Returns the dateRange
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
