package com.blogspot.pavankreddytadi.storyboard.room_database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class StoryBoardViewModel extends AndroidViewModel {
    private StoryBoradRepository storyBoradRepository;
    private LiveData<List<StoryBoard>> mListAllStories;

    public StoryBoardViewModel(@NonNull Application application) {
        super(application);
        storyBoradRepository = new StoryBoradRepository(application);
        mListAllStories = storyBoradRepository.getAllStories();
    }

    public LiveData<List<StoryBoard>> getmListAllStories(){
        return mListAllStories;
    }

    public void insert(StoryBoard storyBoard){
        storyBoradRepository.insert(storyBoard);
    }
}
