package com.paper.smartcafe.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

/**
 * Created by Chudofom on 15.03.17.
 */
@Entity(tableName = "ingredientCategories")
public class IngredientCategory {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    @Ignore
    public ArrayList<Ingredient> ingredients;

    public IngredientCategory() {
    }

    public IngredientCategory(String name, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
}
