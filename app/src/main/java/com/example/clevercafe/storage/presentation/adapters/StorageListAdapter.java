package com.example.clevercafe.storage.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.IngredientCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 10.11.16.
 */

public class StorageListAdapter extends BaseExpandableListAdapter {

    private ArrayList<IngredientCategory> categories;

    public StorageListAdapter(ArrayList<IngredientCategory> categories) {
        this.categories = categories;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return categories.get(groupPosition).ingredients.get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Ingredient child = (Ingredient) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.storage_list_subitem, null);
        }

        TextView ingredientName = convertView.findViewById(R.id.storage_item_text_view);
        ingredientName.setText(child.name);

        TextView ingredientQuantity = convertView.findViewById(R.id.storage_product_quantity);
        ingredientQuantity.setText(String.valueOf(child.quantity)+" "+child.units);

        TextView ingredientCost = convertView.findViewById(R.id.storage_product_cost);
        ingredientCost.setText(String.valueOf(child.cost) + "руб");
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.categories.get(groupPosition).ingredients.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.categories.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.categories.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.storage_list_item, null);
        }

        IngredientCategory categoryName = (IngredientCategory) getGroup(groupPosition);
        TextView lblListHeader = convertView.findViewById(R.id.storage_item_text_view);
        lblListHeader.setText(categoryName.name);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
