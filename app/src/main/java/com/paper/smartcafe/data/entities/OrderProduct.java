package com.paper.smartcafe.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Chudofom on 24.07.17.
 */
@Entity(tableName = "orderProducts")
public class OrderProduct {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long orderId;
    public long productId;
    public double quantity;
    public String comment;

    public OrderProduct() {
    }

    @Ignore
    public OrderProduct(long orderId, long productId, double quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
