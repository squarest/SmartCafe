package com.example.clevercafe.report.presentation.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clevercafe.R;
import com.example.clevercafe.databinding.StorageReportItemBinding;
import com.example.clevercafe.report.entity.StorageReportItem;

import java.util.ArrayList;

/**
 * Created by Chudofom on 07.10.17.
 */

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.ViewHolder> {
    private ArrayList<StorageReportItem> items;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        StorageReportItemBinding binding;

        public ViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);

        }

    }

    public StorageAdapter(ArrayList<StorageReportItem> items) {
        this.items = items;
    }

    @Override
    public StorageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        StorageReportItemBinding binding = StorageReportItemBinding.inflate(inflater, parent, false);
        return new StorageAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(StorageAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            setHeader(holder);
        } else {
            StorageReportItem item = items.get(position - 1);
            holder.binding.name.setText(item.ingredient.name);
            holder.binding.count.setText(String.valueOf(item.ingredient.quantity));
            holder.binding.expense.setText(String.valueOf(item.expensed));

        }
    }

    private void setHeader(StorageAdapter.ViewHolder holder) {
        holder.binding.divider.setBackgroundColor(context.getResources().getColor(R.color.purple));
        holder.binding.name.setText("Товар");
        holder.binding.name.setTextColor(context.getResources().getColor(R.color.darkGrey));
        holder.binding.name.setTextSize(20);

        holder.binding.count.setText("В наличии");
        holder.binding.count.setTextColor(context.getResources().getColor(R.color.darkGrey));
        holder.binding.count.setTextSize(20);

        holder.binding.expense.setText("Израсходовано");
        holder.binding.expense.setTextColor(context.getResources().getColor(R.color.darkGrey));
        holder.binding.expense.setTextSize(20);
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }
}
