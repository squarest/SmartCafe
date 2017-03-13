package com.example.clevercafe.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clevercafe.R;
import com.example.clevercafe.Units;
import com.example.clevercafe.adapters.StorageListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class StorageActivity extends AppCompatActivity {

    private ArrayList<String> categories;
    private HashMap<String, ArrayList<String>> products;
    private StorageListAdapter storageListAdapter;
    private ArrayAdapter categorySpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        categories = fillCategories();
        products = fillProducts(categories);
        ExpandableListView storageList = (ExpandableListView) findViewById(R.id.storage_list);
        storageListAdapter = new StorageListAdapter(this, categories, products);
        storageList.setAdapter(storageListAdapter);

        TextView addCategoryButton = (TextView) findViewById(R.id.add_category_button);
        addCategoryButton.setOnClickListener(v -> createCategoryDialog());
        createSpinner();
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

    private void createSpinner() {
        Spinner categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        categorySpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categories);
        categorySpinner.setAdapter(categorySpinnerAdapter);
        Spinner unitsSpinner = (Spinner) findViewById(R.id.units_spinner);
        ArrayAdapter unitsSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Units.array);
        unitsSpinner.setAdapter(unitsSpinnerAdapter);
    }

    private void createCategoryDialog() {
        Dialog categoryDialog = new Dialog(this);
        categoryDialog.setContentView(R.layout.add_category_dialog);
        Button cancelCategoryButton = (Button) categoryDialog.findViewById(R.id.category_cancel_button);
        cancelCategoryButton.setOnClickListener(v ->
        {
            categoryDialog.dismiss();
        });
        Button submitCategoryButton = (Button) categoryDialog.findViewById(R.id.category_submit_button);
        submitCategoryButton.setOnClickListener(v ->
        {
            EditText categoryNameEditText = (EditText) categoryDialog.findViewById(R.id.add_category_edit_text);
            String categoryName = categoryNameEditText.getText().toString();
            if (!categoryName.isEmpty()) {
                categories.add(categoryName);
                products.put(categoryName, new ArrayList<>());
                storageListAdapter.notifyDataSetChanged();
                categorySpinnerAdapter.notifyDataSetChanged();
                categoryDialog.dismiss();
            } else Toast.makeText(this, " Введите название категории", Toast.LENGTH_SHORT).show();
        });
        categoryDialog.setTitle("Добавление новой категории");
        categoryDialog.show();
    }

    private ArrayList<String> fillCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            categories.add("Категория " + i);
        }
        return categories;
    }

    private HashMap<String, ArrayList<String>> fillProducts(ArrayList<String> categories) {
        HashMap<String, ArrayList<String>> products = new HashMap<>();
        for (String category : categories) {
            ArrayList<String> productArray = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                productArray.add("Продукт " + i);
            }
            products.put(category, productArray);
        }
        return products;
    }
}
