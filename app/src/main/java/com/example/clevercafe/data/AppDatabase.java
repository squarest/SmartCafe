package com.example.clevercafe.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.clevercafe.data.dao.AnalyticsDao;
import com.example.clevercafe.data.dao.DatabaseDao;
import com.example.clevercafe.data.dao.ReportDao;
import com.example.clevercafe.data.entities.CompleteOrderProduct;
import com.example.clevercafe.data.entities.InvoiceIngredient;
import com.example.clevercafe.data.entities.OrderProduct;
import com.example.clevercafe.data.entities.ProductIngredient;
import com.example.clevercafe.entities.CompleteOrder;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.IngredientCategory;
import com.example.clevercafe.entities.Invoice;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;
import com.example.clevercafe.utils.RoomConverter;

/**
 * Created by Chudofom on 24.07.17.
 */
@Database(entities = {Ingredient.class, IngredientCategory.class, Order.class, CompleteOrder.class,
        ProductCategory.class, Product.class, CompleteOrderProduct.class, OrderProduct.class,
        ProductIngredient.class, InvoiceIngredient.class, Invoice.class}, version = 1)
@TypeConverters({RoomConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DatabaseDao databaseDao();

    public abstract AnalyticsDao analyticsDao();

    public abstract ReportDao reportDao();
}
