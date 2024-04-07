package model;

import java.time.LocalDate;

public class AlbumDisplay {
    private String albumName;
    private int numPhotos;
    private String dateRange;
    private Album album;
    public AlbumDisplay(Album album, String albumName, int numPhotos, String dateRange){
        this.album = album;
        this.albumName = albumName;
        this.numPhotos = numPhotos;
        this.dateRange = dateRange;
    }
    public Album getAlbum(){
        return album;
    }
    public int getNumPhotos(){
        return numPhotos;
    }
    public String getAlbumName(){
        return albumName;
    }
    public String getDateRange(){
        return dateRange;
    }
    public String toString(){
        return "Name: "+albumName+", Number of Pictures: "+numPhotos+", Dates: "+dateRange;
    }
}
