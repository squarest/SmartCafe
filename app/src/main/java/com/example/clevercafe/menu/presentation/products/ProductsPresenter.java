package com.example.clevercafe.menu.presentation.products;

import com.arellomobile.mvp.InjectViewState;
import com.example.clevercafe.App;
import com.example.clevercafe.base.BasePresenter;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.main.domain.IMainInteractor;
import com.example.clevercafe.menu.domain.IMenuInteractor;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 31.07.17.
 */
@InjectViewState
public class ProductsPresenter extends BasePresenter<IProductsFragment> {

    private ArrayList<Product> products = new ArrayList<>();
    @Inject
    public IMenuInteractor menuInteractor;

    @Inject
    public IMainInteractor mainInteractor;


    public ProductsPresenter() {
        App.getMainComponent().inject(this);
    }


    @Override
    public void attachView(IProductsFragment view) {
        super.attachView(view);
        menuInteractor.productsUpdates()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setProducts, Throwable::printStackTrace);
    }


    public void productClicked(int productPosition) {
        if (mainInteractor.isOrderActive()) {
            menuInteractor.loadProduct(products.get(productPosition).id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(product -> {
                        mainInteractor.productSelected(product);
                    }, Throwable::printStackTrace);


        }
    }

    public void productsInit(long categoryId) {
        setProducts(categoryId);
    }

    private void setProducts(long categoryId) {
        menuInteractor.loadCategory(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(returnedCategory ->
                {
                    products.clear();
                    products.addAll(returnedCategory.products);
                    getViewState().showProducts(returnedCategory);
                }, Throwable::fillInStackTrace);
    }

    public void editProductButClicked(int productPosition) {
        menuInteractor.editProduct(products.get(productPosition).id);
    }

    public void deleteProductButClicked(int productPosition) {
        menuInteractor.deleteProduct(products.get(productPosition))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().showWarningDialog("Удаление невозможно", throwable.getMessage());
                });
    }

}
