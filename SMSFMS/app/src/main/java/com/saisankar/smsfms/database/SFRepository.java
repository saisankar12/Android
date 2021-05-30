package com.saisankar.smsfms.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.saisankar.smsfms.database.facultydb.Faculty;
import com.saisankar.smsfms.database.studentdb.Student;

import java.util.List;

public class SFRepository {


    LiveData<List<Faculty>> readFacultyData;
    LiveData<List<Student>> readstudentData;
    SFDataBase dataBase;
    public SFRepository(Application application) {
        dataBase = SFDataBase.getDataBase(application);
        readFacultyData = dataBase.myDao().readFacultyData();
        readstudentData = dataBase.myDao().readStudentData();
    }

    public void insertFaculty(final Faculty faculty){
        SFDataBase.databaseWriteExecuter.execute(()->dataBase.myDao().insertFaculty(faculty));
    }

    public void insertStudent(final Student student){
        SFDataBase.databaseWriteExecuter.execute(()->dataBase.myDao().insertStudent(student));
    }
    public void findStudent(final String student){
        SFDataBase.databaseWriteExecuter.execute(()->dataBase.myDao().findWord(student));
    }

    LiveData<List<Faculty>> readFaculty(){
        return readFacultyData;
    }

    LiveData<List<Student>> readStudent(){
        return readstudentData;
    }

}
