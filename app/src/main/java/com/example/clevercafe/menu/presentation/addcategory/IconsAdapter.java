package com.example.clevercafe.menu.presentation.addcategory;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.clevercafe.R;
import com.example.clevercafe.utils.Utility;

/**
 * Created by Chudofom on 21.09.16.
 */
public class IconsAdapter extends RecyclerView.Adapter<IconsAdapter.ViewHolder> {
    private String[] icons;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;


        public ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.category_imageview);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public IconsAdapter(String[] icons) {
        this.icons = icons;
    }


    @Override
    public IconsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.icon_list_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bitmap bitmap = Utility.loadIconFromAssets(context, icons[position]);
        holder.imageView.setImageBitmap(bitmap);
        holder.imageView.setOnClickListener(v -> v.setBackgroundColor(context.getResources().getColor(R.color.darkBlue)));

    }

    @Override
    public int getItemCount() {
        return icons.length;
    }
}
