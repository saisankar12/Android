package com.example.user.master.Models;

public class MyIngredientModel {

    String quantity,measure,ingName;

    public MyIngredientModel(String quantity, String measure, String ingName) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingName = ingName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngName() {
        return ingName;
    }

    public void setIngName(String ingName) {
        this.ingName = ingName;
    }
}
