package com.example.clevercafe.main.presentation.ordersfragment;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.clevercafe.App;
import com.example.clevercafe.base.BasePresenter;
import com.example.clevercafe.entities.CompleteOrder;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.main.domain.IMainInteractor;
import com.example.clevercafe.utils.dateTime.DateTimeUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 10.08.17.
 */
@InjectViewState
public class OrdersPresenter extends BasePresenter<IOrdersFragment> {
    @Inject
    public IMainInteractor interactor;

    private ArrayList<Order> orders = new ArrayList<>();

    public OrdersPresenter() {
        App.getMainComponent().inject(this);
    }

    public void viewInit() {
        interactor.setOrderActive(false);
        setOrders();
    }

    private void setOrders() {
        Disposable disposable = interactor.loadOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(returnedOrders ->
                {
                    if (returnedOrders != null) {
                        orders.clear();
                        orders.addAll(returnedOrders);
                        getViewState().setOrders(orders);
                    }
                }, Throwable::printStackTrace);
        setDisposable(disposable);
    }

    public void orderRemoved(int position) {
        Disposable disposable = interactor.removeOrder(orders.get(position))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setOrders, Throwable::printStackTrace);
        setDisposable(disposable);

    }

    public void orderSubmitButtonClicked(Order order) {
        Disposable disposable = interactor.setCompleteOrder(order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    setOrders();
                    showCompleteOrders();
                }, Throwable::printStackTrace);
        setDisposable(disposable);
    }

    private void showCompleteOrders() {
        Disposable disposable = interactor.loadCompleteOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(returnedOrders ->
                {
                    if (returnedOrders != null) {
                        logOrders(returnedOrders);
                    }
                }, Throwable::printStackTrace);
        setDisposable(disposable);
    }

    public void orderLongClicked(int orderPosition) {
        getViewState().setOrder(orders.get(orderPosition).id);
    }

    private void logOrders(ArrayList<CompleteOrder> orders) {
        for (CompleteOrder order : orders) {
            Log.d("orders", order.id + " datetime" + DateTimeUtil.dateTimeToString(order.dateTime) + " sum " + order.sum + " costSum" + order.costSum);
        }
    }
}
