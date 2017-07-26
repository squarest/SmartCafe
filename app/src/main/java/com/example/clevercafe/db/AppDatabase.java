package com.example.clevercafe.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.clevercafe.db.dao.DatabaseDao;
import com.example.clevercafe.db.entities.CompleteOrderProduct;
import com.example.clevercafe.db.entities.OrderProduct;
import com.example.clevercafe.db.entities.ProductIngredient;
import com.example.clevercafe.entities.CompleteOrder;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.IngredientCategory;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;

/**
 * Created by Chudofom on 24.07.17.
 */
@Database(entities = {Ingredient.class, IngredientCategory.class, Order.class, CompleteOrder.class,
        ProductCategory.class, Product.class, CompleteOrderProduct.class, OrderProduct.class, ProductIngredient.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DatabaseDao databaseDao();
}
