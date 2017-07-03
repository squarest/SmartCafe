package com.example.clevercafe.model;

import java.util.ArrayList;

/**
 * Created by Chudofom on 03.10.16.
 */
public class Product {
    public String name;
    public double cost;
    public double quantity;
    public String units;
    public ArrayList<Ingredient> ingredients;

    public Product() {
    }

    public Product(String name, double cost, double quantity) {
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
    }
}
