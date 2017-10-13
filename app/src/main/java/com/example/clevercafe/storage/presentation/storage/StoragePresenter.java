package com.example.clevercafe.storage.presentation.storage;

import com.arellomobile.mvp.InjectViewState;
import com.example.clevercafe.App;
import com.example.clevercafe.base.BasePresenter;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.IngredientCategory;
import com.example.clevercafe.storage.domain.IStrorageInteractor;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 20.03.17.
 */
@InjectViewState
public class StoragePresenter extends BasePresenter<StorageView> {
    private ArrayList<IngredientCategory> categories;
    private StorageView view = getViewState();
    @Inject
    public IStrorageInteractor interactor;

    public StoragePresenter() {
        App.getStorageComponent().inject(this);
    }

    public void viewInit() {
        Disposable disposable = interactor.loadCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredientCategories ->
                {
                    categories = ingredientCategories;
                    if (categories != null) view.showCategories(categories);

                }, Throwable::fillInStackTrace);
        setDisposable(disposable);
    }

    public void addIngredientButClicked() {
        view.createAddIngredientForm(-1, -1, null, false);
    }

    public void editIngredientButClicked(int categoryId, int ingredientId) {
        Ingredient ingredient = categories.get(categoryId).ingredients.get(ingredientId);
        view.createAddIngredientForm(categoryId, ingredientId, ingredient, true);
    }

    public void deleteIngredientButClicked(int categoryId, int ingredientId) {
        Disposable disposable = interactor.deleteIngredient(categories.get(categoryId).ingredients.get(ingredientId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCategories, throwable -> {
                    throwable.printStackTrace();
                    view.showWarningDialog("Удаление невозможно", throwable.getMessage());
                });
        setDisposable(disposable);
    }

    public void subtractIngredientButClicked(int categoryId, int ingredientId) {
        getViewState().showSubtractDialog(categories.get(categoryId).ingredients.get(ingredientId));
    }

    public void ingredientSubtracted(Ingredient ingredient) {
        Disposable disposable = interactor.subtractIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCategories, Throwable::fillInStackTrace);
        setDisposable(disposable);
    }

    public void addCategoryButClicked() {
        view.createCategoryDialog(-1, null, false);
    }

    public void editCategoryButClicked(int categoryId) {
        view.createCategoryDialog(categoryId, categories.get(categoryId).name, true);

    }

    public void deleteCategoryButClicked(int categoryId) {
        Disposable disposable = interactor.deleteCategory(categories.get(categoryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCategories, throwable -> {
                    throwable.printStackTrace();
                    view.showWarningDialog("Удаление невозможно", throwable.getMessage());
                });
        setDisposable(disposable);
    }

    public void submitIngredientFormButClicked(int categoryId, Ingredient ingredient, boolean editForm) {
        Completable completable;
        if (editForm) {
            completable = interactor.editIngredient(ingredient);
        } else {
            ingredient.categoryId = categories.get(categoryId).id;
            completable = interactor.addIngredient(ingredient);
        }
        Disposable disposable = completable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCategories, throwable ->
                {
                    getViewState().showMessage(throwable.getMessage());
                    throwable.printStackTrace();
                });
        setDisposable(disposable);
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

        Disposable disposable = completable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCategories, throwable ->
                {
                    getViewState().showMessage(throwable.getMessage());
                    throwable.printStackTrace();
                });
        setDisposable(disposable);
    }

    private void updateCategories() {
        Disposable disposable = interactor.loadCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productCategories ->
                {
                    categories.clear();
                    categories.addAll(productCategories);
                    view.updateCategories(categories);

                }, Throwable::fillInStackTrace);
        setDisposable(disposable);

    }
}


