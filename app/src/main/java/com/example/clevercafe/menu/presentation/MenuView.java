package com.example.clevercafe.menu.presentation;

import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 20.03.17.
 */

public interface MenuView {
    void showCategories(ArrayList<ProductCategory> categories);
    void showProducts(ArrayList<Product> products);
    void updateMenu(ArrayList<ProductCategory> categories);
    void removeCategory(ArrayList<ProductCategory> categories, int position);
    void updateProducts(ArrayList <Product> products);
    void createAddProductForm(int categoryId, int productId, Product product, boolean editForm);

    void clearAddProductForm();

    void createCategoryDialog(int categoryId, String name, boolean editForm);
}
