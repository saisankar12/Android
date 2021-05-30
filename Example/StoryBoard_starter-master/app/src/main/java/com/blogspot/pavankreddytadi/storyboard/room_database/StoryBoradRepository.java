package com.blogspot.pavankreddytadi.storyboard.room_database;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class StoryBoradRepository {

    private StoryBoardDAO storyBoardDAO;
    private LiveData<List<StoryBoard>> mListStoryBoard;

    public StoryBoradRepository(Context context) {
        StoryBoardDatabase storyBoardDatabase = StoryBoardDatabase.getDatabase(context);
        storyBoardDAO=storyBoardDatabase.storyBoardDAO();
        mListStoryBoard=storyBoardDAO.getAllStories();
    }

    LiveData<List<StoryBoard>> getAllStories(){
        return mListStoryBoard;
    }

    public void insert(StoryBoard storyBoard){
        new InsertAsynTask().execute(storyBoard);
    }

    private class InsertAsynTask extends AsyncTask<StoryBoard,Void,Void>{

        @Override
        protected Void doInBackground(StoryBoard... storyBoards) {

            storyBoardDAO.insert(storyBoards[0]);
            return null;
        }
    }
}
