package com.example.clevercafe.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.utils.DateTimeUtil;

import java.util.ArrayList;

/**
 * Created by Chudofom on 07.07.17.
 */

public class OrderQueueRepo {
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ProductRepository productRepository;
    public long LAST_ORDER_ID;
    public OrderQueueRepo(Context context) {
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        productRepository = new ProductRepository(context);

    }

    public void addOrder(Order order) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.ORDER_SUM, order.sum);
        values.put(DBHelper.DATE_TIME, DateTimeUtil.getCurrentTimeInMils());
        LAST_ORDER_ID = sqLiteDatabase.insert(
                DBHelper.ORDER_QUEUE_TABLE,
                null,
                values);
        order.id = (int) LAST_ORDER_ID;
        if (order.products != null && order.products.size() > 0) addProducts(order);

    }


    private void addProducts(Order order) {
        for (int i = 0; i < order.products.size(); i++) {
            Product product = order.products.get(i);
            ContentValues values = new ContentValues();
            values.put(DBHelper.ORDER_ID, order.id);
            values.put(DBHelper.PRODUCT_ID, product.id);
            values.put(DBHelper.QUANTITY, product.quantity);
            sqLiteDatabase.insert(
                    DBHelper.QUEUE_PRODUCTS_TABLE,
                    null,
                    values);
        }
    }

    public ArrayList<Order> getOrders() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.ORDER_QUEUE_TABLE, null, null, null, null, null, null);
        ArrayList<Order> orders = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    Order order = new Order();
                    order.id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.ID)));
                    order.sum = Double.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.ORDER_SUM)));
                    order.products = getProducts(order.id);
                    orders.add(order);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            return null;
        }
        return orders;
    }

    private ArrayList<Product> getProducts(int orderId) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.QUEUE_PRODUCTS_TABLE, null,
                DBHelper.ORDER_ID + " = ?", new String[]{String.valueOf(orderId)}, null, null, null);

        ArrayList<Product> products = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    Product product = productRepository.getProduct(Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.PRODUCT_ID))));
                    product.quantity = Double.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.QUANTITY)));
                    products.add(product);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            return null;
        }
        return products;
    }

//    public void editOrder(Order order) {
//        ContentValues values = new ContentValues();
//        values.put(DBHelper.ORDER_SUM, order.sum);
//        values.put(DBHelper.DATE_TIME, DateTimeUtil.getCurrentTimeInMils());
//        long orderId = sqLiteDatabase.insert(
//                DBHelper.ORDER_QUEUE_TABLE,
//                null,
//                values);
//        order.id = (int) orderId;
//        if (order.products != null && order.products.size() > 0) addProducts(order);
//        sqLiteDatabase.update(
//                dbHelper.PRODUCT_C_TABLE,
//                values,
//                DBHelper.ID + " = ?",
//                new String[]{String.valueOf(category.id)});
//    }
//
//
//    public void editProduct(Product product) {
//        ContentValues values = new ContentValues();
//        values.put(DBHelper.PRODUCT_NAME, product.name);
//        values.put(DBHelper.PRODUCT_COST, product.cost);
//        values.put(DBHelper.UNITS, product.units);
//        if (product.ingredients != null) {
//            editIngredients(product);
//        }
//        sqLiteDatabase.update(
//                DBHelper.PRODUCT_TABLE,
//                values,
//                DBHelper.ID + " = ?",
//                new String[]{String.valueOf(product.id)});
//    }
//
    public void deleteOrder(Order order) {
        sqLiteDatabase.delete(
                DBHelper.ORDER_QUEUE_TABLE,
                DBHelper.ID + " = ?",
                new String[]{String.valueOf(order.id)});
        if (order.products != null && order.products.size() > 0) {
            deleteProducts(order.id);
        }

    }

    private void deleteProducts(int categoryId) {
        sqLiteDatabase.delete(
                DBHelper.QUEUE_PRODUCTS_TABLE,
                DBHelper.ORDER_ID + " = ?",
                new String[]{String.valueOf(categoryId)});
    }
}

