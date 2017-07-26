package com.example.clevercafe.main.domain;

import com.example.clevercafe.db.CompleteOrderRepository;
import com.example.clevercafe.db.OrderRepository;
import com.example.clevercafe.db.ProductRepository;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.ProductCategory;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Chudofom on 24.07.17.
 */

public class MainInteractor implements IMainInteractor {
    public ProductRepository productRepository = new ProductRepository();
    public OrderRepository orderRepository = new OrderRepository();
    public CompleteOrderRepository completeOrderRepository = new CompleteOrderRepository();

    @Override
    public Observable<ArrayList<ProductCategory>> loadCategories() {
        return Observable.create(e ->
        {
            ArrayList<ProductCategory> categories = productRepository.getCategories();
            if (categories != null) e.onNext(categories);
            else e.onError(new NullPointerException());
        });
    }

    @Override
    public Observable<ArrayList<Order>> loadOrders() {
        return Observable.create(e ->
        {
            ArrayList<Order> orders = orderRepository.getOrders();
            if (orders != null) e.onNext(orders);
            else e.onError(new NullPointerException());
        });
    }

    @Override
    public Completable setOrder(Order order) {
        return Completable.create(e ->
        {
            orderRepository.addOrder(order);
            e.onComplete();
        });
    }

    @Override
    public Completable removeOrder(Order order) {
        return Completable.create(e ->
        {
            orderRepository.deleteOrder(order);
            e.onComplete();
        });
    }

    @Override
    public Completable setCompleteOrder(Order order) {
        return Completable.create(e ->
        {
            completeOrderRepository.addCompleteOrder(order);
            orderRepository.deleteOrder(order);
            e.onComplete();
        });
    }
}
