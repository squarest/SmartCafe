package com.paper.smartcafe.storage.presentation.storage;

import com.arellomobile.mvp.MvpView;
import com.paper.smartcafe.entities.Ingredient;
import com.paper.smartcafe.entities.IngredientCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 20.03.17.
 */

public interface StorageView extends MvpView {
    void showCategories(ArrayList<IngredientCategory> categories);

    void updateCategories(ArrayList<IngredientCategory> categories);

    void createAddIngredientForm(int categoryId, int ingredientId, Ingredient ingredient, boolean editForm);

    void clearAddIngredientForm();

    void createCategoryDialog(int categoryId, String name, boolean editForm);

    void showSubtractDialog(Ingredient ingredient);

    void showMessage(String message);

    void showWarningDialog(String title, String message);

}
