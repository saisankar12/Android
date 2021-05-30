package com.example.roomdemo.components;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {

    StudentRepository repository;
    LiveData<List<StudentModel>> getstudentData;
    public StudentViewModel(@NonNull Application application) {
        super(application);
        repository=new StudentRepository(application);
        getstudentData=repository.getReposotory();
    }

    public LiveData<List<StudentModel>> getAllStudentData(){
        return getstudentData;
    }
    public void insertdata(StudentModel studentModel){
        repository.insertreposotory(studentModel);
    }

    public void updatedata(StudentModel studentModel){
        repository.updatereposotory(studentModel);
    }

    public void deletedata(StudentModel studentModel){
        repository.deletereposotory(studentModel);
    }

    public void deletealldata(){
        repository.deleteAllreposotory();
    }


}
