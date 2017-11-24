package com.paper.smartcafe.storage.presentation.ingredient;

import com.arellomobile.mvp.InjectViewState;
import com.paper.smartcafe.App;
import com.paper.smartcafe.base.BasePresenter;
import com.paper.smartcafe.entities.IngredientCategory;
import com.paper.smartcafe.entities.Product;
import com.paper.smartcafe.storage.domain.IStrorageInteractor;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chudofom on 27.07.17.
 */
@InjectViewState
public class IngredientPresenter extends BasePresenter<IngredientView> {

    private Product product;
    private ArrayList<IngredientCategory> categories;
    private IngredientView view = getViewState();

    @Inject
    public IStrorageInteractor interactor;

    public IngredientPresenter() {
        App.getStorageComponent().inject(this);
    }

    public void initView(Product product) {
        this.product = product;
        if (product == null)
            product = new Product(new ArrayList<>());
        view.showIngredients(product);
        Disposable disposable = interactor.loadCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredientCategories ->
                {
                    categories = ingredientCategories;
                    if (categories != null) view.showStorage(categories);

                }, Throwable::fillInStackTrace);
        setDisposable(disposable);

    }

    public void ingredientClicked(int categoryId, int productId) {

        product.addIngredient(categories.get(categoryId).ingredients.get(productId));
        view.updateIngredients();
    }

    public void ingredientRemoved(int position) {
        product.ingredients.remove(position);
        view.updateIngredients();
    }

    public void submitButtonClicked() {
        view.returnProduct(product);
    }

    public void cancelButtonClicked() {
        view.returnCancel();
    }
}
