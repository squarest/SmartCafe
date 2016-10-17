package com.example.clevercafe.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.main.IMainPresenter;
import com.example.clevercafe.model.Order;

import java.util.ArrayList;

/**
 * Created by Chudofom on 21.09.16.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private ArrayList<Order> orderList = new ArrayList<>();
    private Context context;
    private IMainPresenter mainPresenter;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderNumber;
        public RecyclerView recyclerView;
        public TextView chooseProductTextView;
        public TextView orderSumTextView;
        public Button submitOrderButton;

        public ViewHolder(View v) {
            super(v);
            orderNumber = (TextView) v.findViewById(R.id.order_number);
            recyclerView = (RecyclerView) v.findViewById(R.id.order_elements);
            chooseProductTextView = (TextView) v.findViewById(R.id.choose_product);
            orderSumTextView = (TextView) v.findViewById(R.id.order_sum);
            submitOrderButton = (Button) v.findViewById(R.id.order_submit_button);
        }
    }

    public OrderListAdapter(Context context, ArrayList<Order> myDataSet, IMainPresenter mainPresenter) {
        this.context = context;
        this.mainPresenter = mainPresenter;
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
        if (orderList.get(position).products.size() > 0) {
            holder.chooseProductTextView.setVisibility(View.GONE);
            holder.orderSumTextView.setText("Итого: " + orderList.get(position).sum);
        } else {
            holder.chooseProductTextView.setVisibility(View.VISIBLE);
        }
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(new OrderElementsAdapter(orderList.get(position).products));
        holder.submitOrderButton.setOnClickListener(v ->
        {
            mainPresenter.orderSubmitButtonClicked();
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
