package com.example.clevercafe.storage.presentation.invoiceIngredients;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.clevercafe.R;
import com.example.clevercafe.databinding.InvoiceProductsBinding;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.IngredientCategory;
import com.example.clevercafe.entities.Invoice;
import com.example.clevercafe.storage.presentation.adapters.InvoiceIngredientListAdapter;
import com.example.clevercafe.storage.presentation.adapters.StorageListAdapter;

import java.util.ArrayList;

public class InvoiceIngredientActivity extends MvpActivity implements InvoiceIngredientView {
    private InvoiceIngredientListAdapter ingredientListAdapter;
    private InvoiceProductsBinding binding;
    @InjectPresenter
    public InvoiceIngredientPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.invoice_products);
        binding.productList.setLayoutManager(new LinearLayoutManager(this));
        presenter.initView((Invoice) getIntent().getSerializableExtra("invoice"));
        setClickListeners();
    }

    private void setClickListeners() {
        binding.storageList.setOnChildClickListener((parent, v, groupPosition, childPosition, id) ->
        {
            presenter.ingredientClicked(groupPosition, childPosition);
            return true;
        });
        binding.submitButton.setOnClickListener(v -> presenter.submitButtonClicked());
        binding.cancelButton.setOnClickListener(v -> returnCancel());
    }

    @Override
    public void showStorage(ArrayList<IngredientCategory> ingredientCategories) {
        StorageListAdapter storageListAdapter = new StorageListAdapter(ingredientCategories);
        binding.storageList.setAdapter(storageListAdapter);
    }

    @Override
    public void showIngredients(Invoice invoice) {
        ingredientListAdapter = new InvoiceIngredientListAdapter(invoice, presenter);
        binding.productList.setAdapter(ingredientListAdapter);
        binding.invoiceSum.setText("Сумма:" + String.valueOf(invoice.sum));
    }

    @Override
    public void updateIngredients(double sum) {
        ingredientListAdapter.notifyDataSetChanged();
        binding.invoiceSum.setText("Сумма:" + String.valueOf(sum));
    }

    @Override
    public void returnInvoice(Invoice invoice) {
        Intent intent = new Intent();
        intent.putExtra("invoice", invoice);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void returnCancel() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void showAddDialog(Ingredient ingredient) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_invoice_product_dialog);
        EditText ingredientQuantity = dialog.findViewById(R.id.ingredient_quantity);
        TextView ingredientUnits = dialog.findViewById(R.id.ingredient_units);
        EditText ingredientCost = dialog.findViewById(R.id.ingredient_cost);

        dialog.setTitle("Добавление " + ingredient.name);
        ingredientUnits.setText(ingredient.units);
        ingredientCost.setText(String.valueOf(ingredient.cost));

        Button cancelCategoryButton = dialog.findViewById(R.id.cancel_button);
        cancelCategoryButton.setOnClickListener(v ->
        {
            dialog.dismiss();
        });

        Button submitCategoryButton = dialog.findViewById(R.id.submit_button);
        submitCategoryButton.setOnClickListener(v ->
        {

            if (!ingredientQuantity.getText().toString().isEmpty() &
                    !ingredientCost.getText().toString().isEmpty()) {
                ingredient.cost = Double.valueOf(ingredientCost.getText().toString());
                presenter.ingredientAdded(ingredient, Double.valueOf(ingredientQuantity.getText().toString()));
                dialog.dismiss();
            } else
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        });

        dialog.show();

    }
}
