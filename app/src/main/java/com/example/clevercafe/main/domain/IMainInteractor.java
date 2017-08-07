package com.example.clevercafe.main.domain;

import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Chudofom on 24.07.17.
 */

public interface IMainInteractor {
    Observable<ArrayList<ProductCategory>> loadCategories();

    Observable<ArrayList<Order>> loadOrders();

    Completable setOrder(Order order);

    Completable removeOrder(Order order);

    Completable setCompleteOrder(Order order);

    Completable checkIngredients(Product product, double productCount);
    long getCurOrderId();

}
