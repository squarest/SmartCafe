package com.paper.smartcafe.invoice.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paper.smartcafe.R;
import com.paper.smartcafe.entities.Invoice;
import com.paper.smartcafe.utils.dateTime.DateTimeUtil;

import java.util.ArrayList;

/**
 * Created by Chudofom on 21.09.16.
 */
public class InvoiceListAdapter extends RecyclerView.Adapter<InvoiceListAdapter.ViewHolder> {
    private ArrayList<Invoice> invoices = new ArrayList<>();
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {


        public TextView invoiceName;
        public TextView supplierName;
        public TextView invoiceSum;
        public RecyclerView invoiceIngredients;
        public View view;

        public ViewHolder(View v) {
            super(v);
            this.view = v;
            invoiceName = v.findViewById(R.id.invoice_name);
            supplierName = v.findViewById(R.id.supplier_name);
            invoiceSum = v.findViewById(R.id.invoice_sum);
            invoiceIngredients = v.findViewById(R.id.invoice_ingredients);
            v.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(1, 1, getAdapterPosition(), "Редактировать");
            contextMenu.add(2, 2, getAdapterPosition(), "Удалить");
            contextMenu.setHeaderTitle("Изменение накладной");
        }
    }

    public InvoiceListAdapter(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public InvoiceListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invoice_list_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Invoice invoice = invoices.get(position);
        holder.invoiceName.setText(invoice.name + " от " + DateTimeUtil.dateToString(invoice.date));
        holder.supplierName.setText("Поставщик: " + invoice.supplierName);
        holder.invoiceSum.setText(String.valueOf("Сумма: " + invoice.sum));
        holder.invoiceIngredients.setLayoutManager(new LinearLayoutManager(context));
        InvoiceIngredientsAdapter adapter = new InvoiceIngredientsAdapter(invoice);
        holder.view.setOnClickListener(v ->
        {
            if (holder.invoiceIngredients.getAdapter() == null) {
                holder.invoiceIngredients.setAdapter(adapter);
            } else {
                holder.invoiceIngredients.setAdapter(null);
            }
            notifyDataSetChanged();
        });


    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }
}
