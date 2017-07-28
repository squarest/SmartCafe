package com.example.clevercafe.storage.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.clevercafe.R;
import com.example.clevercafe.entities.IngredientCategory;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.storage.presentation.adapters.IngredientListAdapter;
import com.example.clevercafe.storage.presentation.adapters.StorageListAdapter;

import java.util.ArrayList;

public class IngredientActivity extends MvpActivity implements IngredientView {
    private Button cancelButton;
    private Button submitButton;
    private ExpandableListView storageList;
    private RecyclerView ingredientsRecyclerView;
    private IngredientListAdapter ingredientListAdapter;

    @InjectPresenter
    public IngredientPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        initUI();
        presenter.initView((Product) getIntent().getSerializableExtra("product"));
        storageList.setOnChildClickListener((parent, v, groupPosition, childPosition, id) ->
        {
            presenter.ingredientClicked(groupPosition, childPosition);
            return true;
        });
        submitButton.setOnClickListener(v ->
        {
            presenter.submitButtonClicked();
        });
        cancelButton.setOnClickListener(v ->
        {
            presenter.cancelButtonClicked();
        });
    }

    private void initUI() {
        cancelButton = findViewById(R.id.cancel_button);
        submitButton = findViewById(R.id.submit_button);
        storageList = findViewById(R.id.storage_list);
        ingredientsRecyclerView = findViewById(R.id.ingredient_list);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showButtons() {
        cancelButton.setVisibility(Button.VISIBLE);
        submitButton.setVisibility(Button.VISIBLE);
    }

    @Override
    public void hideButtons() {
        cancelButton.setVisibility(Button.INVISIBLE);
        submitButton.setVisibility(Button.INVISIBLE);
    }

    @Override
    public void showStorage(ArrayList<IngredientCategory> ingredientCategories) {
        StorageListAdapter storageListAdapter = new StorageListAdapter(this, ingredientCategories);
        storageList.setAdapter(storageListAdapter);
    }

    @Override
    public void showIngredients(Product product) {
        ingredientListAdapter = new IngredientListAdapter(product, presenter);
        ingredientsRecyclerView.setAdapter(ingredientListAdapter);
    }

    @Override
    public void updateIngredients() {
        ingredientListAdapter.notifyDataSetChanged();
    }

    @Override
    public void returnProduct(Product product) {
        Intent intent = new Intent();
        intent.putExtra("product", product);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void returnCancel() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
