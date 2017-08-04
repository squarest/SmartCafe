package com.example.clevercafe.menu.presentation.products;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.clevercafe.App;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.menu.domain.IMenuInteractor;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 31.07.17.
 */
@InjectViewState
public class ProductsPresenter extends MvpPresenter<IProductsFragment> {

    private ArrayList<Product> products = new ArrayList<>();
    @Inject
    public IMenuInteractor interactor;

    private Disposable productsDisposable;

    public ProductsPresenter() {
        App.getMenuComponent().inject(this);
    }


    @Override
    public void attachView(IProductsFragment view) {
        super.attachView(view);
        productsDisposable = interactor.productsUpdates()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setProducts, Throwable::printStackTrace);
    }

    @Override
    public void detachView(IProductsFragment view) {
        super.detachView(view);
        productsDisposable.dispose();
    }

    public void productsInit(long categoryId) {
        setProducts(categoryId);
    }

    private void setProducts(long categoryId) {
        interactor.loadProducts(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(returnedProducts ->
                {
                    products.clear();
                    products.addAll(returnedProducts);
                    getViewState().showProducts(products);
                }, Throwable::fillInStackTrace);
    }
    public void editProductButClicked(int productPosition) {
        interactor.editProduct(products.get(productPosition).id);
    }

    public void deleteProductButClicked(int productPosition) {
        interactor.deleteProduct(products.get(productPosition))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
