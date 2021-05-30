package com.example.saisankar.bookapi;

public class BookModel {

    String bookid,title,thumbnail,description;

    public BookModel(String bookid, String title,String description,String thumbnail) {
        this.bookid = bookid;
        this.title = title;
        this.description=description;
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
