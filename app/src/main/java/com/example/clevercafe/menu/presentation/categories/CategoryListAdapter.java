package com.example.clevercafe.menu.presentation.categories;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.entities.ProductCategory;
import com.example.clevercafe.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Chudofom on 21.09.16.
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private ArrayList<ProductCategory> categoryList = new ArrayList<>();
    private Context context;
    private static boolean editMode;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public TextView nameTextView;
        public CardView cardView;
        public ImageView imageView;


        public ViewHolder(View v) {
            super(v);
            nameTextView = v.findViewById(R.id.card_product_name);
            imageView = v.findViewById(R.id.category_imageview);
            cardView = v.findViewById(R.id.category_card);
            if (editMode)
                v.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(1, 1, 1, "Редактировать");
            menu.add(2, 2, 2, "Удалить");
            menu.setHeaderTitle("Изменение категории");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public CategoryListAdapter(ArrayList<ProductCategory> arrayList, boolean editMode) {
        categoryList = arrayList;
        CategoryListAdapter.editMode = editMode;
    }


    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductCategory productCategory = categoryList.get(position);
        holder.nameTextView.setText(productCategory.name);
        holder.itemView.setOnLongClickListener(v -> {
            setPosition(holder.getAdapterPosition());
            return false;
        });

        int backgroundColor = R.color.darkGrey;
        if (position % 3 == 0)
            backgroundColor = R.color.purple;
        else if (position % 3 == 1 % 3)
            backgroundColor = R.color.darkBlue;
        else if (position % 3 == 2 % 3)
            backgroundColor = R.color.lightBlue;
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(backgroundColor));
        holder.imageView.setImageBitmap(Utility.loadIconFromAssets(context, productCategory.iconPath));

    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
