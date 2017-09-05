package com.example.clevercafe.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Chudofom on 24.07.17.
 */
@Entity(tableName = "completeOrderProducts")
public class CompleteOrderProduct {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long orderId;
    public long productId;
    public double quantity;

    public CompleteOrderProduct() {

    }

    @Ignore
    public CompleteOrderProduct(long orderId, long productId, double quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
