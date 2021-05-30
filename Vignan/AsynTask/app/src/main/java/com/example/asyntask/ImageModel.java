package com.example.asyntask;

public class ImageModel {

    String imagedata;
    String imagelikes;

    public ImageModel(String imagedata, String imagelikes) {
        this.imagedata = imagedata;
        this.imagelikes = imagelikes;
    }

    public String getImagedata() {
        return imagedata;
    }

    public void setImagedata(String imagedata) {
        this.imagedata = imagedata;
    }

    public String getImagelikes() {
        return imagelikes;
    }

    public void setImagelikes(String imagelikes) {
        this.imagelikes = imagelikes;
    }
}
