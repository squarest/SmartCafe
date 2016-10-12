package com.example.clevercafe.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.model.Order;

import java.util.ArrayList;

/**
 * Created by Chudofom on 21.09.16.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private ArrayList<Order> orderList = new ArrayList<>();
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderNumber;
        public RecyclerView recyclerView;
        public TextView chooseProductTextView;

        public ViewHolder(View v) {
            super(v);
            orderNumber = (TextView) v.findViewById(R.id.order_number);
            recyclerView = (RecyclerView) v.findViewById(R.id.order_elements);
            chooseProductTextView = (TextView) v.findViewById(R.id.choose_product);
        }
    }

    public OrderListAdapter(Context context, ArrayList<Order> myDataSet) {
        this.context = context;
        orderList = myDataSet;
    }

    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.orderNumber.setText("Заказ №" + orderList.get(position).id);
        if (orderList.size() == 0) holder.chooseProductTextView.setText("Выберите товар");
        else holder.chooseProductTextView.setText("");
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(new OrderElementsAdapter(orderList.get(position).products));

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
