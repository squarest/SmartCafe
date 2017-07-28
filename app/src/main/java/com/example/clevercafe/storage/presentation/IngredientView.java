package com.example.clevercafe.storage.presentation;

import com.arellomobile.mvp.MvpView;
import com.example.clevercafe.entities.IngredientCategory;
import com.example.clevercafe.entities.Product;

import java.util.ArrayList;

/**
 * Created by Chudofom on 27.07.17.
 */

public interface IngredientView extends MvpView {
    void showButtons();

    void hideButtons();

    void showStorage(ArrayList<IngredientCategory> ingredientCategories);

    void showIngredients(Product product);

    void updateIngredients();

    void returnProduct(Product product);

    void returnCancel();
}
