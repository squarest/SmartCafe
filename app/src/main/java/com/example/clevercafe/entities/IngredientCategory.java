package com.example.clevercafe.entities;

import java.util.ArrayList;

/**
 * Created by Chudofom on 15.03.17.
 */

public class IngredientCategory {
    public int id;
    public String name;
    public ArrayList<Ingredient> ingredients;

    public IngredientCategory() {
    }

    public IngredientCategory(String name, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
}
