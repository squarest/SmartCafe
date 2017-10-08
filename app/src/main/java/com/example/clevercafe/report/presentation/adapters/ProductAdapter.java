package com.example.clevercafe.report.presentation.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clevercafe.R;
import com.example.clevercafe.databinding.ProductReportItemBinding;
import com.example.clevercafe.report.entity.ProductReportItem;

import java.util.ArrayList;

/**
 * Created by Chudofom on 07.10.17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<ProductReportItem> items;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ProductReportItemBinding binding;

        public ViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);

        }

    }

    public ProductAdapter(ArrayList<ProductReportItem> items) {
        this.items = items;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ProductReportItemBinding binding = ProductReportItemBinding.inflate(inflater, parent, false);
        return new ProductAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            setHeader(holder);
        } else {
            ProductReportItem item = items.get(position - 1);
            holder.binding.name.setText(item.name);
            holder.binding.count.setText(String.valueOf(item.count));
            holder.binding.proceed.setText(String.valueOf(item.proceed));
            holder.binding.profit.setText(String.valueOf(item.profit));
            holder.binding.expense.setText(String.valueOf(item.expense));

        }
    }

    private void setHeader(ProductAdapter.ViewHolder holder) {
        holder.binding.divider.setBackgroundColor(context.getResources().getColor(R.color.purple));
        holder.binding.name.setText("Товар");
        holder.binding.name.setTextColor(context.getResources().getColor(R.color.darkGrey));
        holder.binding.name.setTextSize(20);

        holder.binding.count.setText("Продано");
        holder.binding.count.setTextColor(context.getResources().getColor(R.color.darkGrey));
        holder.binding.count.setTextSize(20);

        holder.binding.proceed.setText("Выручка");
        holder.binding.proceed.setTextColor(context.getResources().getColor(R.color.darkGrey));
        holder.binding.proceed.setTextSize(20);

        holder.binding.profit.setText("Прибыль");
        holder.binding.profit.setTextColor(context.getResources().getColor(R.color.darkGrey));
        holder.binding.profit.setTextSize(20);

        holder.binding.expense.setText("Расход");
        holder.binding.expense.setTextColor(context.getResources().getColor(R.color.darkGrey));
        holder.binding.expense.setTextSize(20);
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }
}
