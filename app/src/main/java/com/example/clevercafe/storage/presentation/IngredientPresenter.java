package com.example.clevercafe.storage.presentation;

import com.example.clevercafe.entities.IngredientCategory;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.storage.domain.StorageInteractor;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 27.07.17.
 */

public class IngredientPresenter {

    private StorageInteractor interactor;
    private Product product;
    private ArrayList<IngredientCategory> categories;
    IngredientView view;

    public IngredientPresenter(IngredientView view) {
        interactor = new StorageInteractor();
        this.view = view;
    }

    public void initView(Product product) {
        this.product = product;
        if (product == null)
            product = new Product(new ArrayList<>());
        if (product.ingredients.size() > 0) {
            view.showButtons();
        } else view.hideButtons();
        view.showIngredients(product);
        interactor.loadCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredientCategories ->
                {
                    categories = ingredientCategories;
                    if (categories != null) view.showStorage(categories);

                }, Throwable::fillInStackTrace);

    }

    public void ingredientClicked(int categoryId, int productId) {

        product.addIngredient(categories.get(categoryId).ingredients.get(productId));
        view.updateIngredients();
        if (product.ingredients.size() == 1) {
            view.showButtons();
        }
    }

    public void ingredientRemoved(int position) {
        product.ingredients.remove(position);
        view.updateIngredients();
        if (product.ingredients.size() == 0) {
            view.hideButtons();
        }
    }

    public void submitButtonClicked() {
        view.returnProduct(product);
    }

    public void cancelButtonClicked() {
        view.returnCancel();
    }
}
