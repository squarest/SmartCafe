package com.example.clevercafe.storage.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.storage.presentation.ingredient.IngredientPresenter;

/**
 * Created by Chudofom on 21.09.16.
 */
public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.ViewHolder> {

    public IngredientPresenter presenter;
    private Product product;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredientName;
        public EditText ingredientQuantity;
        public TextView ingredientUnits;
        public TextView deleteButton;

        public ViewHolder(View v) {
            super(v);
            ingredientName = v.findViewById(R.id.ingredient_name);
            ingredientQuantity = v.findViewById(R.id.ingredient_quantity);
            ingredientUnits = v.findViewById(R.id.ingredient_units);
            deleteButton = v.findViewById(R.id.delete_button);
        }
    }

    public IngredientListAdapter(Product product, IngredientPresenter presenter) {
        this.product = product;
        this.presenter = presenter;
    }

    @Override
    public IngredientListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_element, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ingredientName.setText(product.ingredients.get(position).name);
        holder.ingredientQuantity.setText(String.valueOf(product.getIngredientCount(product.ingredients.get(position).id)));
        holder.ingredientQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                product.setIngredientCount(product.ingredients.get(position).id, Double.valueOf(s.toString()));
            }
        });
        holder.ingredientUnits.setText(product.ingredients.get(position).units);
        holder.deleteButton.setOnClickListener(v -> presenter.ingredientRemoved(position));
    }

    @Override
    public int getItemCount() {
        return product.ingredients.size();
    }
}
