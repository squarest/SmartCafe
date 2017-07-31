package com.example.clevercafe.menu.presentation;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.clevercafe.R;
import com.example.clevercafe.adapters.CategoryListAdapter;
import com.example.clevercafe.adapters.ProductListAdapter;
import com.example.clevercafe.base.BaseActivity;
import com.example.clevercafe.databinding.ActivityMenuBinding;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;
import com.example.clevercafe.main.presentation.adapters.RecyclerItemClickListener;
import com.example.clevercafe.storage.presentation.IngredientActivity;

import java.util.ArrayList;

/**
 * Created by Chudofom on 20.03.17.
 */

public class MenuActivity extends BaseActivity implements MenuView {
    private ProductListAdapter productAdapter;
    private CategoryListAdapter categoryAdapter;
    private boolean categoryOnScreen = true;
    private ArrayAdapter categorySpinnerAdapter;
    private int curCategoryPosition;
    private Product newProduct;
    private ArrayList<String> categoryNames = new ArrayList<>();
    private ActivityMenuBinding binding;
    @InjectPresenter
    public MenuPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        createToolbar();
        createDrawer();
        setClickLIsteners();
        registerForContextMenu(binding.categoryTable);


        binding.categoryTable.setLayoutManager(new GridLayoutManager(this, 3));
        binding.categoryTable.addOnItemTouchListener(new RecyclerItemClickListener(this,
                (view, position) -> {
                    if (categoryOnScreen) {
                        curCategoryPosition = position;
                    }
                    presenter.itemClicked(categoryOnScreen, position);
                }));
        categorySpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryNames);
        binding.categorySpinner.setAdapter(categorySpinnerAdapter);
        presenter.viewInit();
    }

    private void setClickLIsteners() {
        binding.addProductButton.setOnClickListener(v -> presenter.addProductButClicked());
        binding.addCategoryButton.setOnClickListener(v -> presenter.addCategoryButClicked());
    }


    @Override
    public void showCategories(ArrayList<ProductCategory> categories) {

        categoryAdapter = new CategoryListAdapter(categories, true);
        binding.categoryTable.setAdapter(categoryAdapter);
        categoryOnScreen = true;
        updateSpinners(categories);
        createToolbar();
    }

    @Override
    public void showProducts(ArrayList<Product> products) {
        productAdapter = new ProductListAdapter(products, true);
        binding.categoryTable.setAdapter(productAdapter);
        categoryOnScreen = false;
        toolbar.setNavigationIcon(R.drawable.back_ic);
        toolbar.setNavigationOnClickListener(v -> presenter.backToCategoryButtonClicked());

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            if (categoryOnScreen) {
                position = ((CategoryListAdapter) binding.categoryTable.getAdapter()).getPosition();
            } else
                position = ((ProductListAdapter) binding.categoryTable.getAdapter()).getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case 1: {//edit
                if (categoryOnScreen) {
                    presenter.editCategoryButClicked(position);
                } else {
                    presenter.editProductButClicked(curCategoryPosition, position);
                }
                break;
            }
            case 2: {//remove
                if (categoryOnScreen) {
                    presenter.deleteCategoryButClicked(position);
                } else {
                    presenter.deleteProductButClicked(curCategoryPosition, position);
                }
                break;
            }

        }
        return super.onContextItemSelected(item);
    }

    private void updateSpinners(ArrayList<ProductCategory> categories) {
        categoryNames.clear();
        for (ProductCategory category : categories) {
            categoryNames.add(category.name);
        }
        categorySpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryNames);
        binding.categorySpinner.setAdapter(categorySpinnerAdapter);
        binding.categorySpinner.setSelection(0);
    }

    @Override
    public void updateMenu(ArrayList<ProductCategory> categories) {
        if (categoryOnScreen) {
            categoryAdapter.notifyDataSetChanged();
            updateSpinners(categories);
        } else {
            updateProducts(categories.get(curCategoryPosition).products);
        }
    }

    @Override
    public void removeCategory(ArrayList<ProductCategory> categories, int position) {
        categoryAdapter.notifyItemRemoved(position);
        updateSpinners(categories);

    }

    @Override
    public void updateProducts(ArrayList<Product> products) {
        productAdapter = new ProductListAdapter(products, true);
        binding.categoryTable.setAdapter(productAdapter);
    }


    public void showStorage() {
        Intent intent = new Intent(MenuActivity.this, IngredientActivity.class);
        if (newProduct.ingredients == null) newProduct.ingredients = new ArrayList<>();
        intent.putExtra("product", newProduct);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (null == data) {
                newProduct = new Product();
                return;
            }
            newProduct = (Product) data.getSerializableExtra("product");
        } else Toast.makeText(this, "Добавление ингедиентов отменено", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createAddProductForm(int categoryId, int productId, Product product, boolean editForm) {
        if (editForm) //если форма вызвана для редактирования продукта то заполняем ее данными
        {
            binding.productName.setText(product.name);
            binding.categorySpinner.setSelection(categoryId);
            binding.categorySpinner.setClickable(false);
            binding.categorySpinner.setEnabled(false);
            binding.productCost.setText(String.valueOf(product.cost));
            newProduct = product;

        } else { //иначе очищаем ее
            clearAddProductForm();
        }

        binding.addProductForm.setVisibility(View.VISIBLE);
        binding.addProductForm.setClickable(true);
        if (editForm) {
            binding.addIngredientsButton.setText("Редактировать ингредиенты");
        } else {
            binding.addIngredientsButton.setText("Добавить ингредиенты");
        }
        binding.addIngredientsButton.setOnClickListener(v -> showStorage());
        binding.productCancelButton.setOnClickListener(v ->
        {
            clearAddProductForm();
            binding.addProductForm.setVisibility(View.INVISIBLE);
            binding.addProductForm.setClickable(false);
        });
        binding.productSubmitButton.setOnClickListener(v ->
        {
            if (!binding.productName.getText().toString().isEmpty() &
                    !binding.productCost.getText().toString().isEmpty()) {
                binding.addProductForm.setVisibility(View.INVISIBLE);
                binding.addProductForm.setClickable(false);
                if (editForm) {
                    newProduct.id = product.id;
                    newProduct.categoryId = product.categoryId;
                }
                newProduct.name = binding.productName.getText().toString();
                newProduct.cost = Double.valueOf(binding.productCost.getText().toString());
                presenter.submitProductFormButClicked(binding.categorySpinner.getSelectedItemPosition(),
                        productId, newProduct, editForm);
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void clearAddProductForm() {
        binding.productName.setText(null);
        binding.productCost.setText(null);
        binding.categorySpinner.setSelection(0);
        binding.categorySpinner.setClickable(true);
        binding.categorySpinner.setEnabled(true);
        newProduct = new Product();
    }

    @Override
    public void createCategoryDialog(int categoryId, String name, boolean editForm) {
        Dialog categoryDialog = new Dialog(this);
        categoryDialog.setContentView(R.layout.add_category_dialog);
        EditText categoryNameEditText = categoryDialog.findViewById(R.id.add_category_edit_text);

        if (editForm) {
            categoryNameEditText.setText(name);
            categoryDialog.setTitle("Изменение категории");
        } else categoryDialog.setTitle("Добавление новой категории");

        Button cancelCategoryButton = categoryDialog.findViewById(R.id.category_cancel_button);
        cancelCategoryButton.setOnClickListener(v ->
        {
            categoryDialog.dismiss();
        });

        Button submitCategoryButton = categoryDialog.findViewById(R.id.category_submit_button);
        submitCategoryButton.setOnClickListener(v ->
        {

            String categoryName = categoryNameEditText.getText().toString();
            if (!categoryName.isEmpty()) {
                presenter.submitCategoryFormButClicked(categoryId, categoryName, editForm);
                categoryDialog.dismiss();
            } else
                Toast.makeText(this, "Введите название категории", Toast.LENGTH_SHORT).show();
        });

        categoryDialog.show();
    }
}
