package com.example.clevercafe.menu.presentation;

import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;
import com.example.clevercafe.menu.domain.IMenuInteractor;
import com.example.clevercafe.menu.domain.MenuInteractor;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 20.03.17.
 */

public class MenuPresenter implements IMenuPresenter {
    private MenuView menuView;
    private ArrayList<ProductCategory> categories = new ArrayList<>();
    public IMenuInteractor interactor = new MenuInteractor();

    public MenuPresenter(MenuView menuView) {

        this.menuView = menuView;
    }

    @Override
    public void viewInit() {
        setCategories();
    }

    private void setCategories() {
        interactor.loadCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productCategories ->
                {
                    categories.clear();
                    categories.addAll(productCategories);
                    menuView.showCategories(categories);
                }, Throwable::fillInStackTrace);
    }

    private void updateCategories() {
        interactor.loadCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productCategories ->
                {
                    categories.clear();
                    categories.addAll(productCategories);
                    menuView.updateMenu(categories);

                }, Throwable::fillInStackTrace);
    }

    @Override
    public void addProductButClicked() {
        menuView.createAddProductForm(-1, -1, null, false);
    }

    @Override
    public void editProductButClicked(int categoryId, int productId) {
        Product product = categories.get(categoryId).products.get(productId);
        menuView.createAddProductForm(categoryId, productId, product, true);

    }

    @Override
    public void deleteProductButClicked(int categoryId, int productId) {
        interactor.deleteProduct(categories.get(categoryId).products.get(productId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCategories, Throwable::fillInStackTrace);
    }

    @Override
    public void addIngredientsButClicked(ArrayList<Ingredient> ingredients) {

    }

    @Override
    public void addPictureButClicked() {

    }

    @Override
    public void addCategoryButClicked() {
        menuView.createCategoryDialog(-1, null, false);
    }

    @Override
    public void editCategoryButClicked(int categoryId) {
        menuView.createCategoryDialog(categoryId, categories.get(categoryId).name, true);
    }

    @Override
    public void deleteCategoryButClicked(int categoryId) {
        interactor.deleteCategory(categories.get(categoryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCategories, Throwable::fillInStackTrace);

    }

    @Override
    public void submitProductFormButClicked(int categoryId, int productId, Product product, boolean editForm) {
        product.categoryId = categories.get(categoryId).id;
        Completable completable;
        if (editForm) {
            completable = interactor.editProduct(product);
        } else {
            completable = interactor.addProduct(product);
        }
        completable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCategories, Throwable::fillInStackTrace);

    }

    @Override
    public void submitCategoryFormButClicked(int categoryId, String name, boolean editForm) {
        ProductCategory category;
        Completable completable;
        if (editForm) {
            category = categories.get(categoryId);
            category.name = name;
            completable = interactor.editCategory(category);
        } else {
            category = new ProductCategory(name, new ArrayList<>());
            completable = interactor.addCategory(category);

        }
        completable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateCategories, Throwable::fillInStackTrace);
    }

    @Override
    public void backToCategoryButtonClicked() {
        menuView.showCategories(categories);
    }

    @Override
    public void itemClicked(boolean categoryOnscreen, int categoryId) {
        if (categoryOnscreen) {
            menuView.showProducts(categories.get(categoryId).products);
        } //иначе тост(добавьте продукты)
    }

}
