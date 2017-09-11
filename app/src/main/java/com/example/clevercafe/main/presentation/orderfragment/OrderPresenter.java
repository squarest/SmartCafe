package com.example.clevercafe.main.presentation.orderfragment;

import com.arellomobile.mvp.InjectViewState;
import com.example.clevercafe.App;
import com.example.clevercafe.base.BasePresenter;
import com.example.clevercafe.entities.Ingredient;
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

    public void viewInit(long orderId) {
        interactor.setOrderActive(true);
        if (orderId == -1) {
            order = new Order(interactor.getCurOrderNumber(), new ArrayList<>());
            order.sum = 0.0;
            getViewState().setOrder(order);
        } else {
            interactor.loadOrder(orderId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(returnedOrder ->
                    {
                        order = returnedOrder;
                        getViewState().setOrder(order);
                    }, Throwable::printStackTrace);
        }

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
            order.costSum = checkCostSum(order);
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

    private double checkCostSum(Order order) {
        double sum = 0;
        for (Product product : order.products) {
            for (Ingredient ingredient : product.ingredients) {
                sum += ingredient.cost * product.getIngredientCount(ingredient.id);
            }
            sum *= order.getProductCount(product.id);
        }
        return sum;
    }

    public void productRemoved(int productPosition) {
        order.removeProduct(productPosition);
        order.sum = checkSum(order);
        getViewState().updateOrder(order);
    }

    public void productLongClicked(int productPosition) {
        long productId = order.products.get(productPosition).id;
        getViewState().showCommentDialog(productId, order.getProductComment(productId));
    }

    public void commentAdded(long productId, String comment) {
        order.setProductComment(productId, comment);
        getViewState().updateOrder(order);
    }
}
