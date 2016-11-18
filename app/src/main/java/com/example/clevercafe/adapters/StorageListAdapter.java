package com.example.clevercafe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.clevercafe.R;

import java.util.HashMap;

/**
 * Created by Chudofom on 10.11.16.
 */

public class StorageListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private String[] listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, String[]> listDataChild;

    public StorageListAdapter(Context context, String[] listDataHeader,
                              HashMap<String, String[]> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return listDataChild.get(listDataHeader[groupPosition])[childPosititon];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.storage_list_subitem, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.storage_item_text_view);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader[groupPosition]).length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader[groupPosition];
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.storage_list_item, null);
        }

        String headerTitle = (String) getGroup(groupPosition);
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.storage_item_text_view);
        lblListHeader.setText(headerTitle);
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
