package com.example.hp.capstoneproject.model;

public class Entertainment
{
    private String et_source;
    private String et_author;
    private String et_title;
    private String et_description;
    private String et_url;
    private String et_urlToImage;
    private String et_publishedAt;

    public Entertainment(String et_author, String et_title, String et_description,
                         String et_url, String et_urlToImage, String et_publishedAt) {
        this.et_author = et_author;
        this.et_title = et_title;
        this.et_description = et_description;
        this.et_url = et_url;
        this.et_urlToImage = et_urlToImage;
        this.et_publishedAt = et_publishedAt;
    }

    public String getEt_author() {
        return et_author;
    }

    public void setEt_author(String et_author) {
        this.et_author = et_author;
    }

    public String getEt_title() {
        return et_title;
    }

    public void setEt_title(String et_title) {
        this.et_title = et_title;
    }

    public String getEt_description() {
        return et_description;
    }

    public void setEt_description(String et_description) {
        this.et_description = et_description;
    }

    public String getEt_url() {
        return et_url;
    }

    public void setEt_url(String et_url) {
        this.et_url = et_url;
    }

    public String getEt_urlToImage() {
        return et_urlToImage;
    }

    public void setEt_urlToImage(String et_urlToImage) {
        this.et_urlToImage = et_urlToImage;
    }

    public String getEt_publishedAt() {
        return et_publishedAt;
    }

    public void setEt_publishedAt(String et_publishedAt) {
        this.et_publishedAt = et_publishedAt;
    }
}
