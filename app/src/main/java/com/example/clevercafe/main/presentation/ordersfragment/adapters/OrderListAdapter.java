package com.example.clevercafe.main.presentation.ordersfragment.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.main.presentation.ordersfragment.OrdersPresenter;

import java.util.ArrayList;

/**
 * Created by Chudofom on 21.09.16.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private ArrayList<Order> orderList = new ArrayList<>();
    private OrdersPresenter presenter;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderNumber;
        public RecyclerView recyclerView;
        public TextView chooseProductTextView;
        public TextView orderSumTextView;
        public Button submitOrderButton;
        public CardView cardView;

        public ViewHolder(View v) {
            super(v);
            orderNumber = v.findViewById(R.id.order_number);
            recyclerView = v.findViewById(R.id.order_elements);
            chooseProductTextView = v.findViewById(R.id.choose_product);
            orderSumTextView = v.findViewById(R.id.order_sum);
            submitOrderButton = v.findViewById(R.id.order_submit_button);
            cardView = v.findViewById(R.id.order_card);
        }
    }

    public OrderListAdapter(ArrayList<Order> myDataSet, OrdersPresenter presenter) {
        this.presenter = presenter;
        orderList = myDataSet;
    }

    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.orderNumber.setText("Заказ №" + orderList.get(position).number);
        if (orderList.get(position).products.size() > 0) {
            holder.chooseProductTextView.setVisibility(View.GONE);
            holder.orderSumTextView.setText("Итого: " + orderList.get(position).sum);
        } else {
            holder.chooseProductTextView.setVisibility(View.VISIBLE);
            holder.orderSumTextView.setText("Итого: ");
        }
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(new OrderElementsAdapter(orderList.get(position)));
        holder.submitOrderButton.setOnClickListener(v ->
        {
            presenter.orderSubmitButtonClicked(orderList.get(position));
        });
        holder.cardView.setOnLongClickListener(v ->
        {
            presenter.orderLongClicked(position);
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
