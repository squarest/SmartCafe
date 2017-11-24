package com.paper.smartcafe.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Chudofom on 24.07.17.
 */

@Entity(tableName = "completeOrders")
public class CompleteOrder {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public Double sum;
    public Double costSum;
    public Date dateTime;
    @Ignore
    public ArrayList<Product> products;

    public CompleteOrder() {
    }

    @Ignore
    public CompleteOrder(Double costSum, Double sum, ArrayList<Product> products) {
        this.costSum = costSum;
        this.sum = sum;
        this.products = products;
    }
}
