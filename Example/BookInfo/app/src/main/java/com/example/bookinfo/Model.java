package com.example.bookinfo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName ="Book")
public class Model {

    @PrimaryKey
    @NonNull
     String id;
    String title;
    String author;
    String published_date;
    String thumbnail;
    String preview;

    public Model(String id, String title, String author, String published_date, String thumbnail, String preview
                  ) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.published_date = published_date;
        this.thumbnail = thumbnail;
        this.preview = preview;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

}
