package com.example.saisankar.firebaseimagedb;

public class MyModel {
    private String name;
    private String imageURL;
    private String key;
    private String description;
    private int position;


    public MyModel() {

    }

    public MyModel(int position) {
        this.position = position;
    }

    public MyModel(String name, String imageURL, String description) {

        if (name.trim().equals("")){
            name="No Name";
        }

        this.name = name;
        this.imageURL = imageURL;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
