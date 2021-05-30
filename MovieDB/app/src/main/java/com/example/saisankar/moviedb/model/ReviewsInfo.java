package com.example.saisankar.moviedb.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sai Sankar on 17-05-2018.
 */

public class ReviewsInfo {
    String auther;
    String content;
    String id;
    String url;

    public ReviewsInfo(String auther, String content, String id, String url) {
        this.auther = auther;
        this.content = content;
        this.id = id;
        this.url = url;
    }

    public ReviewsInfo(JSONObject jsonObject1) throws JSONException {

                this.auther=jsonObject1.getString("author");
                this.content=jsonObject1.getString("content");
                this.id=jsonObject1.getString("id");
                this.url=jsonObject1.getString("url");
        Log.i("auther",getAuther().toString());

    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
