package com.example.clevercafe.menu.presentation.addcategory;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.clevercafe.App;
import com.example.clevercafe.R;
import com.example.clevercafe.databinding.AddcategoryFragmentBinding;
import com.example.clevercafe.entities.ProductCategory;

/**
 * Created by Chudofom on 01.08.17.
 */

public class AddCategoryFragment extends MvpAppCompatFragment implements IAddCategoryFragment {
    private AddcategoryFragmentBinding binding;
    @InjectPresenter
    public AddCategoryPresenter presenter;
    private ProductCategory currentCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.addcategory_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClickListeners();
    }

    private void setClickListeners() {
        binding.categoryCancelButton.setOnClickListener(v -> hideForm());
        binding.categorySubmitButton.setOnClickListener(view1 -> {
            ProductCategory category = getCategory();
            presenter.submitButtonClicked(category);
        });
        binding.addCategoryButton.setOnClickListener(v -> showAddForm());
        binding.addIconButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(getContext(), IconsActivity.class);
            getActivity().startActivityForResult(intent, App.ICON_REQUEST_CODE);
        });
    }

    @Override
    public void showAddForm() {
        currentCategory = new ProductCategory();
        binding.addCategoryForm.setVisibility(View.VISIBLE);
        binding.addCategoryForm.setClickable(true);

    }

    @Override
    public void showEditForm(ProductCategory category) {
        showAddForm();
        currentCategory = category;
        setCategory(category);
    }

    @Override
    public void hideForm() {
        binding.addCategoryForm.setVisibility(View.INVISIBLE);
        binding.addCategoryForm.setClickable(false);
        clearForm();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView(this);
    }

    private void clearForm() {
        currentCategory = null;
        binding.categoryName.setText(null);
    }

    private ProductCategory getCategory() {
        if (!binding.categoryName.getText().toString().isEmpty()) {
            currentCategory.name = binding.categoryName.getText().toString();
        } else {
            Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
        return currentCategory;
    }

    private void setCategory(ProductCategory category) {
        binding.categoryName.setText(category.name);

    }

    public void setCategoryIconPath(String iconPath) {
        currentCategory.setIconPath(iconPath);
    }
}
