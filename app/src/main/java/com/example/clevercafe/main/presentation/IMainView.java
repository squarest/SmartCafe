package com.example.clevercafe.main.presentation;

import com.example.clevercafe.entities.ProductCategory;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.Product;

import java.util.ArrayList;

/**
 * Created by Chudofom on 03.10.16.
 */
public interface IMainView {
    void showProducts(ArrayList<Product> products);

    void showCategories(ArrayList<ProductCategory> categories);

    void setOrders(ArrayList<Order> orders);

    void updateOrders(ArrayList<Order> orders);

    void updateOrder(Order order);

    void setOrder(Order order);

    void showMessage(String message);

    void moveOrder(int start, int finish);

    void removeOrder(int position);
}
