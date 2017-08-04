package com.example.clevercafe.menu.presentation.categories;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.clevercafe.App;
import com.example.clevercafe.entities.ProductCategory;
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
public class CategoriesPresenter extends MvpPresenter<ICategoriesFragment> {
    private ArrayList<ProductCategory> categories = new ArrayList<>();
    @Inject
    public IMenuInteractor interactor;

    private Disposable categoriesDisposable;

    public CategoriesPresenter() {
        App.getMenuComponent().inject(this);
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
        interactor.deleteCategory(categories.get(categoryPosition))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void attachView(ICategoriesFragment view) {
        super.attachView(view);
        categoriesDisposable = interactor.categoriesUpdates()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b ->
                {
                    if (b) setCategories();
                }, Throwable::printStackTrace);
    }

    @Override
    public void detachView(ICategoriesFragment view) {
        super.detachView(view);
        categoriesDisposable.dispose();
    }
}
