package com.example.clevercafe.storage.presentation;

import com.example.clevercafe.entities.Ingredient;

/**
 * Created by Chudofom on 20.03.17.
 */

public interface IStoragePresenter {
    void viewInit();

    void addIngredientButClicked();

    void editIngredientButClicked(int categoryId, int ingredientId);

    void deleteIngredientButClicked(int categoryId, int ingredientId);

    void addCategoryButClicked();

    void editCategoryButClicked(int categoryId);

    void deleteCategoryButClicked(int categoryId);

    void submitIngredientFormButClicked(int categoryId, int ingredientId, Ingredient ingredient, boolean editForm);

    void submitCategoryFormButClicked(int categoryId, String name, boolean editForm);
}
