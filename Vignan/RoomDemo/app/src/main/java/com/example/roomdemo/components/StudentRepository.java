package com.example.roomdemo.components;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class StudentRepository {

    public LiveData<List<StudentModel>> getstudentData;
    public StudentDAO studentDAO;

    public StudentRepository(Application application) {
    StudentDatabase database=StudentDatabase.getDataBase(application);
    studentDAO=database.studentDAO();
    getstudentData=studentDAO.getData();
    }


    public void insertreposotory(StudentModel studentModel){
        new InsertTask().execute(studentModel);
    }
    public void updatereposotory(StudentModel studentModel){
        new UpdateTask().execute(studentModel);
    }
    public void deletereposotory(StudentModel studentModel){
        new DeleteTask().execute(studentModel);
    }
    public void deleteAllreposotory(){
        new DeleteAllTask().execute();
    }

    public LiveData<List<StudentModel>> getReposotory(){

        return getstudentData;
    }

    class InsertTask extends AsyncTask<StudentModel,Void,Void>{

        @Override
        protected Void doInBackground(StudentModel... studentModels) {
            studentDAO.addData(studentModels[0]);
            return null;
        }
    }

    class UpdateTask extends AsyncTask<StudentModel,Void,Void>{

        @Override
        protected Void doInBackground(StudentModel... studentModels) {
            studentDAO.updateData(studentModels[0]);
            return null;
        }
    }
    class DeleteTask extends AsyncTask<StudentModel,Void,Void>{

        @Override
        protected Void doInBackground(StudentModel... studentModels) {
            studentDAO.deleteData(studentModels[0]);
            return null;
        }
    }
    class DeleteAllTask extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {
            studentDAO.deleteAllData();
            return null;
        }
    }
}
