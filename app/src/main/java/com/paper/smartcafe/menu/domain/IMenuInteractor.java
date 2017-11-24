package com.paper.smartcafe.menu.domain;

import com.paper.smartcafe.entities.Product;
import com.paper.smartcafe.entities.ProductCategory;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Chudofom on 24.07.17.
 */

public interface IMenuInteractor {
    Observable<ArrayList<ProductCategory>> loadCategories();

    Observable<ArrayList<Product>> loadProducts(long categoryId);

    Observable<ProductCategory> loadCategory(long categoryId);

    Observable<Product> loadProduct(long productId);

    Observable<Long> productsUpdates();

    Observable<Boolean> categoriesUpdates();

    Observable<Long> productEdited();

    Observable<Long> categoryEdited();

    Completable addCategory(ProductCategory category);

    void editCategory(long categoryId);

    void editProduct(long productId);

    Completable deleteCategory(ProductCategory category);

    Completable addProduct(Product product);

    Completable deleteProduct(Product product);


}
