package com.example.saisankar.moviedb.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sai Sankar on 16-05-2018.
 */

public class TrailersInfo {

    private String id;
    private String key;
    private String name;
    private String site;
    private String type;

    public TrailersInfo(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public TrailersInfo(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getString("id");
        this.key = jsonObject.getString("key");
        this.name = jsonObject.getString("name");
        this.site = jsonObject.getString("site");
        this.type = jsonObject.getString("type");
    }

    public TrailersInfo(String id, String key, String name, String site, String type) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.site = site;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
