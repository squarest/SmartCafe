package com.example.clevercafe.storage.presentation;

import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.IngredientCategory;
import com.example.clevercafe.storage.domain.StorageInteractor;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 20.03.17.
 */

public class StoragePresenter implements IStoragePresenter {
    private ArrayList<IngredientCategory> categories;
    private StorageView view;
    public StorageInteractor interactor = new StorageInteractor();

    public StoragePresenter(StorageActivity view) {
        this.view = view;
    }

    @Override
    public void viewInit() {
        interactor.loadCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredientCategories ->
                {
                    categories = ingredientCategories;
                    if (categories != null) view.showCategories(categories);

                }, Throwable::fillInStackTrace);
    }

    @Override
    public void addIngredientButClicked() {
        view.createAddIngredientForm(-1, -1, null, false);
    }

    @Override
    public void editIngredientButClicked(int categoryId, int ingredientId) {
        Ingredient ingredient = categories.get(categoryId).ingredients.get(ingredientId);
        view.createAddIngredientForm(categoryId, ingredientId, ingredient, true);
    }

    @Override
    public void deleteIngredientButClicked(int categoryId, int ingredientId) {
        interactor.deleteIngredient(categories.get(categoryId).ingredients.get(ingredientId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCategories, Throwable::fillInStackTrace);
    }

    @Override
    public void addCategoryButClicked() {
        view.createCategoryDialog(-1, null, false);
    }

    @Override
    public void editCategoryButClicked(int categoryId) {
        view.createCategoryDialog(categoryId, categories.get(categoryId).name, true);

    }

    @Override
    public void deleteCategoryButClicked(int categoryId) {
        interactor.deleteCategory(categories.get(categoryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCategories, Throwable::fillInStackTrace);
    }

    @Override
    public void submitIngredientFormButClicked(int categoryId, int ingredientId, Ingredient ingredient, boolean editForm) {
        Completable completable;
        if (editForm) {
            completable = interactor.editIngredient(ingredient);
        } else {
            ingredient.categoryId = categories.get(categoryId).id;
            completable = interactor.addIngredient(ingredient);
        }
        completable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCategories, Throwable::fillInStackTrace);
    }

    @Override
    public void submitCategoryFormButClicked(int categoryId, String name, boolean editForm) {
        Completable completable;
        if (editForm) {
            IngredientCategory category = categories.get(categoryId);
            category.name = name;
            completable = interactor.editCategory(category);
        } else {
            IngredientCategory category = new IngredientCategory(name, new ArrayList<>());
            completable = interactor.addCategory(category);
        }

        completable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCategories, Throwable::fillInStackTrace);
    }

    private void updateCategories() {
        interactor.loadCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productCategories ->
                {
                    categories.clear();
                    categories.addAll(productCategories);
                    view.updateCategories(categories);

                }, Throwable::fillInStackTrace);

    }
}


