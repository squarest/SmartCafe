package com.paper.smartcafe.main.presentation.ordersfragment;

import com.arellomobile.mvp.InjectViewState;
import com.paper.smartcafe.App;
import com.paper.smartcafe.base.BasePresenter;
import com.paper.smartcafe.entities.Order;
import com.paper.smartcafe.main.domain.IMainInteractor;

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
                }, Throwable::printStackTrace);
        setDisposable(disposable);
    }


    public void orderLongClicked(int orderPosition) {
        getViewState().setOrder(orders.get(orderPosition).id);
    }

}
