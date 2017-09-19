package com.example.clevercafe.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Chudofom on 24.07.17.
 */
@Entity(tableName = "orders")
public class Order {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long number;
    public Double sum;
    public Double costSum;

    public Order() {
    }

    @Ignore
    public Order(long number, ArrayList<Product> products) {
        this.number = number;
        this.products = products;
    }

    @Ignore
    public ArrayList<Product> products;
    @Ignore
    private HashMap<Long, Double> productsCount = new HashMap<>();

    @Ignore
    public void addProduct(Product product) {

        if (!productsCount.containsKey(product.id)) {
            products.add(product);
            productsCount.put(product.id, 1.0);
        } else {
            productsCount.put(product.id, productsCount.get(product.id) + 1.0);
        }
    }

    @Ignore
    public void removeProduct(int productPosition) {

        productsCount.remove(products.get(productPosition).id);
        products.remove(productPosition);
    }

    @Ignore
    public void setProductCount(long productId, Double quantity) {
        productsCount.put(productId, quantity);
    }

    @Ignore
    public Double getProductCount(long productId) {
        return productsCount.get(productId);
    }

    @Ignore
    private HashMap<Long, String> productsComments = new HashMap<>();

    @Ignore
    public void setProductComment(long productId, String comment) {
        productsComments.put(productId, comment);
    }

    @Ignore
    public String getProductComment(long productId) {
        return productsComments.get(productId);
    }
}
