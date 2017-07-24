package com.example.clevercafe.main.presentation;

import android.content.Context;

import com.example.clevercafe.db.OrderQueueRepo;
import com.example.clevercafe.db.OrdersRepository;
import com.example.clevercafe.db.ProductRepository;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Chudofom on 03.10.16.
 */
public class MainPresenter implements IMainPresenter {

    private IMainView mainView;
    private ArrayList<ProductCategory> categories = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<Product> currentProducts = new ArrayList<>();
    private Order curOrder;
    private boolean ORDER_IS_ACTIVE = false;
    private static int LAST_ORDER_ID = 0;

    private OrderQueueRepo orderQueueRepo;
    private OrdersRepository ordersRepository;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void viewInit() {
        ProductRepository repository = new ProductRepository((Context) mainView);
        orderQueueRepo = new OrderQueueRepo((Context) mainView);
        ordersRepository = new OrdersRepository((Context) mainView);
        categories = repository.getCategories();
        if (categories != null) mainView.showCategories(categories);
        updateOrders();

    }

    private void updateOrders() {
        orders = orderQueueRepo.getOrders();
        if (orders != null) mainView.setOrders(orders);
    }

    @Override
    public void itemClicked(boolean categoryOnscreen, int categoryId) {
        if (categoryOnscreen) {
            currentProducts = categories.get(categoryId).products;
            mainView.showProducts(currentProducts);
        } else if (ORDER_IS_ACTIVE) {
            curOrder.products.add(currentProducts.get(categoryId));
            curOrder.sum = checkSum(curOrder.products);
            mainView.updateOrder(curOrder);
        } else {
            mainView.showMessage("Добавьте новый заказ");
        }

    }

    @Override
    public void addOrderButtonClicked() {
        curOrder = new Order((int) orderQueueRepo.LAST_ORDER_ID + 1, new ArrayList<>());
        ORDER_IS_ACTIVE = true;
        mainView.setOrder(curOrder);

    }

    private double checkSum(ArrayList<Product> products) {
        double sum = 0;
        for (Product product : products) {
            sum += product.cost;
        }
        return sum;
    }

    @Override
    public void submitButtonClicked() {
        ORDER_IS_ACTIVE = false;
        orderQueueRepo.addOrder(curOrder);
        updateOrders();
    }

    @Override
    public void cancelButtonClicked() {
        ORDER_IS_ACTIVE = false;
        mainView.setOrders(orders);
    }

    @Override
    public void itemMoved(int oldPosition, int newPosition) {
        Collections.swap(orders, oldPosition, newPosition);
        mainView.moveOrder(oldPosition, newPosition);
    }

    @Override
    public void itemRemoved(int position) {
        orderQueueRepo.deleteOrder(orders.get(position));
        updateOrders();
    }

    @Override
    public int getOrderSize() {
        return orders.size();
    }

    @Override
    public void orderSubmitButtonClicked(Order order) {
        //// TODO: захуярить прогрес бар
        ordersRepository.addOrder(order);
        orderQueueRepo.deleteOrder(order);
        updateOrders();
    }

    @Override
    public void backToCategoryButtonClicked() {
        mainView.showCategories(categories);
    }
}
