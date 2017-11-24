package com.paper.smartcafe.analytics.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paper.smartcafe.R;
import com.paper.smartcafe.entities.TopProduct;

import java.util.ArrayList;

/**
 * Created by Chudofom on 24.08.17.
 */

public class TopProductsAdapter extends RecyclerView.Adapter<TopProductsAdapter.ViewHolder> {
    private ArrayList<TopProduct> topProducts;

    public TopProductsAdapter(ArrayList<TopProduct> topProducts) {
        this.topProducts = topProducts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.top_product_name);
            count = itemView.findViewById(R.id.top_product_count);
        }
    }


    @Override
    public TopProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_list_item, parent, false);
        return new TopProductsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TopProductsAdapter.ViewHolder holder, int position) {
        TopProduct topProduct = topProducts.get(position);
        if (topProduct != null) {
            holder.name.setText(topProduct.product.name);
            holder.count.setText(String.valueOf(topProduct.quantity));
        }
    }

    @Override
    public int getItemCount() {
        return topProducts.size();
    }
}
