package com.paper.smartcafe.menu.presentation.categories;

import com.arellomobile.mvp.InjectViewState;
import com.paper.smartcafe.App;
import com.paper.smartcafe.base.BasePresenter;
import com.paper.smartcafe.entities.ProductCategory;
import com.paper.smartcafe.menu.domain.IMenuInteractor;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 31.07.17.
 */
@InjectViewState
public class CategoriesPresenter extends BasePresenter<ICategoriesFragment> {
    private ArrayList<ProductCategory> categories = new ArrayList<>();
    @Inject
    public IMenuInteractor interactor;

    public CategoriesPresenter() {
        App.getMainComponent().inject(this);
    }

    public void categoriesInit() {
        setCategories();
    }


    private void setCategories() {
        interactor.loadCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productCategories ->
                {
                    categories.clear();
                    categories.addAll(productCategories);
                    getViewState().showCategories(categories);
                }, Throwable::fillInStackTrace);
    }

    public void editCategoryButClicked(int categoryPosition) {
        interactor.editCategory(categories.get(categoryPosition).id);
    }

    public void deleteCategoryButClicked(int categoryPosition) {
        Disposable disposable = interactor.deleteCategory(categories.get(categoryPosition))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().showWarningDialog("Удаление невозможно", throwable.getMessage());
                });
        setDisposable(disposable);
    }

    @Override
    public void attachView(ICategoriesFragment view) {
        super.attachView(view);
        interactor.categoriesUpdates()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b ->
                {
                    if (b) setCategories();
                }, Throwable::printStackTrace);
    }
}
