package com.example.clevercafe.menu.domain;

import com.example.clevercafe.db.ProductRepository;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Chudofom on 24.07.17.
 */

public class MenuInteractor implements IMenuInteractor {
    public ProductRepository productRepository;

    public MenuInteractor(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
    public Completable addCategory(ProductCategory category) {
        return Completable.create(e ->
        {
            productRepository.addCategory(category);
            e.onComplete();
        });
    }

    @Override
    public Completable editCategory(ProductCategory category) {
        return Completable.create(e ->
        {
            productRepository.editCategory(category);
            e.onComplete();
        });
    }

    @Override
    public Completable deleteCategory(ProductCategory category) {
        return Completable.create(e ->
        {
            productRepository.deleteCategory(category);
            e.onComplete();
        });
    }

    @Override
    public Completable addProduct(Product product) {
        return Completable.create(e ->
        {
            productRepository.addProduct(product);
            e.onComplete();
        });
    }

    @Override
    public Completable editProduct(Product product) {
        return Completable.create(e ->
        {
            productRepository.editProduct(product);
            e.onComplete();
        });
    }

    @Override
    public Completable deleteProduct(Product product) {
        return Completable.create(e ->
        {
            productRepository.deleteProduct(product);
            e.onComplete();
        });
    }
}
