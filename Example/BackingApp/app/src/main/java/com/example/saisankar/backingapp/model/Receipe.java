package com.example.saisankar.backingapp.model;

public class Receipe {

    String servings;
    String name;
    String ingredients;
    String steps;

    public Receipe(String servings, String name, String ingredients, String steps) {
        this.servings = servings;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}

