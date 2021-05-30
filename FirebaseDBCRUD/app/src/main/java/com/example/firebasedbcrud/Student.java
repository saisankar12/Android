package com.example.firebasedbcrud;

public class Student {

    String name,roll, number;

    public Student(String name, String roll, String number) {
        this.name = name;
        this.roll = roll;
        this.number = number;
    }

    public Student() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
