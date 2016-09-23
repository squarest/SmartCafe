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
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private ArrayList<String> productList = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;

        public ViewHolder(View v) {
            super(v);
            productName = (TextView) v.findViewById(R.id.card_product_name);
        }
    }

    public ProductListAdapter(ArrayList<String> myDataSet) {
        productList = myDataSet;
    }

    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.productName.setText(productList.get(position));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
