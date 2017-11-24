package com.paper.smartcafe.storage.presentation.ingredient;

import com.arellomobile.mvp.MvpView;
import com.paper.smartcafe.entities.IngredientCategory;
import com.paper.smartcafe.entities.Product;

import java.util.ArrayList;

/**
 * Created by Chudofom on 27.07.17.
 */

public interface IngredientView extends MvpView {
    void showStorage(ArrayList<IngredientCategory> ingredientCategories);

    void showIngredients(Product product);

    void updateIngredients();

    void returnProduct(Product product);

    void returnCancel();
}
