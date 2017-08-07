package com.example.clevercafe.menu.presentation.addcategory;

import com.arellomobile.mvp.InjectViewState;
import com.example.clevercafe.App;
import com.example.clevercafe.base.BasePresenter;
import com.example.clevercafe.entities.ProductCategory;
import com.example.clevercafe.menu.domain.IMenuInteractor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 01.08.17.
 */
@InjectViewState
public class AddCategoryPresenter extends BasePresenter<IAddCategoryFragment> {
    @Inject
    public IMenuInteractor interactor;

    public AddCategoryPresenter() {
        App.getMenuComponent().inject(this);
    }

    @Override
    public void attachView(IAddCategoryFragment view) {
        super.attachView(view);
        Disposable disposable = interactor.categoryEdited()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setCategory, Throwable::printStackTrace);
        setDisposable(disposable);
    }

    private void setCategory(long categoryId) {
        Disposable disposable = interactor.loadCategory(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(category -> getViewState().showEditForm(category),
                        Throwable::printStackTrace);
        setDisposable(disposable);

    }

    public void submitButtonClicked(ProductCategory category) {
        Disposable disposable = interactor.addCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::hideForm, Throwable::fillInStackTrace);
        setDisposable(disposable);
    }
}
