package com.example.clevercafe.main;

import com.example.clevercafe.model.Category;
import com.example.clevercafe.model.Order;
import com.example.clevercafe.model.Product;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Chudofom on 03.10.16.
 */
public class MainPresenter implements IMainPresenter {

    private IMainView mainView;
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private boolean ORDER_IS_ACTIVE = false;
    private static int LAST_ORDER_ID = 0;

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
        } else if (ORDER_IS_ACTIVE) {
            orders.get(0).products.add(fillProducts().get(id));
            orders.get(0).sum = checkSum(orders.get(0).products);
            mainView.showOrders(orders);
        } else {
            mainView.showMessage("Добавьте новый заказ");
        }

    }

    private double checkSum(ArrayList<Product> products) {
        double sum = 0;
        for (Product product : products) {
            sum += product.cost;
        }
        return sum;
    }

    @Override
    public void addOrderButtonClicked() {
        ORDER_IS_ACTIVE = true;
        LAST_ORDER_ID++;
        orders.add(0, new Order(LAST_ORDER_ID, new ArrayList<>(), 0));
        mainView.showOrders(orders);

    }

    @Override
    public void itemMoved(int oldPosition, int newPosition) {
        Collections.swap(orders, oldPosition, newPosition);
        mainView.moveOrder(oldPosition, newPosition);
    }

    @Override
    public void itemRemoved(int position) {
        orders.remove(position);
        if (orders.size() == 0) ORDER_IS_ACTIVE = false;
        mainView.removeOrder(position);
    }

    @Override
    public int getOrderSize() {
        return orders.size();
    }

    @Override
    public void orderSubmitButtonClicked() {
        ORDER_IS_ACTIVE = false;
    }

    @Override
    public void backToCategoryButtonClicked() {
        mainView.showCategories(fillCategories());
    }

    private ArrayList<Product> fillProducts() {
        ArrayList<Product> arrayList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Product product = new Product();
            product.id = i;
            product.name = "Товар №" + i;
            product.cost = 100.00;
            product.quantity = 1.0;
            arrayList.add(product);
        }
        return arrayList;
    }

    private ArrayList<Category> fillCategories() {
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
