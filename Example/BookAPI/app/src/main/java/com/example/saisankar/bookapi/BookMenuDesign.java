package com.example.saisankar.bookapi;

public class BookMenuDesign {

    String titles,authers,descriptions,country,thumbnail;

    public BookMenuDesign(String titles, String authers, String descriptions, String country, String thumbnail) {
        this.titles = titles;
        this.authers = authers;
        this.descriptions = descriptions;
        this.country = country;
        this.thumbnail = thumbnail;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getAuthers() {
        return authers;
    }

    public void setAuthers(String authers) {
        this.authers = authers;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
