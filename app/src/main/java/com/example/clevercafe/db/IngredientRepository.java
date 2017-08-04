package com.example.clevercafe.db;

import com.example.clevercafe.db.dao.DatabaseDao;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.IngredientCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 15.07.16.
 */
public class IngredientRepository {

    public DatabaseDao databaseDao;

    public IngredientRepository(DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
    }

    //insert
    public void addCategory(IngredientCategory category) {
        databaseDao.insertIngredientCategory(category);

    }

    public void addIngredient(Ingredient ingredient) {
        databaseDao.insertIngredient(ingredient);

    }

    //get
    public ArrayList<IngredientCategory> getCategories() {
        ArrayList<IngredientCategory> categories = (ArrayList<IngredientCategory>) databaseDao.getIngredientCategories();
        for (IngredientCategory category : categories) {
            category.ingredients = getIngredients(category.id);
        }
        return categories;
    }

    private ArrayList<Ingredient> getIngredients(long categoryId) {
        return (ArrayList<Ingredient>) databaseDao.getIngredients(categoryId);

    }

    //delete
    public void deleteCategory(IngredientCategory category) {
        databaseDao.deleteIngredientCategory(category);
        if (category.ingredients != null && category.ingredients.size() > 0) {
            deleteIngredients(category.id);
        }

    }

    private void deleteIngredients(long categoryId) {
        databaseDao.deleteIngredients(categoryId);
    }

    public void deleteIngredient(Ingredient ingredient) {
        databaseDao.deleteIngredient(ingredient);
    }
}
