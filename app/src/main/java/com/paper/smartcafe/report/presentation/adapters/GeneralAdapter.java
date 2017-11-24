package com.paper.smartcafe.report.presentation.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paper.smartcafe.R;
import com.paper.smartcafe.databinding.GeneralReportItemBinding;
import com.paper.smartcafe.report.entity.GeneralReportItem;
import com.paper.smartcafe.utils.dateTime.DateTimeUtil;

import java.util.ArrayList;

/**
 * Created by Chudofom on 07.10.17.
 */

public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.ViewHolder> {
    private ArrayList<GeneralReportItem> items;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        GeneralReportItemBinding binding;

        public ViewHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);

        }
    }

    public GeneralAdapter(ArrayList<GeneralReportItem> items) {
        this.items = items;
    }

    @Override
    public GeneralAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        GeneralReportItemBinding binding = GeneralReportItemBinding.inflate(inflater, parent, false);
        return new GeneralAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(GeneralAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            setHeader(holder);
        } else {
            GeneralReportItem item = items.get(position - 1);
            holder.binding.date.setText(DateTimeUtil.dateToString(item.date));
            holder.binding.proceed.setText(String.valueOf(item.proceeds));
            holder.binding.profit.setText(String.valueOf(item.profit));
            holder.binding.expense.setText(String.valueOf(item.expense));
            holder.binding.orders.setText(String.valueOf(item.orders));
            holder.binding.average.setText(String.format("%.2f", item.averageCheck));
        }
    }

    private void setHeader(ViewHolder holder) {
        holder.binding.divider.setBackgroundColor(context.getResources().getColor(R.color.purple));
        holder.binding.date.setText("Дата");
        holder.binding.date.setTextColor(context.getResources().getColor(R.color.darkGrey));
        holder.binding.date.setTextSize(20);

        holder.binding.proceed.setText("Выручка");
        holder.binding.proceed.setTextColor(context.getResources().getColor(R.color.darkGrey));
        holder.binding.proceed.setTextSize(20);

        holder.binding.profit.setText("Прибыль");
        holder.binding.profit.setTextColor(context.getResources().getColor(R.color.darkGrey));
        holder.binding.profit.setTextSize(20);

        holder.binding.expense.setText("Расходы");
        holder.binding.expense.setTextColor(context.getResources().getColor(R.color.darkGrey));
        holder.binding.expense.setTextSize(20);

        holder.binding.orders.setText("Заказы");
        holder.binding.orders.setTextColor(context.getResources().getColor(R.color.darkGrey));
        holder.binding.orders.setTextSize(20);

        holder.binding.average.setText("Средний чек");
        holder.binding.average.setTextColor(context.getResources().getColor(R.color.darkGrey));
        holder.binding.average.setTextSize(20);
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }
}