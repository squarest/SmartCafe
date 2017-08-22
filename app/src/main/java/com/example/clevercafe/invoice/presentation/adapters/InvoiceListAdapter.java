package com.example.clevercafe.invoice.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.entities.Invoice;
import com.example.clevercafe.utils.dateTime.DateTimeUtil;

import java.util.ArrayList;

/**
 * Created by Chudofom on 21.09.16.
 */
public class InvoiceListAdapter extends RecyclerView.Adapter<InvoiceListAdapter.ViewHolder> {
    private ArrayList<Invoice> invoices = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView invoiceName;
        public TextView supplierName;
        public TextView invoiceSum;


        public ViewHolder(View v) {
            super(v);
            invoiceName = v.findViewById(R.id.invoice_name);
            supplierName = v.findViewById(R.id.supplier_name);
            invoiceSum = v.findViewById(R.id.invoice_sum);
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
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Invoice invoice = invoices.get(position);
        holder.invoiceName.setText(invoice.name + " от " + DateTimeUtil.dateToString(invoice.date));
        holder.supplierName.setText("Поставщик: " + invoice.supplierName);
        holder.invoiceSum.setText(String.valueOf("Сумма: " + invoice.sum));


    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }
}
