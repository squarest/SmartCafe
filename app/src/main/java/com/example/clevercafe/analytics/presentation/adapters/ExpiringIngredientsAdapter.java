package com.example.clevercafe.analytics.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.entities.Ingredient;

import java.util.ArrayList;

/**
 * Created by Chudofom on 07.09.17.
 */

public class ExpiringIngredientsAdapter extends RecyclerView.Adapter<ExpiringIngredientsAdapter.ViewHolder> {
    private ArrayList<Ingredient> ingredients;

    public ExpiringIngredientsAdapter(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.top_product_name);
            count = itemView.findViewById(R.id.top_product_count);
        }
    }


    @Override
    public ExpiringIngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_list_item, parent, false);
        return new ExpiringIngredientsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExpiringIngredientsAdapter.ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        if (ingredient != null) {
            holder.name.setText(ingredient.name);
            holder.count.setText(String.format("%.2f %s", ingredient.quantity, ingredient.units));
        }
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
