package com.paper.smartcafe.menu.presentation.addcategory;

import com.arellomobile.mvp.InjectViewState;
import com.paper.smartcafe.App;
import com.paper.smartcafe.base.BasePresenter;
import com.paper.smartcafe.entities.ProductCategory;
import com.paper.smartcafe.menu.domain.IMenuInteractor;

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
        App.getMainComponent().inject(this);
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
                .subscribe(getViewState()::hideForm, throwable ->
                {
                    getViewState().showMessage("Категория уже существует");
                    throwable.printStackTrace();
                });
    }
}
