package com.example.clevercafe.storage.presentation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.clevercafe.R;
import com.example.clevercafe.databinding.ActivityIngredientBinding;
import com.example.clevercafe.entities.IngredientCategory;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.storage.presentation.adapters.IngredientListAdapter;
import com.example.clevercafe.storage.presentation.adapters.StorageListAdapter;

import java.util.ArrayList;

public class IngredientActivity extends MvpActivity implements IngredientView {
    private IngredientListAdapter ingredientListAdapter;
    private ActivityIngredientBinding binding;
    @InjectPresenter
    public IngredientPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ingredient);
        binding.ingredientList.setLayoutManager(new LinearLayoutManager(this));
        presenter.initView((Product) getIntent().getSerializableExtra("product"));
        setClickListeners();
    }

    private void setClickListeners() {
        binding.storageList.setOnChildClickListener((parent, v, groupPosition, childPosition, id) ->
        {
            presenter.ingredientClicked(groupPosition, childPosition);
            return true;
        });
        binding.submitButton.setOnClickListener(v -> presenter.submitButtonClicked());
        binding.cancelButton.setOnClickListener(v -> presenter.cancelButtonClicked());
    }


    @Override
    public void showButtons() {
        binding.cancelButton.setVisibility(Button.VISIBLE);
        binding.submitButton.setVisibility(Button.VISIBLE);
    }

    @Override
    public void hideButtons() {
        binding.cancelButton.setVisibility(Button.INVISIBLE);
        binding.submitButton.setVisibility(Button.INVISIBLE);
    }

    @Override
    public void showStorage(ArrayList<IngredientCategory> ingredientCategories) {
        StorageListAdapter storageListAdapter = new StorageListAdapter(this, ingredientCategories);
        binding.storageList.setAdapter(storageListAdapter);
    }

    @Override
    public void showIngredients(Product product) {
        ingredientListAdapter = new IngredientListAdapter(product, presenter);
        binding.ingredientList.setAdapter(ingredientListAdapter);
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
