package com.example.clevercafe.db;

import com.example.clevercafe.App;
import com.example.clevercafe.db.dao.DatabaseDao;
import com.example.clevercafe.db.entities.ProductIngredient;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 15.07.16.
 */
public class ProductRepository {
    DatabaseDao databaseDao;

    public ProductRepository() {
        databaseDao = App.database.databaseDao();

    }

    //insert
    public void addCategory(ProductCategory category) {
        databaseDao.insertProductCategory(category);


    }

    public void addProduct(Product product) {
        long newRowID = databaseDao.insertProduct(product);
        if (product.ingredients != null && product.ingredients.size() > 0) {
            product.id = (int) newRowID;
            addIngredients(product);
        }

    }

    private void addIngredients(Product product) {
        for (int i = 0; i < product.ingredients.size(); i++) {
            ProductIngredient productIngredient = new ProductIngredient(product.id, product.ingredients.get(i).id,
                    product.getIngredientCount(product.ingredients.get(i).id));
            databaseDao.insertProductIngredient(productIngredient);

        }
    }

    //get
    public ArrayList<ProductCategory> getCategories() {
        ArrayList<ProductCategory> categories = (ArrayList<ProductCategory>) databaseDao.getProductCategories();
        for (ProductCategory category : categories) {
            category.products = getProducts(category.id);
        }
        return categories;
    }

    private ArrayList<Product> getProducts(long categoryId) {
        ArrayList<Product> products = (ArrayList<Product>) databaseDao.getProducts(categoryId);
        if (products.size() > 0) {
            for (Product product : products) {
                //findIngredientsById
                product.ingredients = getIngredients(product);
            }
        }
        return products;

    }

    private ArrayList<Ingredient> getIngredients(Product product) {
        ArrayList<ProductIngredient> productIngredients = (ArrayList<ProductIngredient>) databaseDao.getProductIngredients(product.id);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        if (productIngredients != null && productIngredients.size() > 0) {
            for (ProductIngredient productIngredient : productIngredients) {
                ingredients.add(databaseDao.getIngredient(productIngredient.ingredientId));
                product.setIngredientCount(productIngredient.ingredientId, productIngredient.quantity);
            }
        }
        return ingredients;
    }

    //edit
    public void editCategory(ProductCategory category) {
        databaseDao.updateProductCategory(category);
    }


    public void editProduct(Product product) {
        databaseDao.updateProduct(product);
        if (product.ingredients != null && product.ingredients.size() > 0) {
            editIngredients(product);
        }
    }

    public void editIngredients(Product product) {
        deleteIngredients(product.id);
        addIngredients(product);
    }

    //delete
    public void deleteCategory(ProductCategory category) {
        databaseDao.deleteProductCategory(category);
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
        databaseDao.deleteProduct(product);
        if (product.ingredients != null && product.ingredients.size() > 0) {
            deleteIngredients(product.id);
        }
    }

    private void deleteProducts(long categoryId) {
        databaseDao.deleteProducts(categoryId);

    }

    public void deleteIngredients(long productId) {
        databaseDao.deleteProductIngredients(productId);

    }
}
