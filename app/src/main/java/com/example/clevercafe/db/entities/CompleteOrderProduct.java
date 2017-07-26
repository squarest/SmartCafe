package com.example.clevercafe.db.entities;

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

    public CompleteOrderProduct() {

    }

    @Ignore
    public CompleteOrderProduct(long orderId, long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
}
