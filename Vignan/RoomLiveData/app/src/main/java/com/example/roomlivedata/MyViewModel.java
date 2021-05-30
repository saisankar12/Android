package com.example.roomlivedata;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    public StudentRepository repository;
    public LiveData<List<StudentModel>> readStudnetData;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository=new StudentRepository(application);
        readStudnetData=repository.getData();
    }

    public void insertData(StudentModel model){
        repository.addData(model);
    }

    public LiveData<List<StudentModel>> readAllStudentData(){
        return readStudnetData;
    }

}
