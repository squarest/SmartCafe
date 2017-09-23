package com.example.clevercafe.invoice.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.Invoice;

/**
 * Created by Chudofom on 23.09.17.
 */

public class InvoiceIngredientsAdapter extends RecyclerView.Adapter<InvoiceIngredientsAdapter.ViewHolder> {

    private Invoice invoice;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredientName;
        public TextView ingredientQuantity;
        public TextView ingredientCost;

        public ViewHolder(View v) {
            super(v);
            ingredientName = v.findViewById(R.id.ingredient_name);
            ingredientQuantity = v.findViewById(R.id.ingredient_quantity);
            ingredientCost = v.findViewById(R.id.ingredient_cost);
        }
    }

    public InvoiceIngredientsAdapter(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public InvoiceIngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invoice_ingredient, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = invoice.ingredients.get(position);
        holder.ingredientName.setText(ingredient.name);
        holder.ingredientQuantity.setText(String.valueOf(invoice.getIngredientCount(ingredient.id)) + ingredient.units);
        holder.ingredientCost.setText(String.valueOf(ingredient.cost));
    }

    @Override
    public int getItemCount() {
        return invoice.ingredients.size();
    }
}
