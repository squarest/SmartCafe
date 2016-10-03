package com.example.clevercafe.main;

import com.example.clevercafe.model.Category;
import com.example.clevercafe.model.Product;

import java.util.ArrayList;

/**
 * Created by Chudofom on 03.10.16.
 */
public class MainPresenter implements IMainPresenter {

    private IMainView mainView;
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void viewInit() {
        categories.clear();
        categories.addAll(fillCategories());
        mainView.showCategories(categories);
    }

    @Override
    public void itemClicked(boolean categoryOnscreen, int id) {
        if (categoryOnscreen) {
            products.clear();
            products.addAll(fillProducts());
            mainView.showProducts(products);
        }
//        else {
//            mainView.addProductToOrder();
//        }

    }

    @Override
    public void backToCategoryButtonClicked() {
//        mainView.showCategories();
    }

    public ArrayList<Product> fillProducts() {
        ArrayList<Product> arrayList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Product product = new Product();
            product.id = i;
            product.name = "Товар №" + i;
            arrayList.add(product);
        }
        return arrayList;
    }

    public ArrayList<Category> fillCategories() {
        ArrayList<Category> arrayList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Category category = new Category();
            category.id = i;
            category.name = "Категория №" + i;
            arrayList.add(category);
        }
        return arrayList;
    }
}
