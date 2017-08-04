package com.example.clevercafe.menu.presentation.addcategory;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.clevercafe.App;
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
public class AddCategoryPresenter extends MvpPresenter<IAddCategoryFragment> {
    private Disposable categoryDisposable;
    @Inject
    public IMenuInteractor interactor;

    public AddCategoryPresenter() {
        App.getMenuComponent().inject(this);
    }

    @Override
    public void attachView(IAddCategoryFragment view) {
        super.attachView(view);
        categoryDisposable = interactor.categoryEdited()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setCategory, Throwable::printStackTrace);
    }

    @Override
    public void detachView(IAddCategoryFragment view) {
        super.detachView(view);
        categoryDisposable.dispose();
    }

    private void setCategory(long categoryId) {
        interactor.loadCategory(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(category -> getViewState().showEditForm(category),
                        Throwable::printStackTrace);

    }

    public void submitButtonClicked(ProductCategory category) {
        interactor.addCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::hideForm, Throwable::fillInStackTrace);
    }
}
