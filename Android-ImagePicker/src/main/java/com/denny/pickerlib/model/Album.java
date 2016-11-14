package com.denny.pickerlib.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cai on 2016/10/12.
 */
public class Album {
    private List<String> pictures;
    private Bitmap firstPictureBmp;
    private String albumPath;
    private String albumName;

    public Album() {
        pictures = new ArrayList<>();
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public void addPicture(String path) {
        pictures.add(path);
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
        return obj != null && obj instanceof Album && ((Album) obj).getAlbumPath().equals(albumPath) && ((Album) obj).getAlbumName().equals(albumName);
    }

    public Bitmap getFirstPictureBmp() {
        return firstPictureBmp;
    }

    public void setFirstPictureBmp(Bitmap firstPictureBmp) {
        this.firstPictureBmp = firstPictureBmp;
    }

    public int getPicturesNumber() {
        return pictures.size();
    }
}
