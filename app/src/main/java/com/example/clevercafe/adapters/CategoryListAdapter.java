package com.example.clevercafe.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.model.ProductCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 21.09.16.
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private ArrayList<ProductCategory> categoryList = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;

        public ViewHolder(View v) {
            super(v);
            nameTextView = (TextView) v.findViewById(R.id.card_product_name);
        }
    }

    public CategoryListAdapter(ArrayList<ProductCategory> arrayList)
    {
        categoryList =arrayList;
    }
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTextView.setText(categoryList.get(position).name);

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
