package com.example.roomlivedata;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class StudentRepository {

    public StudnetDAO studnetDAO;
    public LiveData<List<StudentModel>> getStudnetData;

    public StudentRepository(Application application) {
        StudentDataBase dataBase=StudentDataBase.createDatabase(application);
        studnetDAO=dataBase.studnetDAO();
        getStudnetData=studnetDAO.readData();
    }

    public void addData(StudentModel model){
        new InsertTask().execute(model);
    }

    public LiveData<List<StudentModel>> getData(){
            return getStudnetData;
    }

    class InsertTask extends AsyncTask<StudentModel,Void,Void>{
        @Override
        protected Void doInBackground(StudentModel... studentModels) {
            studnetDAO.insert(studentModels[0]);
            return null;
        }
    }




}
