package com.paper.smartcafe.storage.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paper.smartcafe.R;
import com.paper.smartcafe.entities.Ingredient;
import com.paper.smartcafe.entities.Invoice;
import com.paper.smartcafe.storage.presentation.invoiceIngredients.InvoiceIngredientPresenter;

/**
 * Created by Chudofom on 21.09.16.
 */
public class InvoiceIngredientListAdapter extends RecyclerView.Adapter<InvoiceIngredientListAdapter.ViewHolder> {

    public InvoiceIngredientPresenter presenter;
    private Invoice invoice;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredientName;
        public TextView ingredientQuantity;
        public TextView ingredientCost;
        public TextView deleteButton;

        public ViewHolder(View v) {
            super(v);
            ingredientName = v.findViewById(R.id.ingredient_name);
            ingredientQuantity = v.findViewById(R.id.ingredient_quantity);
            ingredientCost = v.findViewById(R.id.ingredient_cost);
            deleteButton = v.findViewById(R.id.delete_button);
        }
    }

    public InvoiceIngredientListAdapter(Invoice invoice, InvoiceIngredientPresenter presenter) {
        this.invoice = invoice;
        this.presenter = presenter;
    }

    @Override
    public InvoiceIngredientListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invoice_products_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = invoice.ingredients.get(position);
        holder.ingredientName.setText(ingredient.name);
        holder.ingredientQuantity.setText(String.valueOf(invoice.getIngredientCount(ingredient.id)) + ingredient.units);
        holder.ingredientCost.setText(String.valueOf(ingredient.cost));
        holder.deleteButton.setOnClickListener(v -> presenter.ingredientRemoved(position));
    }

    @Override
    public int getItemCount() {
        return invoice.ingredients.size();
    }
}
