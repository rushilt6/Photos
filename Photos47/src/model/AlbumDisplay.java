package model;

import java.time.LocalDate;

public class AlbumDisplay {
    private String albumName;
    private int numPhotos;
    private LocalDate earliestDate;
    private LocalDate latestDate;
    private Album album;
    public AlbumDisplay(Album album){
        this.album = album;
    }
    public int getNumPhotos(){
        return numPhotos;
    }
    public String getAlbumName(){
        return albumName;
    }
    public LocalDate getEarliest(){
        return earliestDate;
    }
    public LocalDate getLatest(){
        return latestDate;
    }
    public void setAlbumName(String newName){
        albumName = newName;
    }
}
