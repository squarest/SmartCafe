package com.example.clevercafe.main.presentation.ordersfragment.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.entities.Order;

/**
 * Created by Chudofom on 04.10.16.
 */

public class OrderElementsAdapter extends RecyclerView.Adapter<OrderElementsAdapter.ViewHolder> {
    private Order order;

    public OrderElementsAdapter(Order order) {
        this.order = order;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView cost;
        public TextView count;
        public TextView comment;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.order_product_name);
            cost = itemView.findViewById(R.id.order_product_cost);
            count = itemView.findViewById(R.id.order_product_quantity);
            comment = itemView.findViewById(R.id.comment);
        }
    }


    @Override
    public OrderElementsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_element, parent, false);
        return new OrderElementsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(order.products.get(position).name);
        holder.cost.setText(String.valueOf(order.products.get(position).cost));
        holder.count.setText(String.valueOf(order.getProductCount(order.products.get(position).id)));
        String comment = order.getProductComment(order.products.get(position).id);
        if (comment != null && !comment.isEmpty()) {
            holder.comment.setText(comment);
            holder.comment.setVisibility(View.VISIBLE);
        } else {
            holder.comment.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return order.products.size();
    }
}
