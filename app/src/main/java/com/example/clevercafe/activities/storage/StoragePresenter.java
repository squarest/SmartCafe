package com.example.clevercafe.activities.storage;

import android.content.Context;

import com.example.clevercafe.DB.IngredientRepository;
import com.example.clevercafe.utils.Units;
import com.example.clevercafe.model.Ingredient;
import com.example.clevercafe.model.IngredientCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 20.03.17.
 */

public class StoragePresenter implements IStoragePresenter {
    private ArrayList<IngredientCategory> categories;
    private IStorageView view;
    IngredientRepository repository;

    public StoragePresenter(StorageView view) {
        this.view = view;
    }

    @Override
    public void viewInit() {

        repository = new IngredientRepository((Context) view);
        categories = repository.getCategories();
        if (categories != null) view.showCategories(categories);

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
        repository.deleteIngredient(categories.get(categoryId).ingredients.remove(productId).id);
        updateCategories();
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
        repository.deleteCategory(categories.get(categoryId).id);
        updateCategories();
    }

    @Override
    public void submitProductFormButClicked(int categoryId, int productId, Ingredient ingredient, boolean editForm) {
        if (editForm) {
            repository.editIngredient(ingredient);
        } else {
            ingredient.categoryId = categories.get(categoryId).id;
            repository.addIngredient(ingredient);
        }
        updateCategories();
    }

    @Override
    public void submitCategoryFormButClicked(int categoryId, String name, boolean editForm) {
        if (editForm) {
            IngredientCategory category = categories.get(categoryId);
            category.name = name;
            repository.editCategory(category);
        } else {
            IngredientCategory category = new IngredientCategory(name, new ArrayList<>());
            repository.addCategory(category);
        }

        updateCategories();
    }

    private void updateCategories() {
        categories.clear();
        categories.addAll(repository.getCategories());
        view.updateCategories(categories);
    }

    private ArrayList<IngredientCategory> fillCategories() {
        ArrayList<IngredientCategory> categories = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
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
