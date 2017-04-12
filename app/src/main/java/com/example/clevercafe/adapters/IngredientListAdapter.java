package com.example.clevercafe.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.model.Ingredient;

import java.util.ArrayList;

/**
 * Created by Chudofom on 21.09.16.
 */
public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.ViewHolder> {
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredientName;
        public EditText ingredientQuantity;
        public TextView ingredientUnits;

        public ViewHolder(View v) {
            super(v);
            ingredientName = (TextView) v.findViewById(R.id.ingredient_name);
            ingredientQuantity = (EditText) v.findViewById(R.id.ingredient_quantity);
            ingredientUnits = (TextView) v.findViewById(R.id.ingredient_units);
        }
    }

    public IngredientListAdapter(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
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
        holder.ingredientName.setText(ingredients.get(position).name);
        holder.ingredientQuantity.setText("1.0");
        holder.ingredientUnits.setText(ingredients.get(position).units);

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
