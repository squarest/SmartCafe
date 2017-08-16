package com.example.clevercafe.main.presentation.orderfragment;

import com.arellomobile.mvp.InjectViewState;
import com.example.clevercafe.App;
import com.example.clevercafe.base.BasePresenter;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.Product;
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
public class OrderPresenter extends BasePresenter<IOrderFragment> {

    @Inject
    public IMainInteractor interactor;
    private Order order;

    public OrderPresenter() {
        App.getMainComponent().inject(this);
    }

    public void viewInit() {
        interactor.setOrderActive(true);
        order = new Order(interactor.getCurOrderNumber(), new ArrayList<>());
        if (order.sum == null) order.sum = 0.0;
        getViewState().setOrder(order);
    }

    @Override
    public void attachView(IOrderFragment view) {
        super.attachView(view);
        Disposable disposable = interactor.productSelection()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(product -> {
                    order.addProduct(product);
                    order.sum = checkSum(order);
                    getViewState().updateOrder(order);
                });
        setDisposable(disposable);
    }

    public void ingredientsCountChanged(Product product) {
        Disposable disposable = interactor.checkIngredients(product, order.getProductCount(product.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(maxProductCount -> {
                    order.setProductCount(product.id, maxProductCount);
                    order.sum = checkSum(order);
                    getViewState().updateOrder(order);
                    getViewState().showOrderAlertDialog(product.name, maxProductCount);
                }, Throwable::printStackTrace);
        setDisposable(disposable);
    }

    public void submitButtonClicked() {
        if (order.products.size() > 0) {
            Disposable disposable = interactor.setOrder(order)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getViewState()::showOrders, Throwable::printStackTrace);
            setDisposable(disposable);
        } else getViewState().showMessage("Заказ пуст");
    }

    private double checkSum(Order order) {
        double sum = 0;
        for (Product product : order.products) {
            sum += product.cost * order.getProductCount(product.id);
        }
        return sum;
    }
}
