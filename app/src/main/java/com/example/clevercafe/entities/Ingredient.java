package com.example.clevercafe.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/*
 * Created by Chudofom on 15.03.17.
 */
@Entity(tableName = "ingredients")
public class Ingredient implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long categoryId;
    public String name;
    public Double quantity;
    public String units;
    public Double cost;

    @Ignore
    public Ingredient(String name, double quantity, String units) {
        this.name = name;
        this.quantity = quantity;
        this.units = units;
    }


    public Ingredient() {
    }
}
