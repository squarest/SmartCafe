package com.example.clevercafe.storage.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
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
@InjectViewState
public class StoragePresenter extends MvpPresenter<StorageView> {
    private ArrayList<IngredientCategory> categories;
    private StorageView view = getViewState();
    public StorageInteractor interactor = new StorageInteractor();


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

    public void addIngredientButClicked() {
        view.createAddIngredientForm(-1, -1, null, false);
    }

    public void editIngredientButClicked(int categoryId, int ingredientId) {
        Ingredient ingredient = categories.get(categoryId).ingredients.get(ingredientId);
        view.createAddIngredientForm(categoryId, ingredientId, ingredient, true);
    }

    public void deleteIngredientButClicked(int categoryId, int ingredientId) {
        interactor.deleteIngredient(categories.get(categoryId).ingredients.get(ingredientId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCategories, Throwable::fillInStackTrace);
    }

    public void addCategoryButClicked() {
        view.createCategoryDialog(-1, null, false);
    }

    public void editCategoryButClicked(int categoryId) {
        view.createCategoryDialog(categoryId, categories.get(categoryId).name, true);

    }

    public void deleteCategoryButClicked(int categoryId) {
        interactor.deleteCategory(categories.get(categoryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCategories, Throwable::fillInStackTrace);
    }

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


