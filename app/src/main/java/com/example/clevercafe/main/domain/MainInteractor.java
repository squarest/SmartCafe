package com.example.clevercafe.main.domain;

import com.example.clevercafe.db.CompleteOrderRepository;
import com.example.clevercafe.db.IngredientRepository;
import com.example.clevercafe.db.OrderRepository;
import com.example.clevercafe.db.ProductRepository;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Chudofom on 24.07.17.
 */

public class MainInteractor implements IMainInteractor {
    public ProductRepository productRepository;
    public OrderRepository orderRepository;
    public CompleteOrderRepository completeOrderRepository;
    public IngredientRepository ingredientRepository;

    public MainInteractor(ProductRepository productRepository, OrderRepository orderRepository,
                          CompleteOrderRepository completeOrderRepository, IngredientRepository ingredientRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.completeOrderRepository = completeOrderRepository;
        this.ingredientRepository = ingredientRepository;
    }

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
            subtractIngredients(order);
            e.onComplete();
        });
    }

    @Override
    public Single<Double> checkIngredients(Product product, double productCount) {
        return Single.create(e ->
        {
            double maxProductCount = -1;
            ArrayList<Ingredient> ingredients = product.ingredients;
            for (Ingredient ingredient : ingredients) {
                double quantity = ingredientRepository.getIngredientsQuantity(ingredient.id);
                if (quantity < product.getIngredientCount(ingredient.id) * productCount) {
                    double count = quantity / product.getIngredientCount(ingredient.id);
                    if (count > maxProductCount) maxProductCount = count;
                }
            }
            if (maxProductCount > -1) e.onSuccess(maxProductCount);
        });
    }

    @Override
    public long getCurOrderId() {
        return orderRepository.getOrderId();
    }

    private void subtractIngredients(Order order) {
        for (Product product : order.products) {
            for (Ingredient ingredient : product.ingredients) {
                long id = ingredient.id;
                double quantity = ingredientRepository.getIngredientsQuantity(id);
                ingredient.quantity = quantity - (product.getIngredientCount(id) * order.getProductCount(product.id));
                ingredientRepository.addIngredient(ingredient);
            }
        }
    }
}
