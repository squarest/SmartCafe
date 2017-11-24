package com.paper.smartcafe.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.paper.smartcafe.data.dao.AnalyticsDao;
import com.paper.smartcafe.data.dao.DatabaseDao;
import com.paper.smartcafe.data.dao.ReportDao;
import com.paper.smartcafe.data.entities.CompleteOrderProduct;
import com.paper.smartcafe.data.entities.InvoiceIngredient;
import com.paper.smartcafe.data.entities.OrderProduct;
import com.paper.smartcafe.data.entities.ProductIngredient;
import com.paper.smartcafe.entities.CompleteOrder;
import com.paper.smartcafe.entities.Ingredient;
import com.paper.smartcafe.entities.IngredientCategory;
import com.paper.smartcafe.entities.Invoice;
import com.paper.smartcafe.entities.Order;
import com.paper.smartcafe.entities.Product;
import com.paper.smartcafe.entities.ProductCategory;
import com.paper.smartcafe.utils.RoomConverter;

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
