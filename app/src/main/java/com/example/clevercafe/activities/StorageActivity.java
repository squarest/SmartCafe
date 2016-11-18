package com.example.clevercafe.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.Units;
import com.example.clevercafe.adapters.StorageListAdapter;

import java.util.HashMap;

public class StorageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        String[] categories = fillCategories();
        HashMap<String, String[]> products = fillProducts(categories);
        ExpandableListView storageList = (ExpandableListView) findViewById(R.id.storage_list);
        StorageListAdapter storageListAdapter = new StorageListAdapter(this, categories, products);
        storageList.setAdapter(storageListAdapter);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.drawer_header);
        dialog.show();
        Spinner categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fillCategories());
        categorySpinner.setAdapter(categoryAdapter);
        Spinner unitsSpinner = (Spinner) findViewById(R.id.units_spinner);
        ArrayAdapter unitsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Units.array);
        unitsSpinner.setAdapter(unitsAdapter);
        TextView addProductButton = (TextView) findViewById(R.id.add_product_button);
        CardView addProductForm = (CardView) findViewById(R.id.add_product_form);
        addProductButton.setOnClickListener(v ->
        {
            addProductForm.setVisibility(View.VISIBLE);
            addProductForm.setClickable(true);

        });
        Button cancelButton = (Button) findViewById(R.id.product_cancel_button);
        cancelButton.setOnClickListener(v ->
        {
            addProductForm.setVisibility(View.INVISIBLE);
            addProductForm.setClickable(false);
        });


    }

    private String[] fillCategories() {
        String[] categories = new String[5];
        for (int i = 0; i < 5; i++) {
            categories[i] = "Категория " + i;
        }
        return categories;
    }

    private HashMap<String, String[]> fillProducts(String[] categories) {
        HashMap<String, String[]> products = new HashMap<String, String[]>();
        for (String category : categories) {
            String[] productArray = new String[5];
            for (int i = 0; i < 5; i++) {
                productArray[i] = "Продукт " + i;
            }
            products.put(category, productArray);
        }
        return products;
    }
}
