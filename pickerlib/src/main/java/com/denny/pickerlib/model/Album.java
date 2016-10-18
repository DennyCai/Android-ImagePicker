package com.denny.pickerlib.model;

/**
 * Created by Cai on 2016/10/12.
 */
public class Album {
    private String firstPicture;
    private String albumPath;
    private String albumName;
    private int number = 1;

    public int getPictureNumber() {
        return number;
    }

    public void setPictureNumber(int number) {
        this.number = number;
    }

    public void incrementPicture(){
        number++;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getAlbumPath() {
        return albumPath;
    }

    public void setAlbumPath(String albumPath) {
        this.albumPath = albumPath;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public boolean equals(Object obj) {
        return obj!=null&&obj instanceof Album&&((Album)obj).getAlbumPath().equals(albumPath)&&((Album)obj).getAlbumName().equals(albumName);
    }
}
