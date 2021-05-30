package com.example.saisankar.servicetest.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = UserModel.class,version = 1)
public abstract class UserDataBase extends RoomDatabase {

    public abstract UserDao userDao();
}
