package com.example.clevercafe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
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
    private ArrayList<Ingredient> ingredients;
    private Button cancelButton;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        categories = fillCategories();


        cancelButton = (Button) findViewById(R.id.cancel_button);
        submitButton = (Button) findViewById(R.id.submit_button);
        ExpandableListView storageList = (ExpandableListView) findViewById(R.id.storage_list);
        StorageListAdapter storageListAdapter = new StorageListAdapter(this, categories);
        storageList.setAdapter(storageListAdapter);

        if (getIntent().getSerializableExtra("ingredients") != null) {
            ingredients = (ArrayList<Ingredient>) getIntent().getSerializableExtra("ingredients");
            showButtons();
        }
        else
        {
            ingredients = new ArrayList<>();
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ingredient_list);
        IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(ingredients, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ingredientListAdapter);

        storageList.setOnChildClickListener((parent, v, groupPosition, childPosition, id) ->
        {
            ingredients.add(categories.get(groupPosition).ingredients.get(childPosition));
            ingredientListAdapter.notifyDataSetChanged();
            if (ingredients.size() == 1) {
                showButtons();
            }
            return true;
        });
        submitButton.setOnClickListener(v ->
        {
            Intent intent = new Intent();
            intent.putExtra("ingredients", ingredients);
            setResult(RESULT_OK, intent);
            finish();
        });
        cancelButton.setOnClickListener(v ->
        {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });
    }

    public void showButtons() {
        cancelButton.setVisibility(Button.VISIBLE);
        submitButton.setVisibility(Button.VISIBLE);
    }

    public void hideButtons() {
        cancelButton.setVisibility(Button.INVISIBLE);
        submitButton.setVisibility(Button.INVISIBLE);
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
