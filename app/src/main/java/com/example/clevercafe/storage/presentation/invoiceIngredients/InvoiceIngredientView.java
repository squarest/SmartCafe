package com.example.clevercafe.storage.presentation.invoiceIngredients;

import com.arellomobile.mvp.MvpView;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.IngredientCategory;
import com.example.clevercafe.entities.Invoice;

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
