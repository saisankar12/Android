package com.blogspot.pavankreddytadi.storyboard.room_database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {StoryBoard.class},version = 1,exportSchema = false)
public abstract class StoryBoardDatabase extends RoomDatabase {

    abstract StoryBoardDAO storyBoardDAO();

    private static StoryBoardDatabase INSTANCE;

    static StoryBoardDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (StoryBoardDatabase.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder
                            (context,StoryBoardDatabase.class,"story_board_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
