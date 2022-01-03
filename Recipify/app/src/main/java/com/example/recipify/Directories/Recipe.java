package com.example.recipify.Directories;

import android.widget.Spinner;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class Recipe {
    private String id;
    private String title;
    private String thumbnail;
    private String summary;
    private String step;
    private int amountOfDishes;
    private int readyInMins;
    private String healthScore;
    private boolean isVegan;
    private boolean isGlutenFree;
    private ArrayList<Ingredient> ingredients;

    public Recipe(String id, String title, String thumbnail, String summary, String step, int amountOfDishes, int readyInMins,
                  String healthScore, boolean isVegan, boolean isGlutenFree) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.summary = summary;
        this.step = step;
        this.amountOfDishes = amountOfDishes;
        this.readyInMins = readyInMins;
        this.healthScore = healthScore;
        this.isVegan = isVegan;
        this.isGlutenFree = isGlutenFree;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getSummary() {
        return summary;
    }

    public String getStep() {
        return step;
    }

    public int getAmountOfDishes() {
        return amountOfDishes;
    }

    public int getReadyInMins() {
        return readyInMins;
    }

    public String getHealthScore() { return healthScore; }

    public boolean isVegan() { return isVegan; };

    public boolean isGlutenFree() { return isGlutenFree; }

    @NonNull
    @Override
    public String toString() {
        return getTitle();
    }
}
