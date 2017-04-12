package com.example.clevercafe.activities.menu;

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

import com.example.clevercafe.R;
import com.example.clevercafe.RecyclerItemClickListener;
import com.example.clevercafe.activities.BaseActivity;
import com.example.clevercafe.activities.IngredientActivity;
import com.example.clevercafe.adapters.CategoryListAdapter;
import com.example.clevercafe.adapters.ProductListAdapter;
import com.example.clevercafe.model.Product;
import com.example.clevercafe.model.ProductCategory;

import java.util.ArrayList;

/**
 * Created by Chudofom on 20.03.17.
 */

public class MenuView extends BaseActivity implements IMenuView {
    private IMenuPresenter presenter;

    private EditText productNameEditText;
    private EditText productCostEditText;
    private CardView addProductForm;
    private ProductListAdapter productAdapter;
    private CategoryListAdapter categoryAdapter;
    private RecyclerView categoryProductRecyclerView;
    private boolean categoryOnScreen = true;
    ArrayAdapter categorySpinnerAdapter;
    private int curCategoryPosition;
    private Spinner categorySpinner;
    ArrayList<String> categoryNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MenuPresenter(this);
        createToolbar();
        createDrawer();
        categoryProductRecyclerView = (RecyclerView) findViewById(R.id.category_table);
        categoryProductRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        categoryProductRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                (view, position) -> {
                    if (categoryOnScreen) {
                        curCategoryPosition = position;
                    }
                    presenter.itemClicked(categoryOnScreen, position);
                }));


        presenter.viewInit();
        TextView addProductButton = (TextView) findViewById(R.id.add_product_button);
        addProductForm = (CardView) findViewById(R.id.add_product_form);
        productNameEditText = (EditText) findViewById(R.id.ingredient_name_edit_text);
        productCostEditText = (EditText) findViewById(R.id.ingredient_cost_edit_text);
        registerForContextMenu(categoryProductRecyclerView);

        addProductButton.setOnClickListener(v ->
        {
            presenter.addProductButClicked();
        });

        Button addIngredientButton = (Button)findViewById(R.id.add_ingredients_button);
        addIngredientButton.setOnClickListener(v->{
            presenter.addIngredientsButClicked();
        });
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        categorySpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryNames);
        categorySpinner.setAdapter(categorySpinnerAdapter);
        TextView addCategoryButton = (TextView) findViewById(R.id.add_category_button);
        addCategoryButton.setOnClickListener(v -> presenter.addCategoryButClicked());
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
        for (ProductCategory category : categories) {
            categoryNames.add(category.name);
        }
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
                //TODO: добавить окно с разрешением на удаление
                if (categoryOnScreen) {
                    presenter.deleteCategoryButClicked(position);
                } else {
                    presenter.deleteProductButClicked(curCategoryPosition, position);
                }
                break;
            }
//            case 3: {
//            }

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void updateMenu(ArrayList<ProductCategory> categories) {
        if (categoryOnScreen) {
            categoryAdapter.notifyDataSetChanged();
            categoryNames.clear();
            for (ProductCategory category : categories) {
                categoryNames.add(category.name);
            }
            categorySpinnerAdapter.notifyDataSetChanged();
        } else {
            productAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showStorage() {
        Intent intent = new Intent(MenuView.this, IngredientActivity.class);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        } else { //иначе очищаем ее
            clearAddProductForm();
        }

        addProductForm.setVisibility(View.VISIBLE);
        addProductForm.setClickable(true);

        Button cancelButton = (Button) findViewById(R.id.product_cancel_button);
        cancelButton.setOnClickListener(v ->
        {
            clearAddProductForm();
            addProductForm.setVisibility(View.INVISIBLE);
            addProductForm.setClickable(false);
        });
        Button submitButton = (Button) findViewById(R.id.product_submit_button);
        submitButton.setOnClickListener(v ->
        {
            if (!productNameEditText.getText().toString().isEmpty() &
                    !productCostEditText.getText().toString().isEmpty()) {
                addProductForm.setVisibility(View.INVISIBLE);
                addProductForm.setClickable(false);
                Product newProduct = new Product();
                newProduct.name = productNameEditText.getText().toString();
                newProduct.quantity = Double.valueOf(productCostEditText.getText().toString());
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
    }

    @Override
    public void createCategoryDialog(int categoryId, String name, boolean editForm) {
        Dialog categoryDialog = new Dialog(this);
        categoryDialog.setContentView(R.layout.add_category_dialog);
        EditText categoryNameEditText = (EditText) categoryDialog.findViewById(R.id.add_category_edit_text);

        if (editForm) {
            categoryNameEditText.setText(name);
            categoryDialog.setTitle("Изменение категории");
        } else categoryDialog.setTitle("Добавление новой категории");

        Button cancelCategoryButton = (Button) categoryDialog.findViewById(R.id.category_cancel_button);
        cancelCategoryButton.setOnClickListener(v ->
        {
            categoryDialog.dismiss();
        });

        Button submitCategoryButton = (Button) categoryDialog.findViewById(R.id.category_submit_button);
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
