package com.example.clevercafe.activities.menu;

import com.example.clevercafe.model.Product;
import com.example.clevercafe.model.ProductCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 20.03.17.
 */

public interface IMenuView {
    void showCategories(ArrayList<ProductCategory> categories);
    void showProducts(ArrayList<Product> products);
    void updateMenu(ArrayList<ProductCategory> categories);
    void showStorage();
    void createAddProductForm(int categoryId, int productId, Product product, boolean editForm);

    void clearAddProductForm();

    void createCategoryDialog(int categoryId, String name, boolean editForm);
}
