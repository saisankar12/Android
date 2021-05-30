package com.blogspot.pavankreddytadi.storyboard.room_database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "story_board")
public class StoryBoard {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "stories")
    String story;
    @ColumnInfo(name = "auther")
    String auther;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StoryBoard(String story, String auther) {
        this.story = story;
        this.auther = auther;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }
}
