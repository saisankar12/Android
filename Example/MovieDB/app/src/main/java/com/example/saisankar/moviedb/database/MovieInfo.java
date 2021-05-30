package com.example.saisankar.moviedb.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Sai Sankar on 05-05-2018.
 */

@Entity(tableName = "Movie_Data")
public class MovieInfo {

    @ColumnInfo(name = "movie_id")
    private int mid;
    @ColumnInfo(name = "movie_vote_count")
    private int mvote_count;
    @ColumnInfo(name = "movie_video")
    private String mvideo;
    @ColumnInfo(name = "movie_average")
    private String mvote_average;
    @ColumnInfo(name = "movie_title")
    private String mtitle;
    @ColumnInfo(name = "movie_papularity")
    private String mpopularity;
    @ColumnInfo(name = "movie_poster")
    @PrimaryKey @NonNull
    private String mposter_path;
    @ColumnInfo(name = "movie_language")
    private String moriginal_language;
    @ColumnInfo(name = "movie_original_title")
    private String moriginal_title;
    @ColumnInfo(name = "movie_backdrop")
    private String mbackdrop_path;
    @ColumnInfo(name = "movie_adult")
    private String madult;
    @ColumnInfo(name = "movie_overview")
    private String moverview;
    @ColumnInfo(name = "movie_date")
    private String mrelease_date;


    public MovieInfo(int id, int vote_count, String video,
                     String vote_average, String title,
                     String popularity, String poster_path,
                     String original_language, String original_title,
                     String backdrop_path, String adult,
                     String overview, String release_date){

        mid=id;
        mvote_count=vote_count;
        mvideo=video;
        mvote_average=vote_average;
        mtitle=title;
        mpopularity=popularity;
        mposter_path=poster_path;
        moriginal_language=original_language;
        moriginal_title=original_title;
        mbackdrop_path=backdrop_path;
        madult=adult;
        moverview=overview;
        mrelease_date=release_date;

    }

    public MovieInfo() {

    }


    @NonNull
    public int getMid() {
        return mid;
    }

    public void setMid(@NonNull int mid) {
        this.mid = mid;
    }

    public int getMvote_count() {
        return mvote_count;
    }

    public void setMvote_count(int mvote_count) {
        this.mvote_count = mvote_count;
    }

    public String getMvideo() {
        return mvideo;
    }

    public void setMvideo(String mvideo) {
        this.mvideo = mvideo;
    }

    public String getMvote_average() {
        return mvote_average;
    }

    public void setMvote_average(String mvote_average) {
        this.mvote_average = mvote_average;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getMpopularity() {
        return mpopularity;
    }

    public void setMpopularity(String mpopularity) {
        this.mpopularity = mpopularity;
    }

    public String getMposter_path() {
        return mposter_path;
    }

    public void setMposter_path(String mposter_path) {
        this.mposter_path = mposter_path;
    }

    public String getMoriginal_language() {
        return moriginal_language;
    }

    public void setMoriginal_language(String moriginal_language) {
        this.moriginal_language = moriginal_language;
    }

    public String getMoriginal_title() {
        return moriginal_title;
    }

    public void setMoriginal_title(String moriginal_title) {
        this.moriginal_title = moriginal_title;
    }

    public String getMbackdrop_path() {
        return mbackdrop_path;
    }

    public void setMbackdrop_path(String mbackdrop_path) {
        this.mbackdrop_path = mbackdrop_path;
    }

    public String getMadult() {
        return madult;
    }

    public void setMadult(String madult) {
        this.madult = madult;
    }

    public String getMoverview() {
        return moverview;
    }

    public void setMoverview(String moverview) {
        this.moverview = moverview;
    }

    public String getMrelease_date() {
        return mrelease_date;
    }

    public void setMrelease_date(String mrelease_date) {
        this.mrelease_date = mrelease_date;
    }
}
