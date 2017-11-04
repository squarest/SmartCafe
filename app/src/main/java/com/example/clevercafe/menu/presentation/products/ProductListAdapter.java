package com.example.clevercafe.menu.presentation.products;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;
import com.example.clevercafe.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Chudofom on 21.09.16.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private ProductCategory category;
    private ArrayList<Product> productList;
    private static boolean editMode;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public TextView nameTextView;
        public TextView costTextView;
        public ImageView imageView;
        public CardView cardView;


        public ViewHolder(View v) {
            super(v);
            nameTextView = v.findViewById(R.id.card_product_name);
            costTextView = v.findViewById(R.id.product_cost);
            imageView = v.findViewById(R.id.product_imageview);
            cardView = v.findViewById(R.id.product_card);
            if (editMode)
                v.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(1, 1, 1, "Редактировать");
            menu.add(2, 2, 2, "Удалить");
            menu.setHeaderTitle("Изменение продукта");
        }
    }

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ProductListAdapter(ProductCategory category, boolean editMode) {
        this.category = category;
        productList = category.products;
        ProductListAdapter.editMode = editMode;
    }


    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameTextView.setText(product.name);
        holder.costTextView.setText(String.valueOf(product.cost));
        if (product.imagePath != null && !product.imagePath.isEmpty()) {
            holder.imageView.setImageBitmap(BitmapFactory.decodeFile(product.imagePath));
        } else {
            holder.imageView.setImageBitmap(Utility.loadIconFromAssets(context, category.iconPath));
        }
        holder.itemView.setOnLongClickListener(v -> {
            setPosition(holder.getAdapterPosition());
            return false;
        });
        int backgroundColor = R.color.darkGrey;
        if (Utility.calculateNoOfColumns(context) == 3) {
            if (position % 3 == 0)
                backgroundColor = R.color.purple;
            else if (position % 3 == 1 % 3)
                backgroundColor = R.color.darkBlue;
            else if (position % 3 == 2 % 3)
                backgroundColor = R.color.lightBlue;
        } else if (Utility.calculateNoOfColumns(context) == 4) {
            if (position % 4 == 0)
                backgroundColor = R.color.darkPurple;
            else if (position % 4 == 1 % 4)
                backgroundColor = R.color.purple;
            else if (position % 4 == 2 % 4)
                backgroundColor = R.color.lightBlue;
            else if (position % 4 == 3 % 4)
                backgroundColor = R.color.darkBlue;

        }
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(backgroundColor));


    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
