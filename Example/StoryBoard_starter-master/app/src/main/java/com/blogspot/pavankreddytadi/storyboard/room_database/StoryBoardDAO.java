package com.blogspot.pavankreddytadi.storyboard.room_database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;

import java.util.List;

@Dao
public interface StoryBoardDAO {
    @Insert
    void insert(StoryBoard storyBoard);


    @Query("Select * from story_board")
    LiveData<List<StoryBoard>> getAllStories();
}
