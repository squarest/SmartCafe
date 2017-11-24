package com.paper.smartcafe.data.repositories;

import android.content.SharedPreferences;

import com.paper.smartcafe.data.dao.DatabaseDao;
import com.paper.smartcafe.data.entities.OrderProduct;
import com.paper.smartcafe.data.entities.ProductIngredient;
import com.paper.smartcafe.entities.Ingredient;
import com.paper.smartcafe.entities.Order;
import com.paper.smartcafe.entities.Product;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Chudofom on 07.07.17.
 */

public class OrderRepository {
    public DatabaseDao databaseDao;
    public SharedPreferences preferences;

    public OrderRepository(DatabaseDao databaseDao, SharedPreferences preferences) {
        this.databaseDao = databaseDao;
        this.preferences = preferences;
    }

    private long curOrderNumber;
    private String curOrderNumberString = "curOrderNumber";


    public long getCurOrderNumber() {
        this.curOrderNumber = preferences.getLong(curOrderNumberString, 1);
        long lastOrderTime = preferences.getLong("lastOrderTime", 0);
        Calendar lastCalendar = Calendar.getInstance();
        lastCalendar.setTimeInMillis(lastOrderTime);
        lastCalendar.set(Calendar.HOUR_OF_DAY, 0);
        lastCalendar.set(Calendar.MINUTE, 0);
        lastCalendar.set(Calendar.SECOND, 0);
        lastCalendar.set(Calendar.MILLISECOND, 0);
        Calendar curCalendar = Calendar.getInstance();
        curCalendar.set(Calendar.HOUR_OF_DAY, 0);
        curCalendar.set(Calendar.MINUTE, 0);
        curCalendar.set(Calendar.SECOND, 0);
        curCalendar.set(Calendar.MILLISECOND, 0);
        if (curCalendar.getTime().after(lastCalendar.getTime())) this.curOrderNumber = 1;
        return curOrderNumber;
    }

    private void setCurOrderNumber(Order order) {
        this.curOrderNumber = order.number + 1;
        preferences.edit()
                .putLong(curOrderNumberString, curOrderNumber)
                .putLong("lastOrderTime", System.currentTimeMillis())
                .apply();
    }

    public void addOrder(Order order) {
        order.id = databaseDao.insertOrder(order);
        setCurOrderNumber(order);
        if (order.products != null && order.products.size() > 0) {
            deleteProducts(order.id);
            addProducts(order);
        }
    }


    private void addProducts(Order order) {
        List<OrderProduct> orderProductList = new ArrayList<>();
        for (int i = 0; i < order.products.size(); i++) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.orderId = order.id;
            orderProduct.productId = order.products.get(i).id;
            orderProduct.quantity = order.getProductCount(order.products.get(i).id);
            orderProduct.comment = order.getProductComment(order.products.get(i).id);
            orderProductList.add(orderProduct);
        }
        databaseDao.insertOrderProducts(orderProductList);
    }

    public ArrayList<Order> getOrders() {
        ArrayList<Order> orders = (ArrayList<Order>) databaseDao.getOrders();
        for (Order order : orders) {
            //todo:findProductsById
            //todo:findIngredientsById
            order.products = getProducts(order);
        }
        return orders;
    }

    public Order getOrder(long orderId) {
        Order order = databaseDao.getOrder(orderId);
        order.number = orderId;
        order.products = getProducts(order);
        return order;
    }

    private ArrayList<Product> getProducts(Order order) {
        ArrayList<OrderProduct> orderProducts = (ArrayList<OrderProduct>) databaseDao.getOrderProducts(order.id);
        ArrayList<Product> products = new ArrayList<>();
        if (orderProducts != null && !orderProducts.isEmpty()) {
            for (OrderProduct orderProduct : orderProducts) {
                order.setProductCount(orderProduct.productId, orderProduct.quantity);
                order.setProductComment(orderProduct.productId, orderProduct.comment);
                Product product = databaseDao.getProduct(orderProduct.productId);
                if (product != null) {
                    product.ingredients = getIngredients(product);
                    products.add(product);
                }
            }
        }
        return products;

    }

    private ArrayList<Ingredient> getIngredients(Product product) {
        ArrayList<ProductIngredient> productIngredients = (ArrayList<ProductIngredient>) databaseDao.getProductIngredients(product.id);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        if (productIngredients != null && productIngredients.size() > 0) {
            for (ProductIngredient productIngredient : productIngredients) {
                ingredients.add(databaseDao.getIngredient(productIngredient.ingredientId));
                product.setIngredientCount(productIngredient.ingredientId, productIngredient.quantity);
            }
        }
        return ingredients;
    }

    public void deleteOrder(Order order) {
        databaseDao.deleteOrder(order);
        if (order.products != null && order.products.size() > 0) {
            deleteProducts(order.id);
        }

    }

    private void deleteProducts(long orderId) {
        databaseDao.deleteOrderProducts(orderId);
    }
}


