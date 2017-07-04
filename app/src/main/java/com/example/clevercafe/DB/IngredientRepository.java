package com.example.clevercafe.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.clevercafe.model.Ingredient;
import com.example.clevercafe.model.IngredientCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 15.07.16.
 */
public class IngredientRepository {
    private IngredientDBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public IngredientRepository(Context context) {
        dbHelper = new IngredientDBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void addCategory(IngredientCategory category) {
        ContentValues values = new ContentValues();
        values.put(IngredientDBHelper.CATEGORY_NAME, category.name);

        long newRowId;
        newRowId = sqLiteDatabase.insert(
                IngredientDBHelper.INGREDIENT_C_TABLE,
                null,
                values);

    }

    public void addIngredient(Ingredient ingredient) {
        ContentValues values = new ContentValues();
        values.put(IngredientDBHelper.CATEGORY_ID, ingredient.categoryId);
        values.put(IngredientDBHelper.INGREDIENT_NAME, ingredient.name);
        values.put(IngredientDBHelper.QUANTITY, ingredient.quantity);
        values.put(IngredientDBHelper.UNITS, ingredient.units);

        long newRowId;
        newRowId = sqLiteDatabase.insert(
                IngredientDBHelper.INGREDIENT_TABLE,
                null,
                values);

    }

    public ArrayList<IngredientCategory> getCategories() {
        Cursor cursor = sqLiteDatabase.query(IngredientDBHelper.INGREDIENT_C_TABLE, null, null, null, null, null, IngredientDBHelper.CATEGORY_NAME);
        ArrayList<IngredientCategory> categories = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    IngredientCategory ingredientCategory = new IngredientCategory();
                    ingredientCategory.id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(IngredientDBHelper.ID)));
                    ingredientCategory.name = cursor.getString(cursor.getColumnIndex(IngredientDBHelper.CATEGORY_NAME));
                    ingredientCategory.ingredients = getIngredients(ingredientCategory.id);
                    categories.add(ingredientCategory);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            return null;
        }
        return categories;
    }

    private ArrayList<Ingredient> getIngredients(int categoryId) {
        Cursor cursor = sqLiteDatabase.query(IngredientDBHelper.INGREDIENT_TABLE, null,
                IngredientDBHelper.CATEGORY_ID + " = ?", new String[]{String.valueOf(categoryId)}, null, null, IngredientDBHelper.INGREDIENT_NAME);

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    Ingredient ingredient = new Ingredient();
                    ingredient.id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(IngredientDBHelper.ID)));
                    ingredient.categoryId = Integer.valueOf(cursor.getString(cursor.getColumnIndex(IngredientDBHelper.CATEGORY_ID)));
                    ingredient.name = cursor.getString(cursor.getColumnIndex(IngredientDBHelper.INGREDIENT_NAME));
                    ingredient.quantity = Double.valueOf(cursor.getString(cursor.getColumnIndex(IngredientDBHelper.QUANTITY)));
                    ingredient.units = cursor.getString(cursor.getColumnIndex(IngredientDBHelper.UNITS));
                    ingredients.add(ingredient);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            return null;
        }
        return ingredients;
    }

    public void editCategory(IngredientCategory category) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.CATEGORY_NAME, category.name);

        int count = sqLiteDatabase.update(
                dbHelper.INGREDIENT_C_TABLE,
                values,
                IngredientDBHelper.ID + " = ?",
                new String[]{String.valueOf(category.id)});
    }

    public void deleteCategory(int id) {
        sqLiteDatabase.delete(
                dbHelper.INGREDIENT_C_TABLE,
                IngredientDBHelper.ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void editIngredient(Ingredient ingredient) {
        ContentValues values = new ContentValues();
        values.put(IngredientDBHelper.INGREDIENT_NAME, ingredient.name);
        values.put(IngredientDBHelper.QUANTITY, ingredient.quantity);
        values.put(IngredientDBHelper.UNITS, ingredient.units);

        int count = sqLiteDatabase.update(
                dbHelper.INGREDIENT_TABLE,
                values,
                IngredientDBHelper.ID + " = ?",
                new String[]{String.valueOf(ingredient.id)});
    }

    public void deleteIngredient(int id) {
        sqLiteDatabase.delete(
                dbHelper.INGREDIENT_TABLE,
                IngredientDBHelper.ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
