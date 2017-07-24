package com.example.clevercafe.storage.presentation;

import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.IngredientCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 20.03.17.
 */

public interface IStorageView {
    void showCategories(ArrayList<IngredientCategory> categories);
    void updateCategories(ArrayList<IngredientCategory> categories);
    void createAddProductForm(int categoryId, int productId, Ingredient ingredient, boolean editForm);

    void clearAddProductForm();

    void createCategoryDialog(int categoryId, String name, boolean editForm);

}
