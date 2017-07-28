package com.example.clevercafe.menu.presentation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.clevercafe.R;
import com.example.clevercafe.adapters.CategoryListAdapter;
import com.example.clevercafe.adapters.ProductListAdapter;
import com.example.clevercafe.base.BaseActivity;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;
import com.example.clevercafe.main.presentation.adapters.RecyclerItemClickListener;
import com.example.clevercafe.storage.presentation.IngredientActivity;

import java.util.ArrayList;

/**
 * Created by Chudofom on 20.03.17.
 */

public class MenuActivity extends BaseActivity implements MenuView {
    private EditText productNameEditText;
    private EditText productCostEditText;
    private CardView addProductForm;
    private ProductListAdapter productAdapter;
    private CategoryListAdapter categoryAdapter;
    private RecyclerView categoryProductRecyclerView;
    private boolean categoryOnScreen = true;
    private ArrayAdapter categorySpinnerAdapter;
    private int curCategoryPosition;
    private Spinner categorySpinner;
    private Product newProduct;
    private ArrayList<String> categoryNames = new ArrayList<>();

    @InjectPresenter
    public MenuPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createToolbar();
        createDrawer();
        categoryProductRecyclerView = findViewById(R.id.category_table);
        categoryProductRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        categoryProductRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                (view, position) -> {
                    if (categoryOnScreen) {
                        curCategoryPosition = position;
                    }
                    presenter.itemClicked(categoryOnScreen, position);
                }));
        TextView addProductButton = findViewById(R.id.add_product_button);
        addProductForm = findViewById(R.id.add_product_form);
        productNameEditText = findViewById(R.id.ingredient_name_edit_text);
        productCostEditText = findViewById(R.id.ingredient_cost_edit_text);
        registerForContextMenu(categoryProductRecyclerView);

        addProductButton.setOnClickListener(v ->
        {
            presenter.addProductButClicked();
        });


        categorySpinner = findViewById(R.id.category_spinner);
        categorySpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryNames);
        categorySpinner.setAdapter(categorySpinnerAdapter);
        TextView addCategoryButton = findViewById(R.id.add_category_button);
        addCategoryButton.setOnClickListener(v -> presenter.addCategoryButClicked());
        presenter.viewInit();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_menu;
    }

    @Override
    public void showCategories(ArrayList<ProductCategory> categories) {

        categoryAdapter = new CategoryListAdapter(categories, true);
        categoryProductRecyclerView.setAdapter(categoryAdapter);
        categoryOnScreen = true;
        updateSpinners(categories);
        createToolbar();
    }

    @Override
    public void showProducts(ArrayList<Product> products) {
        productAdapter = new ProductListAdapter(products, true);
        categoryProductRecyclerView.setAdapter(productAdapter);
        categoryOnScreen = false;
        toolbar.setNavigationIcon(R.drawable.back_ic);
        toolbar.setNavigationOnClickListener(v -> presenter.backToCategoryButtonClicked());

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            if (categoryOnScreen) {
                position = ((CategoryListAdapter) categoryProductRecyclerView.getAdapter()).getPosition();
            } else
                position = ((ProductListAdapter) categoryProductRecyclerView.getAdapter()).getPosition();
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
        categorySpinner.setAdapter(categorySpinnerAdapter);
        categorySpinner.setSelection(0);
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
        categoryProductRecyclerView.setAdapter(productAdapter);
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
            productNameEditText.setText(product.name);
            categorySpinner.setSelection(categoryId);
            categorySpinner.setClickable(false);
            categorySpinner.setEnabled(false);
            productCostEditText.setText(String.valueOf(product.cost));
            newProduct = product;

        } else { //иначе очищаем ее
            clearAddProductForm();
        }

        addProductForm.setVisibility(View.VISIBLE);
        addProductForm.setClickable(true);
        Button addIngredientButton = findViewById(R.id.add_ingredients_button);
        if (editForm) {
            addIngredientButton.setText("Редактировать ингредиенты");
        } else {
            addIngredientButton.setText("Добавить ингредиенты");
        }
        addIngredientButton.setOnClickListener(v -> showStorage());
        Button cancelButton = findViewById(R.id.product_cancel_button);
        cancelButton.setOnClickListener(v ->
        {
            clearAddProductForm();
            addProductForm.setVisibility(View.INVISIBLE);
            addProductForm.setClickable(false);
        });
        Button submitButton = findViewById(R.id.product_submit_button);
        submitButton.setOnClickListener(v ->
        {
            if (!productNameEditText.getText().toString().isEmpty() &
                    !productCostEditText.getText().toString().isEmpty()) {
                addProductForm.setVisibility(View.INVISIBLE);
                addProductForm.setClickable(false);
                if (editForm) {
                    newProduct.id = product.id;
                    newProduct.categoryId = product.categoryId;
                }
                newProduct.name = productNameEditText.getText().toString();
                newProduct.cost = Double.valueOf(productCostEditText.getText().toString());
                presenter.submitProductFormButClicked(categorySpinner.getSelectedItemPosition(),
                        productId, newProduct, editForm);
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void clearAddProductForm() {
        productCostEditText.setText(null);
        productNameEditText.setText(null);
        categorySpinner.setSelection(0);
        categorySpinner.setClickable(true);
        categorySpinner.setEnabled(true);
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
