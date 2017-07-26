package com.example.clevercafe.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

/**
 * Created by Chudofom on 03.10.16.
 */
@Entity(tableName = "products")
public class Product {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long categoryId;
    public String name;
    public double cost;
    public double quantity;
    public String units;
    @Ignore
    public ArrayList<Ingredient> ingredients;

    public Product() {
    }

    @Ignore
    public Product(String name, double cost, double quantity) {
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
    }
}
