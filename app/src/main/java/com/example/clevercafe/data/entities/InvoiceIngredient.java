package com.example.clevercafe.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Chudofom on 17.08.17.
 */
@Entity(tableName = "invoiceIngredients")
public class InvoiceIngredient {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long invoiceId;
    public long ingredientId;
    public String ingredientName;
    public String units;
    public double quantity;
    public double cost;

    public InvoiceIngredient() {
    }

}
