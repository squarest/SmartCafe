package com.example.clevercafe.main.presentation;

import com.arellomobile.mvp.MvpView;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.ProductCategory;
import com.example.clevercafe.entities.Product;

import java.util.ArrayList;

/**
 * Created by Chudofom on 03.10.16.
 */
public interface MainView extends MvpView {
    void showProducts(ArrayList<Product> products);

    void showCategories(ArrayList<ProductCategory> categories);

    void setOrders(ArrayList<Order> orders);

    void updateOrders(ArrayList<Order> orders);

    void updateOrder(Order order);

    void setOrder(Order order);

    void showMessage(String message);

    void showProgress();

    void hideProgress();

    void moveOrder(int start, int finish);

    void removeOrder(int position);
}
