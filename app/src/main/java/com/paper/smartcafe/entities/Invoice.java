package com.paper.smartcafe.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Chudofom on 17.08.17.
 */
@Entity(tableName = "invoices")
public class Invoice implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String supplierName;
    public Double sum;
    public Date date;

    public Invoice() {
        sum = 0.0;
    }

    @Ignore
    public ArrayList<Ingredient> ingredients;
    @Ignore
    public HashMap<Long, Double> ingredientsCount = new HashMap<>();

    @Ignore
    public Invoice(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.sum = 0.0;
    }

    @Ignore
    public void setIngredientCount(long ingredientId, double ingredientCount) {
        ingredientsCount.put(ingredientId, ingredientCount);
    }

    @Ignore
    public double getIngredientCount(long ingredientId) {
        return ingredientsCount.get(ingredientId);
    }
}
