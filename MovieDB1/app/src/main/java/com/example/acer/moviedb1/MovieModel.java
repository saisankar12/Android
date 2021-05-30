package com.example.acer.moviedb1;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class MovieModel {
    @PrimaryKey@NonNull
    String id1;
    String img,tit,ave,over,reale;

    public MovieModel(String img, String tit, String id1, String ave, String over,String releaseDate) {
        this.img = img;
        this.tit = tit;
        this.id1 = id1;
        this.ave = ave;
        this.over = over;
        this.reale=releaseDate;
    }

    public MovieModel() {

    }

    public String getReale() {
        return reale;
    }

    public void setReale(String reale) {
        this.reale = reale;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTit() {
        return tit;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getAve() {
        return ave;
    }

    public void setAve(String ave) {
        this.ave = ave;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }
}
