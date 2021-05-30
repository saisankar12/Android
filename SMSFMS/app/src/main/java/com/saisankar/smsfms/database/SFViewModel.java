package com.saisankar.smsfms.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.saisankar.smsfms.database.facultydb.Faculty;
import com.saisankar.smsfms.database.studentdb.Student;

import java.util.List;

public class SFViewModel extends AndroidViewModel {
    LiveData<List<Faculty>> readFaculty;
    LiveData<List<Student>> readStudent;
    SFRepository repository;
    public SFViewModel(@NonNull Application application) {
        super(application);

        repository =new SFRepository(application);
        readFaculty =repository.readFaculty();
        readStudent =repository.readStudent();
    }

    public String findStudent(final String student){
        repository.findStudent(student);
        return student;
    }

    public void inserStudentData(Student student){
        repository.insertStudent(student);
    }
    public void inserFacultyData(Faculty faculty){
        repository.insertFaculty(faculty);
    }

    public LiveData<List<Student>> readStudentData(){
        return readStudent;
    }

    public LiveData<List<Faculty>> readFacultyData(){
        return readFaculty;
    }

}
