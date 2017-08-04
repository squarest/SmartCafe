package com.example.clevercafe.menu.presentation.addproduct;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.clevercafe.App;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.menu.domain.IMenuInteractor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 01.08.17.
 */
@InjectViewState
public class AddProductPresenter extends MvpPresenter<IAddProductFragment> {
    Disposable productDisposable;
    @Inject
    public IMenuInteractor interactor;

    public AddProductPresenter() {
        App.getMenuComponent().inject(this);
    }
    public void submitButtonClicked(Product product) {
        interactor.addProduct(product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::hideForm, Throwable::fillInStackTrace);
    }
    @Override
    public void attachView(IAddProductFragment view) {
        super.attachView(view);
        productDisposable = interactor.productEdited()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setProduct, Throwable::printStackTrace);
    }

    @Override
    public void detachView(IAddProductFragment view) {
        super.detachView(view);
        productDisposable.dispose();
    }

    private void setProduct(long productId) {
        interactor.loadProduct(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(product -> getViewState().showEditForm(product),
                        Throwable::printStackTrace);

    }
}
