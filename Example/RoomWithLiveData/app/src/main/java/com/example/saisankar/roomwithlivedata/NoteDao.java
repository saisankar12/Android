package com.example.saisankar.roomwithlivedata;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    public void insertData(User user);


    @Query("select * from userinfo order by roll_number")
    public LiveData<List<User>> getallUserData();

    @Delete
    public void deleteData(User user);

    @Update
    public void updateData(User user);

    @Query("delete from userinfo")
    public void deleteAll();
}
