package com.example.saisankar.servicetest.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertuserdata(UserModel userModel);

    @Query("select * from userinfo")
    public List<UserModel> getUserData();

    @Update
    public void updateuserdata(UserModel userModel);

    @Delete
    public void deleteuserdata(UserModel userModel);
}
