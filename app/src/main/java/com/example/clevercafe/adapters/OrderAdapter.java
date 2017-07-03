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
import com.example.clevercafe.activities.main.MainView;
import com.example.clevercafe.model.Product;

import java.util.ArrayList;

/**
 * Created by Chudofom on 21.09.16.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<Product> products;
    private MainView  view;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public EditText productQuantity;
        public TextView productUnits;
        public TextView deleteButton;

        public ViewHolder(View v) {
            super(v);
            productName = (TextView) v.findViewById(R.id.ingredient_name);
            productQuantity = (EditText) v.findViewById(R.id.ingredient_quantity);
            productUnits = (TextView) v.findViewById(R.id.ingredient_units);
            deleteButton = (TextView) v.findViewById(R.id.delete_button);
        }
    }

    public OrderAdapter(ArrayList<Product> products, MainView view) {
        this.products = products;
        this.view = view;
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_element, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.productName.setText(products.get(position).name);
        holder.productQuantity.setText(String.valueOf(products.get(position).quantity));
            holder.productQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    products.get(position).quantity = Double.valueOf(s.toString());
                }
            });
        holder.productUnits.setText(products.get(position).units);
        holder.deleteButton.setOnClickListener(v ->
        {
            products.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, products.size());
            if (products.size() == 0) {
                view.hideButtonPanel();
            }

        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
