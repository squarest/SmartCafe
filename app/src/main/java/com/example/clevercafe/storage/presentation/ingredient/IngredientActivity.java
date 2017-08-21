package com.example.clevercafe.storage.presentation.ingredient;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.clevercafe.R;
import com.example.clevercafe.databinding.ActivityIngredientBinding;
import com.example.clevercafe.entities.IngredientCategory;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.storage.presentation.adapters.IngredientListAdapter;
import com.example.clevercafe.storage.presentation.adapters.StorageListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

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
    public void showStorage(ArrayList<IngredientCategory> ingredientCategories) {
        StorageListAdapter storageListAdapter = new StorageListAdapter(ingredientCategories);
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
        intent.putExtra("",new HashMap<String, String>());
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
