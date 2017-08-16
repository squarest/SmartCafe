package com.example.clevercafe.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

/**
 * Created by Chudofom on 03.10.16.
 */
@Entity(tableName = "productCategories")
public class ProductCategory {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;

    public String iconPath;

    @Ignore
    public String getIconPath() {
        return iconPath;
    }

    @Ignore
    public void setIconPath(String iconPath) {

        this.iconPath = iconPath;
    }

    public ProductCategory() {
    }

    @Ignore
    public ArrayList<Product> products;

    @Ignore
    public ProductCategory(String name, ArrayList<Product> products) {
        this.name = name;
        this.products = products;
    }
}
