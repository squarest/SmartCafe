package com.example.clevercafe.main.presentation.ordersfragment;

import com.arellomobile.mvp.InjectViewState;
import com.example.clevercafe.App;
import com.example.clevercafe.base.BasePresenter;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.main.domain.IMainInteractor;

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
                .subscribe(this::setOrders, Throwable::printStackTrace);
        setDisposable(disposable);
    }
}
