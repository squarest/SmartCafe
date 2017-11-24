package com.paper.smartcafe.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Chudofom on 24.07.17.
 */
@Entity(tableName = "productIngredients")
public class ProductIngredient {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long productId;
    public long ingredientId;
    public double quantity;

    public ProductIngredient() {
    }

    @Ignore
    public ProductIngredient(long productId, long ingredientId, double quantity) {
        this.productId = productId;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
    }

}
