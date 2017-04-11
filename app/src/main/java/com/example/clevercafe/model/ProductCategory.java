package com.example.clevercafe.model;

import java.util.ArrayList;

/**
 * Created by Chudofom on 03.10.16.
 */
public class ProductCategory {
    public String name;
    public ArrayList<Product> products;

    public ProductCategory(String name, ArrayList<Product> products) {
        this.name = name;
        this.products = products;
    }

    public ProductCategory() {
    }
}
