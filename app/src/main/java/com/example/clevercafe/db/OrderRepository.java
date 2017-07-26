package com.example.clevercafe.db;

import com.example.clevercafe.App;
import com.example.clevercafe.db.dao.DatabaseDao;
import com.example.clevercafe.db.entities.OrderProduct;
import com.example.clevercafe.db.entities.ProductIngredient;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chudofom on 07.07.17.
 */

public class OrderRepository {
    public DatabaseDao databaseDao = App.database.databaseDao();

    public void addOrder(Order order) {
        order.id = databaseDao.insertOrder(order);
        if (order.products != null && order.products.size() > 0) addProducts(order);
    }


    private void addProducts(Order order) {
        List<OrderProduct> orderProductList = new ArrayList<>();
        for (int i = 0; i < order.products.size(); i++) {
            orderProductList.add(new OrderProduct(order.id, order.products.get(i).id,
                    order.products.get(i).quantity));
        }
        databaseDao.insertOrderProducts(orderProductList);
    }

    public ArrayList<Order> getOrders() {
        ArrayList<Order> orders = (ArrayList<Order>) databaseDao.getOrders();
        for (Order order : orders) {
            //todo:findProductsById
            //todo:findIngredientsById
            order.products = getProducts(order.id);
        }
        return orders;
    }

    private ArrayList<Product> getProducts(long orderId) {
        ArrayList<OrderProduct> orderProducts = (ArrayList<OrderProduct>) databaseDao.getOrderProducts(orderId);
        ArrayList<Product> products = new ArrayList<>();
        if (orderProducts != null && orderProducts.size() > 0) {
            for (OrderProduct orderProduct : orderProducts) {
                Product product = databaseDao.getProduct(orderProduct.productId);
                if (product != null) {
                    product.quantity = orderProduct.quantity;
                    product.ingredients = getIngredients(product.id);
                    products.add(product);
                }
            }
        }
        return products;

    }

    private ArrayList<Ingredient> getIngredients(long productId) {
        ArrayList<ProductIngredient> productIngredients = (ArrayList<ProductIngredient>) databaseDao.getProductIngredients(productId);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        if (productIngredients != null && productIngredients.size() > 0) {
            for (ProductIngredient productIngredient : productIngredients) {
                ingredients.add(databaseDao.getIngredient(productIngredient.ingredientId));
            }
        }
        return ingredients;
    }

    public void editOrder(Order order) {
        databaseDao.updateOrder(order);
        if (order.products != null && order.products.size() > 0) {
            deleteProducts(order.id);
            addProducts(order);
        }
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


