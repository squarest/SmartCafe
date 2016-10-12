package com.example.clevercafe.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.model.Product;

import java.util.ArrayList;

/**
 * Created by Chudofom on 04.10.16.
 */

public class OrderElementsAdapter extends RecyclerView.Adapter<OrderElementsAdapter.ViewHolder> {
    private ArrayList<Product> products;

    public OrderElementsAdapter(ArrayList<Product> objects) {
        this.products = objects;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView cost;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.order_product_name);
            cost = (TextView) itemView.findViewById(R.id.order_product_cost);
        }
    }


    @Override
    public OrderElementsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_element, parent, false);
        return new OrderElementsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(products.get(position).name);
        holder.cost.setText(String.valueOf(products.get(position).cost));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
