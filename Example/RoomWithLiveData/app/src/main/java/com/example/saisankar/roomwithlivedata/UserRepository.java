package com.example.saisankar.roomwithlivedata;


import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class UserRepository {

    public static NoteDao noteDao;

    private LiveData<List<User>> getliveData;

    public UserRepository(Context context){
        UserDataBase userDataBase=UserDataBase.getInstance(context);
        noteDao=userDataBase.noteDao();
        getliveData=noteDao.getallUserData();
    }

    public void insert(User user){
        new InsertTask().execute(user);
    }

    LiveData<List<User>> getliveData(){
        return getliveData;
    }
    public void delete(User user){
       new DeleteTask().execute(user);
    }
    public void update(User user){
        new UpdateeTask().execute(user);
    }

    public void deleteAlldata(User user){
        new DeleteallData(noteDao).execute();
    }


    public class DeleteallData extends AsyncTask<Void,Void,Void>{

        public DeleteallData(NoteDao dao) {
            noteDao=dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }

    public class InsertTask extends AsyncTask<User,Void,Void>{

        @Override
        protected Void doInBackground(User... users) {
            noteDao.insertData(users[0]);
            return null;
        }
    }
    public class UpdateeTask extends AsyncTask<User,Void,Void>{

        @Override
        protected Void doInBackground(User... users) {
            noteDao.updateData(users[0]);
            return null;
        }
    }
    public class DeleteTask extends AsyncTask<User,Void,Void>{

        @Override
        protected Void doInBackground(User... users) {
            noteDao.deleteData(users[0]);
            return null;
        }
    }
}
