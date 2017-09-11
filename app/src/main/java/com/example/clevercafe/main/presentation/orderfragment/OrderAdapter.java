package com.example.clevercafe.main.presentation.orderfragment;

import android.content.Context;
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
import com.example.clevercafe.utils.DialogUtil;

/**
 * Created by Chudofom on 21.09.16.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private OrderPresenter presenter;
    private Order order;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public TextView comment;
        public EditText productQuantity;
        public TextView productUnits;
        public TextView deleteButton;

        public ViewHolder(View v) {
            super(v);

            productName = v.findViewById(R.id.product_name);
            comment = v.findViewById(R.id.comment);
            productQuantity = v.findViewById(R.id.product_quantity);
            productUnits = v.findViewById(R.id.product_units);
            deleteButton = v.findViewById(R.id.delete_button);
        }
    }

    public OrderAdapter(Order order, OrderPresenter presenter) {
        this.order = order;
        this.presenter = presenter;
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        this.context = parent.getContext();
        View v = LayoutInflater.from(context)
                .inflate(R.layout.order_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.productName.setText(order.products.get(position).name);
        holder.productName.setOnLongClickListener(v ->
        {
            presenter.productLongClicked(position);
            return true;
        });
        String comment = order.getProductComment(order.products.get(position).id);
        if (comment != null && !comment.isEmpty()) {
            holder.comment.setText(comment);
            holder.comment.setVisibility(View.VISIBLE);
        } else {
            holder.comment.setVisibility(View.GONE);
        }
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
                presenter.ingredientsCountChanged(order.products.get(position));
            }
        });
        holder.productQuantity.setText(String.valueOf(order.getProductCount(order.products.get(position).id)));
        holder.productUnits.setText(order.products.get(position).units);
        holder.deleteButton.setOnClickListener(v ->
                DialogUtil.getDeleteAlertDialog(context, "Удаление продукта", "Вы действительно хотите удалить продукт?", (dialogInterface, i) -> {
                    presenter.productRemoved(position);
                }).show());
    }

    @Override
    public int getItemCount() {
        return order.products.size();
    }
}
