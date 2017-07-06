package com.example.clevercafe.activities.menu;

import android.content.Context;

import com.example.clevercafe.DB.ProductRepository;
import com.example.clevercafe.model.Ingredient;
import com.example.clevercafe.model.Product;
import com.example.clevercafe.model.ProductCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 20.03.17.
 */

public class MenuPresenter implements IMenuPresenter {
    IMenuView menuView;
    ArrayList<ProductCategory> categories = new ArrayList<>();
    ProductRepository repository;

    public MenuPresenter(IMenuView menuView) {

        this.menuView = menuView;
    }

    @Override
    public void viewInit() {
        repository = new ProductRepository((Context) menuView);
        updateCategories();
        menuView.showCategories(categories);
    }

    private void updateCategories() {
        categories.clear();
        categories.addAll(repository.getCategories());
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
        repository.deleteProduct(categories.get(categoryId).products.get(productId));
        updateCategories();
        menuView.updateMenu(categories);
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
        repository.deleteCategory(categories.get(categoryId));
        updateCategories();
        menuView.removeCategory(categories, categoryId);
    }

    @Override
    public void submitProductFormButClicked(int categoryId, int productId, Product product, boolean editForm) {
        product.categoryId = categories.get(categoryId).id;
        if (editForm) {
            repository.editProduct(product);
        } else {
            repository.addProduct(product);
        }
        updateCategories();
        menuView.updateMenu(categories);
    }

    @Override
    public void submitCategoryFormButClicked(int categoryId, String name, boolean editForm) {
        ProductCategory category;
        if (editForm) {
            category = categories.get(categoryId);
            category.name = name;
            repository.editCategory(category);
        } else {
            category = new ProductCategory(name, new ArrayList<>());
            repository.addCategory(category);
        }
        updateCategories();
        menuView.updateMenu(categories);
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
