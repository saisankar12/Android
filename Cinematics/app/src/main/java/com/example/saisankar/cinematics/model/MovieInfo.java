package com.example.saisankar.cinematics.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Sai Sankar on 05-05-2018.
 */

public class MovieInfo implements Parcelable {

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };
    private int mid;
    private int mvote_count;
    private String mvideo;
    private String mvote_average;
    private String mtitle;
    private String mpopularity;
    private String mposter_path;
    private String moriginal_language;
    private String moriginal_title;
    private String mbackdrop_path;
    private String madult;
    private String moverview;
    private String mrelease_date;

    public MovieInfo() {
    }

    public MovieInfo(int id, int vote_count, String video,
                     String vote_average, String title,
                     String popularity, String poster_path,
                     String original_language, String original_title,
                     String backdrop_path, String adult,
                     String overview, String release_date) {

        mid = id;
        mvote_count = vote_count;
        mvideo = video;
        mvote_average = vote_average;
        mtitle = title;
        mpopularity = popularity;
        mposter_path = poster_path;
        moriginal_language = original_language;
        moriginal_title = original_title;
        mbackdrop_path = backdrop_path;
        madult = adult;
        moverview = overview;
        mrelease_date = release_date;

    }

    public MovieInfo(Parcel in) {
        mid = in.readInt();
        mvote_count = in.readInt();
        mvideo = in.readString();
        mvote_average = in.readString();
        mtitle = in.readString();
        mpopularity = in.readString();
        mposter_path = in.readString();
        moriginal_language = in.readString();
        moriginal_title = in.readString();
        mbackdrop_path = in.readString();
        madult = in.readString();
        moverview = in.readString();
        mrelease_date = in.readString();
    }

    public MovieInfo(Cursor cursor) {

        this.mid = cursor.getInt(1);
        this.mvote_count = 0;
        this.mvideo = null;
        this.mvote_average = cursor.getString(2);
        this.mtitle = cursor.getString(3);
        this.mpopularity = null;
        this.mposter_path = cursor.getString(4);

        this.moriginal_language = null;
        this.moriginal_title = cursor.getString(5);
        this.mbackdrop_path = cursor.getString(6);
        this.madult = null;
        this.moverview = cursor.getString(7);
        this.mrelease_date = cursor.getString(8);

    }

    public int getMid() {
        return mid;
    }








    public String getMvote_average() {
        return mvote_average;
    }



    public String getMtitle() {
        return mtitle;
    }







    public String getMposter_path() {
        return mposter_path;
    }







    public String getMoriginal_title() {
        return moriginal_title;
    }



    public String getMbackdrop_path() {
        return mbackdrop_path;
    }

    public String getMoverview() {
        return moverview;
    }



    public String getMrelease_date() {
        return mrelease_date;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mid);
        dest.writeInt(mvote_count);
        dest.writeString(mvideo);
        dest.writeString(mvote_average);
        dest.writeString(mtitle);
        dest.writeString(mpopularity);
        dest.writeString(mposter_path);
        dest.writeString(moriginal_language);
        dest.writeString(moriginal_title);
        dest.writeString(mbackdrop_path);
        dest.writeString(madult);
        dest.writeString(moverview);
        dest.writeString(mrelease_date);
    }

}
