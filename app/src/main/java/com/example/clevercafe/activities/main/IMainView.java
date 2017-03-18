package com.example.clevercafe.activities.main;

import com.example.clevercafe.model.ProductCategory;
import com.example.clevercafe.model.Order;
import com.example.clevercafe.model.Product;

import java.util.ArrayList;

/**
 * Created by Chudofom on 03.10.16.
 */
public interface IMainView {
    void showProducts(ArrayList<Product> products);

    void showCategories(ArrayList<ProductCategory> categories);

    void showOrders(ArrayList<Order> orders);

    void showMessage(String message);

    void moveOrder(int start, int finish);

    void removeOrder(int position);
}
