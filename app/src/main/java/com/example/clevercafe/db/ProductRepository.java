package com.example.clevercafe.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 15.07.16.
 */
public class ProductRepository {
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public ProductRepository(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void addCategory(ProductCategory category) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CATEGORY_NAME, category.name);

        sqLiteDatabase.insert(
                DBHelper.PRODUCT_C_TABLE,
                null,
                values);

    }

    public void addProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CATEGORY_ID, product.categoryId);
        values.put(DBHelper.PRODUCT_NAME, product.name);
        values.put(DBHelper.PRODUCT_COST, product.cost);
        values.put(DBHelper.UNITS, product.units);

        long newRowID = sqLiteDatabase.insert(
                DBHelper.PRODUCT_TABLE,
                null,
                values);

        if (product.ingredients.size() > 0) {
            product.id = (int) newRowID;
            addIngredients(product);
        }

    }

    private void addIngredients(Product product) {
        for (int i = 0; i < product.ingredients.size(); i++) {
            Ingredient ingredient = product.ingredients.get(i);
            ContentValues values = new ContentValues();
            values.put(DBHelper.PRODUCT_ID, product.id);//ERROR
            values.put(DBHelper.INGREDIENT_ID, ingredient.id);
            values.put(DBHelper.QUANTITY, ingredient.quantity);

            sqLiteDatabase.insert(
                    DBHelper.PRODUCT_CNS_TABLE,
                    null,
                    values);
        }
    }

    public ArrayList<ProductCategory> getCategories() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.PRODUCT_C_TABLE, null, null, null, null, null, null);
        ArrayList<ProductCategory> categories = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    ProductCategory productCategory = new ProductCategory();
                    productCategory.id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.ID)));
                    productCategory.name = cursor.getString(cursor.getColumnIndex(DBHelper.CATEGORY_NAME));
                    productCategory.products = getProducts(productCategory.id);
                    categories.add(productCategory);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            return null;
        }
        return categories;
    }

    private ArrayList<Product> getProducts(int categoryId) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.PRODUCT_TABLE, null,
                DBHelper.CATEGORY_ID + " = ?", new String[]{String.valueOf(categoryId)}, null, null, null);

        ArrayList<Product> products = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    Product product = new Product();
                    product.id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.ID)));
                    product.categoryId = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.CATEGORY_ID)));
                    product.name = cursor.getString(cursor.getColumnIndex(DBHelper.PRODUCT_NAME));
                    product.cost = Double.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.PRODUCT_COST)));
                    product.units = cursor.getString(cursor.getColumnIndex(DBHelper.UNITS));
                    product.ingredients = getIngredients(product.id);
                    products.add(product);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            return null;
        }
        return products;
    }

    public ArrayList<Ingredient> getIngredients(int productId) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.PRODUCT_CNS_TABLE, null,
                DBHelper.PRODUCT_ID + " = ?", new String[]{String.valueOf(productId)}, null, null, null);
        IngredientRepository ingredientRepository = new IngredientRepository(context);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    int ingredientId = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.INGREDIENT_ID)));
                    Ingredient ingredient = ingredientRepository.getIngredient(ingredientId);
                    ingredient.quantity = Double.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.QUANTITY)));
                    ingredients.add(ingredient);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            return null;
        }
        return ingredients;
    }

    public Product getProduct(int productId) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.PRODUCT_TABLE, null,
                DBHelper.ID + " = ?", new String[]{String.valueOf(productId)}, null, null, null);
        Product product = new Product();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                product.id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.ID)));
                product.categoryId = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.CATEGORY_ID)));
                product.name = cursor.getString(cursor.getColumnIndex(DBHelper.PRODUCT_NAME));
                product.cost = Double.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.PRODUCT_COST)));
                product.units = cursor.getString(cursor.getColumnIndex(DBHelper.UNITS));
                product.ingredients =getIngredients(product.id);
            }
            cursor.close();
        } else {
            return null;
        }
        return product;
    }
    public void editCategory(ProductCategory category) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.CATEGORY_NAME, category.name);

        sqLiteDatabase.update(
                dbHelper.PRODUCT_C_TABLE,
                values,
                DBHelper.ID + " = ?",
                new String[]{String.valueOf(category.id)});
    }


    public void editProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.PRODUCT_NAME, product.name);
        values.put(DBHelper.PRODUCT_COST, product.cost);
        values.put(DBHelper.UNITS, product.units);
        if (product.ingredients != null) {
            editIngredients(product);
        }
        sqLiteDatabase.update(
                DBHelper.PRODUCT_TABLE,
                values,
                DBHelper.ID + " = ?",
                new String[]{String.valueOf(product.id)});
    }

    public void deleteCategory(ProductCategory category) {
        sqLiteDatabase.delete(
                DBHelper.PRODUCT_C_TABLE,
                DBHelper.ID + " = ?",
                new String[]{String.valueOf(category.id)});
        if (category.products != null && category.products.size() > 0) {
            deleteProducts(category.id);
            for (int i = 0; i < category.products.size(); i++) {
                Product product = category.products.get(i);
                if (product.ingredients != null && product.ingredients.size() > 0) {
                    deleteIngredients(product.id);
                }
            }
        }

    }

    public void deleteProduct(Product product) {
        sqLiteDatabase.delete(
                dbHelper.PRODUCT_TABLE,
                DBHelper.ID + " = ?",
                new String[]{String.valueOf(product.id)});
        if (product.ingredients != null && product.ingredients.size() > 0) {
            deleteIngredients(product.id);
        }
    }

    private void deleteProducts(int categoryId) {
        sqLiteDatabase.delete(
                dbHelper.PRODUCT_TABLE,
                DBHelper.CATEGORY_ID + " = ?",
                new String[]{String.valueOf(categoryId)});
    }

    public void deleteIngredients(int productId) {
        sqLiteDatabase.delete(
                dbHelper.PRODUCT_CNS_TABLE,
                DBHelper.PRODUCT_ID + " = ?",
                new String[]{String.valueOf(productId)});
    }

    public void editIngredients(Product product) {
        sqLiteDatabase.delete(
                dbHelper.PRODUCT_CNS_TABLE,
                DBHelper.PRODUCT_ID + " = ?",
                new String[]{String.valueOf(product.id)});
        if (product.ingredients.size() > 0) addIngredients(product);
    }
}
