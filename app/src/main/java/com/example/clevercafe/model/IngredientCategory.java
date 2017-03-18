package com.example.clevercafe.model;

import java.util.ArrayList;

/**
 * Created by Chudofom on 15.03.17.
 */

public class IngredientCategory {
    public String name;
    public ArrayList<Ingredient> ingredients;

    public IngredientCategory(String name, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
}
