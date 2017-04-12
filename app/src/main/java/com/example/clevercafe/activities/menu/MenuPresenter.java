package com.example.clevercafe.activities.menu;

import com.example.clevercafe.model.Product;
import com.example.clevercafe.model.ProductCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 20.03.17.
 */

public class MenuPresenter implements IMenuPresenter {
    IMenuView menuView;
    ArrayList<ProductCategory> categories = new ArrayList<>();

    public MenuPresenter(IMenuView menuView) {

        this.menuView = menuView;
    }

    @Override
    public void viewInit() {
        categories = fillCategories();
        menuView.showCategories(categories);
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
        categories.get(categoryId).products.remove(productId);
        menuView.updateMenu(categories);

    }

    @Override
    public void addIngredientsButClicked() {
        menuView.showStorage();

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
        categories.remove(categoryId);
        menuView.updateMenu(categories);
    }

    @Override
    public void submitProductFormButClicked(int categoryId, int productId, Product product, boolean editForm) {
        if (editForm) {
            categories.get(categoryId).products.set(productId, product);
        } else categories.get(categoryId).products.add(product);
        menuView.updateMenu(categories);
    }

    @Override
    public void submitCategoryFormButClicked(int categoryId, String name, boolean editForm) {
        if (editForm) {
            categories.get(categoryId).name = name;
        } else {
            ProductCategory category = new ProductCategory(name, new ArrayList<>());
            categories.add(category);
        }
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
        }
    }

    private ArrayList<Product> fillProducts() {
        ArrayList<Product> arrayList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Product product = new Product();
            product.name = "Товар №" + i;
            product.cost = 100.00;
            product.quantity = 1.0;
            arrayList.add(product);
        }
        return arrayList;
    }

    private ArrayList<ProductCategory> fillCategories() {
        ArrayList<ProductCategory> arrayList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            ProductCategory category = new ProductCategory();
            category.name = "Категория №" + i;
            category.products = fillProducts();
            arrayList.add(category);
        }
        return arrayList;
    }
}
