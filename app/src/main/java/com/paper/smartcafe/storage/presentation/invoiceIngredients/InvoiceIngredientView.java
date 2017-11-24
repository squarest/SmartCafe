package com.paper.smartcafe.storage.presentation.invoiceIngredients;

import com.arellomobile.mvp.MvpView;
import com.paper.smartcafe.entities.Ingredient;
import com.paper.smartcafe.entities.IngredientCategory;
import com.paper.smartcafe.entities.Invoice;

import java.util.ArrayList;

/**
 * Created by Chudofom on 27.07.17.
 */

public interface InvoiceIngredientView extends MvpView {

    void showStorage(ArrayList<IngredientCategory> ingredientCategories);

    void showAddDialog(Ingredient ingredient);

    void showIngredients(Invoice invoice);

    void updateIngredients(double sum);

    void returnInvoice(Invoice invoice);

    void returnCancel();
}
