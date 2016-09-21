package com.example.clevercafe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chudofom on 21.09.16.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private ArrayList<Integer> orderList = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderNumber;

        public ViewHolder(View v) {
            super(v);
            orderNumber = (TextView) v.findViewById(R.id.order_number);
        }
    }

    public OrderListAdapter(ArrayList<Integer> myDataSet) {
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
        holder.orderNumber.setText(holder.orderNumber.getText() + orderList.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
