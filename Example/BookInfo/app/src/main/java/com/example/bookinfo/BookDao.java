package com.example.bookinfo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.view.Display;

import java.util.List;

@Dao
public interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Model model);

    @Delete
    public void delete(Model model);

    @Query("select * from Book")
    LiveData<List<Model>> getBooks();

    @Query("select id from Book where id==:bid")
    String checkBook(String bid);
}
