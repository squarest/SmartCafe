package com.example.clevercafe.menu.domain;

import com.example.clevercafe.data.repositories.ProductRepository;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by Chudofom on 24.07.17.
 */

public class MenuInteractor implements IMenuInteractor {
    private ProductRepository productRepository;

    private ReplaySubject<Long> productsUpdates;
    private ReplaySubject<Boolean> categoriesUpdates;
    private ReplaySubject<Long> productEdited;
    private ReplaySubject<Long> categoryEdited;

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
    public Observable<ArrayList<Product>> loadProducts(long categoryId) {
        return Observable.create(e ->
        {
            ArrayList<Product> products = productRepository.getProducts(categoryId);
            if (products != null) e.onNext(products);
            else e.onError(new NullPointerException());
        });
    }

    @Override
    public Observable<ProductCategory> loadCategory(long categoryId) {
        return Observable.create(e ->
        {
            ProductCategory category = productRepository.getProductCategory(categoryId);
            if (category != null) e.onNext(category);
            else e.onError(new NullPointerException());
        });
    }

    @Override
    public Observable<Product> loadProduct(long productId) {
        return Observable.create(e ->
        {
            Product product = productRepository.getProduct(productId);
            if (product != null) e.onNext(product);
            else e.onError(new NullPointerException());
        });
    }

    @Override
    public Observable<Long> productsUpdates() {
        productsUpdates = ReplaySubject.create();
        return productsUpdates;
    }

    @Override
    public Observable<Boolean> categoriesUpdates() {
        categoriesUpdates = ReplaySubject.create();
        return categoriesUpdates;
    }

    @Override
    public Observable<Long> productEdited() {
        productEdited = ReplaySubject.create();
        return productEdited;
    }

    @Override
    public Observable<Long> categoryEdited() {
        categoryEdited = ReplaySubject.create();
        return categoryEdited;
    }

    @Override
    public Completable addCategory(ProductCategory category) {
        return Completable.create(e ->
        {
            if (category.id == 0 & productRepository.findCategory(category.name) == null | category.id != 0) {
                productRepository.addCategory(category);
                categoriesUpdates.onNext(true);
                e.onComplete();
            } else {
                e.onError(new Exception("Имя уже существует"));
            }
        });
    }

    @Override
    public void editCategory(long categoryId) {
        categoryEdited.onNext(categoryId);
    }

    @Override
    public void editProduct(long productId) {
        productEdited.onNext(productId);
    }

    @Override
    public Completable deleteCategory(ProductCategory category) {
        return Completable.create(e ->
        {
            productRepository.deleteCategory(category);
            categoriesUpdates.onNext(true);
            e.onComplete();
        });
    }

    @Override
    public Completable addProduct(Product product) {
        return Completable.create(e ->
        {
            if (product.id == 0 & productRepository.findProduct(product.name) == null | product.id != 0) {
                productRepository.addProduct(product);
                productsUpdates.onNext(product.categoryId);
                e.onComplete();
            } else {
                e.onError(new Exception("Имя уже существует"));
            }
        });
    }


    @Override
    public Completable deleteProduct(Product product) {
        return Completable.create(e ->
        {
            productRepository.deleteProduct(product);
            productsUpdates.onNext(product.categoryId);
            e.onComplete();
        });
    }
}
