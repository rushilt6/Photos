package model;

import java.time.LocalDate;

public class AlbumDisplay {
    private String albumName;
    private int numPhotos;
    private String dateRange;
    public AlbumDisplay(String albumName, int numPhotos, String dateRange){
        this.albumName = albumName;
        this.numPhotos = numPhotos;
        this.dateRange = dateRange;
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
