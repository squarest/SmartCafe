package com.example.clevercafe.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clevercafe.R;

/**
 * Created by Chudofom on 17.10.16.
 */

public class DrawerListAdapter extends ArrayAdapter<String> {
    Context context;
    String[] titels;
    Integer[] icons;

    public DrawerListAdapter(Context context, String[] titels, Integer[] icons) {
        super(context, R.layout.drawer_list_item, titels);
        this.context = context;
        this.titels = titels;
        this.icons = icons;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.drawer_list_item, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.drawer_item_text_view);
        ImageView imageView = (ImageView) view.findViewById(R.id.drawer_item_image_view);
        textView.setText(titels[position]);
        imageView.setImageResource(icons[position]);
        return view;

    }
}
