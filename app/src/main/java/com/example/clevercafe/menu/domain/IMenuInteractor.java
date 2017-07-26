package com.example.clevercafe.menu.domain;

import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Chudofom on 24.07.17.
 */

public interface IMenuInteractor {
    Observable<ArrayList<ProductCategory>> loadCategories();


    Completable addCategory(ProductCategory category);

    Completable editCategory(ProductCategory category);

    Completable deleteCategory(ProductCategory category);


    Completable addProduct(Product product);

    Completable editProduct(Product product);

    Completable deleteProduct(Product product);
}
