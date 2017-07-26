package com.example.clevercafe.storage.domain;

import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.IngredientCategory;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Chudofom on 24.07.17.
 */

public interface IStrorageInteractor {
    Observable<ArrayList<IngredientCategory>> loadCategories();


    Completable addCategory(IngredientCategory category);

    Completable editCategory(IngredientCategory category);

    Completable deleteCategory(IngredientCategory category);


    Completable addIngredient(Ingredient ingredient);

    Completable editIngredient(Ingredient ingredient);

    Completable deleteIngredient(Ingredient ingredient);
}
