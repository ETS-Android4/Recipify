package com.example.recipify.Directories;

import org.jetbrains.annotations.NotNull;

public class Ingredient {

    private String nom;
    private String image;

    public Ingredient(String nom, String image){
        this.nom = nom;
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public String getImage() {
        return image;
    }

    @NotNull
    @Override
    public String toString() {
        return getNom();
    }
}
