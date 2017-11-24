package com.paper.smartcafe.storage.presentation.invoiceIngredients;

import com.arellomobile.mvp.InjectViewState;
import com.paper.smartcafe.App;
import com.paper.smartcafe.base.BasePresenter;
import com.paper.smartcafe.entities.Ingredient;
import com.paper.smartcafe.entities.IngredientCategory;
import com.paper.smartcafe.entities.Invoice;
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
public class InvoiceIngredientPresenter extends BasePresenter<InvoiceIngredientView> {

    private Invoice invoice;
    private ArrayList<IngredientCategory> categories;
    private InvoiceIngredientView view = getViewState();

    @Inject
    public IStrorageInteractor interactor;

    public InvoiceIngredientPresenter() {
        App.getStorageComponent().inject(this);
    }

    public void initView(Invoice invoice) {
        this.invoice = invoice;
        if (invoice == null)
            invoice = new Invoice(new ArrayList<>());
        view.showIngredients(invoice);
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
        view.showAddDialog(categories.get(categoryId).ingredients.get(productId));
    }

    public void ingredientAdded(Ingredient ingredient, double count) {

        invoice.ingredients.add(ingredient);
        invoice.setIngredientCount(ingredient.id, count);
        checkSum();
        view.updateIngredients(invoice.sum);
    }

    public void ingredientRemoved(int position) {
        invoice.ingredients.remove(position);
        checkSum();
        view.updateIngredients(invoice.sum);
    }

    public void submitButtonClicked() {
        checkSum();
        view.returnInvoice(invoice);
    }

    private void checkSum() {
        double sum = 0.0;
        for (Ingredient ingredient : invoice.ingredients) {
            sum += ingredient.cost * invoice.getIngredientCount(ingredient.id);
        }
        invoice.sum = sum;
    }

}
