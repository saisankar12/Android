package com.example.roomdemo.components;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface StudentDAO {

    @Insert
    public void addData(StudentModel studentModel);

    @Query("select * from StudentData")
    LiveData<List<StudentModel>> getData();

    @Delete
    public void deleteData(StudentModel studentModel);

    @Update
    public void updateData(StudentModel studentModel);

    @Query("delete from StudentData")
    public void deleteAllData();
}
