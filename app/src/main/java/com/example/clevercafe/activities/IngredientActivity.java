package com.example.clevercafe.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ExpandableListView;

import com.example.clevercafe.R;
import com.example.clevercafe.Units;
import com.example.clevercafe.adapters.IngredientListAdapter;
import com.example.clevercafe.adapters.StorageListAdapter;
import com.example.clevercafe.model.Ingredient;
import com.example.clevercafe.model.IngredientCategory;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {
    private ArrayList<IngredientCategory> categories = new ArrayList<>();
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        categories = fillCategories();
        ExpandableListView storageList = (ExpandableListView) findViewById(R.id.storage_list);
        StorageListAdapter storageListAdapter = new StorageListAdapter(this, categories);
        storageList.setAdapter(storageListAdapter);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ingredient_list);
        IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(ingredients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ingredientListAdapter);
        storageList.setOnChildClickListener((parent, v, groupPosition, childPosition, id) ->
        {
            ingredients.add(categories.get(groupPosition).ingredients.get(childPosition));
            ingredientListAdapter.notifyDataSetChanged();
            return true;
        });

    }

    private ArrayList<IngredientCategory> fillCategories() {
        ArrayList<IngredientCategory> categories = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            IngredientCategory category = new IngredientCategory("Категория " + i, fillIngredients());
            categories.add(category);
        }
        return categories;
    }

    private ArrayList<Ingredient> fillIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Ingredient ingredient = new Ingredient("Продукт " + i, 1, Units.count);
            ingredients.add(ingredient);
        }
        return ingredients;
    }
}
