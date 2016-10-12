package com.example.clevercafe.model;

import java.util.ArrayList;

/**
 * Created by Chudofom on 09.10.16.
 */

public class Order {
    public int id;
    public ArrayList<Product> products;

    public Order(int id) {
        this.id = id;
    }

    public Order(ArrayList<Product> products) {
        this.products = products;
    }

    public Order(int id, ArrayList<Product> products) {
        this.id = id;
        this.products = products;
    }

}
