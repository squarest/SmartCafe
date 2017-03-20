package com.example.clevercafe.activities.storage;

import com.example.clevercafe.Units;
import com.example.clevercafe.model.Ingredient;
import com.example.clevercafe.model.IngredientCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 20.03.17.
 */

public class StoragePresenter implements IStoragePresenter {
    private ArrayList<IngredientCategory> categories;
    private IStorageView view;

    public StoragePresenter(StorageView view) {
        this.view = view;
    }

    @Override
    public void viewInit() {
        categories = fillCategories();
        view.showCategories(categories);

    }

    @Override
    public void addProductButClicked() {
        view.createAddProductForm(-1, -1, null, false);
    }

    @Override
    public void editProductButClicked(int categoryId, int productId) {
        Ingredient ingredient = categories.get(categoryId).ingredients.get(productId);
        view.createAddProductForm(categoryId, productId, ingredient, true);
    }

    @Override
    public void deleteProductButClicked(int categoryId, int productId) {
        categories.get(categoryId).ingredients.remove(productId);
        view.updateCategories(categories);
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
        categories.remove(categoryId);
        view.updateCategories(categories);
    }

    @Override
    public void submitProductFormButClicked(int categoryId, int productId, Ingredient ingredient, boolean editForm) {
        if (editForm) {
            categories.get(categoryId).ingredients.set(productId, ingredient);
        } else categories.get(categoryId).ingredients.add(ingredient);
        view.updateCategories(categories);
    }

    @Override
    public void submitCategoryFormButClicked(int categoryId, String name, boolean editForm) {
        if (editForm) {
            categories.get(categoryId).name = name;
        } else {
            IngredientCategory category = new IngredientCategory(name, new ArrayList<>());
            categories.add(category);
        }
        view.updateCategories(categories);
    }

    private ArrayList<IngredientCategory> fillCategories() {
        ArrayList<IngredientCategory> categories = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            IngredientCategory category = new IngredientCategory("Категория " + i, fillIngredients());
            categories.add(category);
        }
        return categories;
    }

    private ArrayList<Ingredient> fillIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Ingredient ingredient = new Ingredient("Продукт " + i, 1, Units.count);
            ingredients.add(ingredient);
        }
        return ingredients;
    }
}
