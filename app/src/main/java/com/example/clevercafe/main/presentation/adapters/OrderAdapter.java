package com.example.clevercafe.main.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.main.presentation.MainActivity;

/**
 * Created by Chudofom on 21.09.16.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private MainActivity view;
    private Order order;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public EditText productQuantity;
        public TextView productUnits;
        public TextView deleteButton;

        public ViewHolder(View v) {
            super(v);
            productName = v.findViewById(R.id.ingredient_name);
            productQuantity = v.findViewById(R.id.ingredient_quantity);
            productUnits = v.findViewById(R.id.ingredient_units);
            deleteButton = v.findViewById(R.id.delete_button);
        }
    }

    public OrderAdapter(Order order, MainActivity view) {
        this.order = order;
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
        holder.productName.setText(order.products.get(position).name);
        holder.productQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                order.setProductCount(order.products.get(position).id, Double.valueOf(s.toString()));
            }
        });
        holder.productQuantity.setText(String.valueOf(order.getProductCount(order.products.get(position).id)));
        holder.productUnits.setText(order.products.get(position).units);
        holder.deleteButton.setOnClickListener(v ->
        {
            order.products.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, order.products.size());
            if (order.products.size() == 0) {
                view.hideButtonPanel();
            }

        });
    }

    @Override
    public int getItemCount() {
        return order.products.size();
    }
}
