package com.example.roomlivedata;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface StudnetDAO {

    @Insert
    public void insert(StudentModel model);


    @Query("select * from Students")
    public LiveData<List<StudentModel>> readData();

}
