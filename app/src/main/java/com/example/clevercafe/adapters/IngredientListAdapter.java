package com.example.clevercafe.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.activities.IngredientActivity;
import com.example.clevercafe.model.Ingredient;

import java.util.ArrayList;

/**
 * Created by Chudofom on 21.09.16.
 */
public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.ViewHolder> {
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private IngredientActivity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredientName;
        public EditText ingredientQuantity;
        public TextView ingredientUnits;
        public TextView deleteButton;

        public ViewHolder(View v) {
            super(v);
            ingredientName = (TextView) v.findViewById(R.id.ingredient_name);
            ingredientQuantity = (EditText) v.findViewById(R.id.ingredient_quantity);
            ingredientUnits = (TextView) v.findViewById(R.id.ingredient_units);
            deleteButton = (TextView) v.findViewById(R.id.delete_button);
        }
    }

    public IngredientListAdapter(ArrayList<Ingredient> ingredients, IngredientActivity activity) {
        this.ingredients = ingredients;
        this.activity = activity;
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
        holder.ingredientQuantity.setText(String.valueOf(ingredients.get(position).quantity));
        //TODO: попробовать реализовать сбор данных о количестве при нажатии кнопки готово
        holder.ingredientQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ingredients.get(position).quantity = Double.valueOf(s.toString());
            }
        });
        holder.ingredientUnits.setText(ingredients.get(position).units);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredients.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, ingredients.size());
                if (ingredients.size() == 0) {
                    activity.hideButtons();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
