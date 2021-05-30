package com.example.saisankar.cinematics.model;



import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sai Sankar on 17-05-2018.
 */

public class ReviewsInfo {
    private String auther;
    private String content;
    private String id;


    public ReviewsInfo(String auther, String content, String id) {
        this.auther = auther;
        this.content = content;
        this.id = id;

    }

    public ReviewsInfo(JSONObject jsonObject1) throws JSONException {

        this.auther = jsonObject1.getString("author");
        this.content = jsonObject1.getString("content");
        this.id = jsonObject1.getString("id");


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




}
