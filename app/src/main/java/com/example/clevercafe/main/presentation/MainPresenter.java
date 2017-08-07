package com.example.clevercafe.main.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.example.clevercafe.App;
import com.example.clevercafe.base.BasePresenter;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;
import com.example.clevercafe.main.domain.IMainInteractor;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 03.10.16.
 */
@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

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
        Disposable disposable = mainInteractor.loadCategories()
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
        setDisposable(disposable);
    }

    public void updateOrders() {
        Disposable disposable = mainInteractor.loadOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(returnedOrders ->
                {
                    if (returnedOrders != null) {
                        orders.clear();
                        orders.addAll(returnedOrders);
                        mainView.setOrders(orders);
                    }
                }, Throwable::printStackTrace);
        setDisposable(disposable);
    }

    public void ingredientsCountChanged(Product product) {
        Disposable disposable = mainInteractor.checkIngredients(product, curOrder.getProductCount(product.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, throwable ->
                {
                    // TODO: 06.08.17 показывать диалог с названием недостающего ингредиента и спрашивать "все равно продолжить?"
                    // TODO: 06.08.17 если пользователь нажал нет то удалять продукт


                    mainView.showMessage("Недостаточно ингредиентов на складе");
                });
        setDisposable(disposable);
    }

    public void itemClicked(boolean categoryOnscreen, int id) {
        if (categoryOnscreen) {
            if (categories.get(id).products != null && categories.get(id).products.size() > 0) {
                currentProducts = categories.get(id).products;
                mainView.showProducts(currentProducts);
            } else {
                mainView.showMessage("Категория не содержит продуктов");
            }
        } else if (ORDER_IS_ACTIVE) {
            curOrder.addProduct(currentProducts.get(id));
            mainView.updateOrder(curOrder);
            if (curOrder.products.size() > 0)
                mainView.showButtonPanel();


        } else {
            mainView.showMessage("Добавьте новый заказ");
        }

    }

    public void addOrderButtonClicked() {
        curOrder = new Order(mainInteractor.getCurOrderId() + 1, new ArrayList<>());
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
            Disposable disposable = mainInteractor.setOrder(curOrder)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::updateOrders, Throwable::printStackTrace);
            setDisposable(disposable);
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
        Disposable disposable = mainInteractor.removeOrder(orders.get(position))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateOrders, Throwable::printStackTrace);
        setDisposable(disposable);
    }

    public int getOrderSize() {
        return orders.size();
    }

    public void orderSubmitButtonClicked(Order order) {
        Disposable disposable = mainInteractor.setCompleteOrder(order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateOrders, Throwable::printStackTrace);
        setDisposable(disposable);
    }

    public void backToCategoryButtonClicked() {
        mainView.showCategories(categories);
    }
}
