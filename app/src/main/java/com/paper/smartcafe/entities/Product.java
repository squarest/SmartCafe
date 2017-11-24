package com.paper.smartcafe.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Chudofom on 03.10.16.
 */
@Entity(tableName = "products")
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long categoryId;
    public String name;
    public double cost;
    public String units;
    public String imagePath;

    public Product() {
    }

    @Ignore
    public ArrayList<Ingredient> ingredients;

    @Ignore
    public Product(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Ignore
    public Product(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    @Ignore
    private HashMap<Long, Double> ingredientCount = new HashMap<>();

    @Ignore
    public void addIngredient(Ingredient ingredient) {
        if (!ingredients.contains(ingredient)) {
            ingredients.add(ingredient);
            ingredientCount.put(ingredient.id, 1.0);
        } else {
            ingredientCount.put(ingredient.id, ingredientCount.get(ingredient.id) + 1.0);
        }
    }

    @Ignore
    public void setIngredientCount(long ingredientId, Double quantity) {
        ingredientCount.put(ingredientId, quantity);
    }

    @Ignore
    public Double getIngredientCount(long ingredientId) {
        return ingredientCount.get(ingredientId);
    }

    @Ignore
    public HashMap<Long, Double> getIngredientsCount() {
        return ingredientCount;
    }

    @Ignore
    public void setIngredientsCount(HashMap<Long, Double> ingredientCount) {
        this.ingredientCount = ingredientCount;
    }
}
