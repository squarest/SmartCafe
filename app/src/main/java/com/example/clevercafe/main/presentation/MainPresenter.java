package com.example.clevercafe.main.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.clevercafe.App;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;
import com.example.clevercafe.main.domain.IMainInteractor;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 03.10.16.
 */
@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    //todo: добавлять подписки в список и в basePresenter отписываться

    private ArrayList<ProductCategory> categories = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<Product> currentProducts = new ArrayList<>();
    private Order curOrder;
    private boolean ORDER_IS_ACTIVE = false;

    private MainView mainView = getViewState();

    @Inject
    public IMainInteractor mainInteractor;

    public MainPresenter() {
        App.getMainComponent().inject(this);
    }

    public void viewInit() {
        mainInteractor.loadCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productCategories ->
                {
                    if (productCategories != null && productCategories.size() > 0) {
                        categories = productCategories;
                        mainView.showCategories(categories);
                        updateOrders();
                    }
                }, Throwable::printStackTrace);
    }

    private void updateOrders() {
        mainInteractor.loadOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(returnedOrders ->
                {
                    if (returnedOrders != null) {
                        orders.clear();
                        orders.addAll(returnedOrders);
                        mainView.setOrders(orders);
                    }
                });
    }

    public void itemClicked(boolean categoryOnscreen, int id) {
        if (categoryOnscreen) {
            currentProducts = categories.get(id).products;
            mainView.showProducts(currentProducts);
        } else if (ORDER_IS_ACTIVE) {
            curOrder.addProduct(currentProducts.get(id));
            mainView.updateOrder(curOrder);
        } else {
            mainView.showMessage("Добавьте новый заказ");
        }

    }

    public void addOrderButtonClicked() {
        //при удалении всех заказов номер заказа сбрасывается
        //необходимо где то хранить
        //возможно в репозитории
        // TODO: 26.07.17 fix orderId
        long orderId = 1;
        if (orders.size() > 0)
            orderId = orders.get(orders.size() - 1).id + 1;
        curOrder = new Order(orderId, new ArrayList<>());
        ORDER_IS_ACTIVE = true;
        mainView.setOrder(curOrder);

    }

    private double checkSum(ArrayList<Product> products) {
        double sum = 0;
        for (Product product : products) {
            sum += product.cost * curOrder.getProductCount(product.id);
        }
        return sum;
    }

    public void submitButtonClicked() {
        ORDER_IS_ACTIVE = false;
        if (curOrder != null) {
            curOrder.sum = checkSum(curOrder.products);
            mainInteractor.setOrder(curOrder)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::updateOrders);
        } else mainView.showMessage("Заказ пуст");
    }

    public void cancelButtonClicked() {
        ORDER_IS_ACTIVE = false;
        mainView.setOrders(orders);
    }

    public void itemMoved(int oldPosition, int newPosition) {
        Collections.swap(orders, oldPosition, newPosition);
        mainView.moveOrder(oldPosition, newPosition);
    }

    public void itemRemoved(int position) {
        mainInteractor.removeOrder(orders.get(position))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateOrders);
    }

    public int getOrderSize() {
        return orders.size();
    }

    public void orderSubmitButtonClicked(Order order) {
        //// TODO: 27.07.17 списывать использованные для заказа ингредиенты
        mainInteractor.setCompleteOrder(order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateOrders);
    }

    public void backToCategoryButtonClicked() {
        mainView.showCategories(categories);
    }
}
