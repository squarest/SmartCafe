package com.example.clevercafe.storage.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.clevercafe.db.IngredientRepository;
import com.example.clevercafe.R;
import com.example.clevercafe.storage.presentation.adapters.IngredientListAdapter;
import com.example.clevercafe.storage.presentation.adapters.StorageListAdapter;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.IngredientCategory;

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
        IngredientRepository repository = new IngredientRepository(this);
        categories = repository.getCategories();


        cancelButton = (Button) findViewById(R.id.cancel_button);
        submitButton = (Button) findViewById(R.id.submit_button);
        ExpandableListView storageList = (ExpandableListView) findViewById(R.id.storage_list);
        StorageListAdapter storageListAdapter = new StorageListAdapter(this, categories);
        storageList.setAdapter(storageListAdapter);

        if (getIntent().getSerializableExtra("ingredients") != null) {
            ingredients = (ArrayList<Ingredient>) getIntent().getSerializableExtra("ingredients");
            showButtons();
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
}
