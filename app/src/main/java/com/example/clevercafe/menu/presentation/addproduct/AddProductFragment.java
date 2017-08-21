package com.example.clevercafe.menu.presentation.addproduct;

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
import com.example.clevercafe.databinding.AddproductFragmentBinding;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.storage.presentation.ingredient.IngredientActivity;
import com.example.clevercafe.utils.ImagePicker;

import java.util.ArrayList;

/**
 * Created by Chudofom on 01.08.17.
 */

public class AddProductFragment extends MvpAppCompatFragment implements IAddProductFragment {
    private AddproductFragmentBinding binding;
    @InjectPresenter
    public AddProductPresenter presenter;
    private Product curentProduct;
    private long categoryId;

    public static AddProductFragment newInstance(long categoryId) {
        Bundle args = new Bundle();
        args.putLong("categoryId", categoryId);
        AddProductFragment fragment = new AddProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.addproduct_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        categoryId = args.getLong("categoryId", -1);
        setClickListeners();
        if (categoryId == -1) {
            new NullPointerException().printStackTrace();
        }
    }

    private void setClickListeners() {
        binding.productCancelButton.setOnClickListener(v -> hideForm());
        binding.productSubmitButton.setOnClickListener(v -> {
            Product product = getProduct();
            presenter.submitButtonClicked(product);
        });
        binding.addProductButton.setOnClickListener(v -> showAddForm());
        binding.addIngredientsButton.setOnClickListener(v -> showStorage());
        binding.addPictureButton.setOnClickListener(v -> {
            Intent chooseImageIntent = ImagePicker.getPickImageIntent(getContext());
            getActivity().startActivityForResult(chooseImageIntent, App.IMAGE_REQUEST_CODE);
        });
    }

    @Override
    public void showAddForm() {
        curentProduct = new Product();
        binding.addProductForm.setVisibility(View.VISIBLE);
        binding.addProductForm.setClickable(true);

    }

    @Override
    public void showEditForm(Product product) {
        showAddForm();
        curentProduct = product;
        setProduct(product);
    }

    @Override
    public void hideForm() {
        binding.addProductForm.setVisibility(View.INVISIBLE);
        binding.addProductForm.setClickable(false);
        clearForm();

    }

    private void clearForm() {
        binding.productName.setText(null);
        binding.productCost.setText(null);
    }

    private Product getProduct() {
        if (!binding.productName.getText().toString().isEmpty() &
                !binding.productCost.getText().toString().isEmpty()) {
            curentProduct.categoryId = categoryId;
            curentProduct.name = binding.productName.getText().toString();
            curentProduct.cost = Double.valueOf(binding.productCost.getText().toString());
        } else {
            Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
        return curentProduct;
    }

    private void setProduct(Product product) {
        binding.productName.setText(product.name);
        binding.productCost.setText(String.valueOf(product.cost));

    }

    private void showStorage() {
        Intent intent = new Intent(getActivity(), IngredientActivity.class);
        if (curentProduct.ingredients == null) curentProduct.ingredients = new ArrayList<>();
        intent.putExtra("product", curentProduct);
        getActivity().startActivityForResult(intent, App.INGREDIENT_REQUEST_CODE);
    }

    public void setImagePath(String imagePath) {
        curentProduct.imagePath = imagePath;
    }

    public void setIngredients(Product product) {
        if (product.ingredients != null) {
            curentProduct.ingredients = product.ingredients;
            curentProduct.setIngredientsCount(product.getIngredientsCount());

        }

    }

}
