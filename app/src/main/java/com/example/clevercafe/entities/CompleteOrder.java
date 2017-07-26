package com.example.clevercafe.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

/**
 * Created by Chudofom on 24.07.17.
 */

@Entity(tableName = "completeOrders")
public class CompleteOrder {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public Double sum;
    @Ignore
    public ArrayList<Product> products;

    public CompleteOrder() {
    }

    @Ignore
    public CompleteOrder(Double sum, ArrayList<Product> products) {
        this.sum = sum;
        this.products = products;
    }
}
