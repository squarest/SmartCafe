package com.example.clevercafe.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clevercafe.R;

import java.util.ArrayList;

/**
 * Created by Chudofom on 21.09.16.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private ArrayList<String> orderList = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderNumber;

        public ViewHolder(View v) {
            super(v);
            orderNumber = (TextView) v.findViewById(R.id.order_number);
        }
    }

    public OrderListAdapter(ArrayList<String> myDataSet) {
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
        holder.orderNumber.setText(orderList.get(position));

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
