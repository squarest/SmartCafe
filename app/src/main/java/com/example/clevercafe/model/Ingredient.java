package com.example.clevercafe.model;

import java.io.Serializable;

/**
 * Created by Chudofom on 15.03.17.
 */

public class Ingredient implements Serializable {
    public int id;
    public int categoryId;
    public String name;
    public double quantity;
    public String units;

    public Ingredient(String name, double quantity, String units) {
        this.name = name;
        this.quantity = quantity;
        this.units = units;
    }

    public Ingredient() {
    }
}
