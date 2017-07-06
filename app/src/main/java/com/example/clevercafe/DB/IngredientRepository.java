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
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public IngredientRepository(Context context) {
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void addCategory(IngredientCategory category) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CATEGORY_NAME, category.name);

        sqLiteDatabase.insert(
                DBHelper.INGREDIENT_C_TABLE,
                null,
                values);

    }

    public void addIngredient(Ingredient ingredient) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CATEGORY_ID, ingredient.categoryId);
        values.put(DBHelper.INGREDIENT_NAME, ingredient.name);
        values.put(DBHelper.QUANTITY, ingredient.quantity);
        values.put(DBHelper.UNITS, ingredient.units);

        sqLiteDatabase.insert(
                DBHelper.INGREDIENT_TABLE,
                null,
                values);

    }

    public ArrayList<IngredientCategory> getCategories() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.INGREDIENT_C_TABLE, null, null, null, null, null, DBHelper.CATEGORY_NAME);
        ArrayList<IngredientCategory> categories = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    IngredientCategory ingredientCategory = new IngredientCategory();
                    ingredientCategory.id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.ID)));
                    ingredientCategory.name = cursor.getString(cursor.getColumnIndex(DBHelper.CATEGORY_NAME));
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
        Cursor cursor = sqLiteDatabase.query(DBHelper.INGREDIENT_TABLE, null,
                DBHelper.CATEGORY_ID + " = ?", new String[]{String.valueOf(categoryId)}, null, null, DBHelper.INGREDIENT_NAME);

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    Ingredient ingredient = new Ingredient();
                    ingredient.id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.ID)));
                    ingredient.categoryId = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.CATEGORY_ID)));
                    ingredient.name = cursor.getString(cursor.getColumnIndex(DBHelper.INGREDIENT_NAME));
                    ingredient.quantity = Double.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.QUANTITY)));
                    ingredient.units = cursor.getString(cursor.getColumnIndex(DBHelper.UNITS));
                    ingredients.add(ingredient);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            return null;
        }
        return ingredients;
    }

    public Ingredient getIngredient(int ingredientId) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.INGREDIENT_TABLE, null,
                DBHelper.ID + " = ?", new String[]{String.valueOf(ingredientId)}, null, null, null);
        Ingredient ingredient = new Ingredient();
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                ingredient.id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.ID)));
                ingredient.categoryId = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.CATEGORY_ID)));
                ingredient.name = cursor.getString(cursor.getColumnIndex(DBHelper.INGREDIENT_NAME));
                ingredient.quantity = Double.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.QUANTITY)));
                ingredient.units = cursor.getString(cursor.getColumnIndex(DBHelper.UNITS));
            }
            cursor.close();
        } else {
            return null;
        }
        return ingredient;
    }

    public void editCategory(IngredientCategory category) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.CATEGORY_NAME, category.name);

        sqLiteDatabase.update(
                dbHelper.INGREDIENT_C_TABLE,
                values,
                DBHelper.ID + " = ?",
                new String[]{String.valueOf(category.id)});
    }

    public void deleteCategory(int id) {
        sqLiteDatabase.delete(
                dbHelper.INGREDIENT_C_TABLE,
                DBHelper.ID + " = ?",
                new String[]{String.valueOf(id)});
        deleteIngredients(id);

    }

    public void editIngredient(Ingredient ingredient) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.INGREDIENT_NAME, ingredient.name);
        values.put(DBHelper.QUANTITY, ingredient.quantity);
        values.put(DBHelper.UNITS, ingredient.units);

        sqLiteDatabase.update(
                dbHelper.INGREDIENT_TABLE,
                values,
                DBHelper.ID + " = ?",
                new String[]{String.valueOf(ingredient.id)});
    }

    public void deleteIngredients(int categoryId) {
        sqLiteDatabase.delete(
                dbHelper.INGREDIENT_TABLE,
                DBHelper.CATEGORY_ID + " = ?",
                new String[]{String.valueOf(categoryId)});
    }

    public void deleteIngredient(int id) {
        sqLiteDatabase.delete(
                dbHelper.INGREDIENT_TABLE,
                DBHelper.ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
