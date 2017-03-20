package com.example.clevercafe.activities.storage;

import com.example.clevercafe.model.Ingredient;

/**
 * Created by Chudofom on 20.03.17.
 */

public interface IStoragePresenter {
    void viewInit();
    void addProductButClicked();
    void editProductButClicked(int categoryId, int productId);
    void deleteProductButClicked(int categoryId, int productId);

    void addCategoryButClicked();
    void editCategoryButClicked(int categoryId);
    void deleteCategoryButClicked(int categoryId);
    void submitProductFormButClicked(int categoryId, int productId, Ingredient ingredient, boolean editForm);
    void submitCategoryFormButClicked(int categoryId, String name, boolean editForm);
}
