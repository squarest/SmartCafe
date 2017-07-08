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
import com.example.clevercafe.utils.RecyclerItemClickListener;
import com.example.clevercafe.activities.BaseActivity;
import com.example.clevercafe.activities.IngredientActivity;
import com.example.clevercafe.adapters.CategoryListAdapter;
import com.example.clevercafe.adapters.ProductListAdapter;
import com.example.clevercafe.model.Ingredient;
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
    private ArrayList<Ingredient> ingredients;
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


        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        categorySpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryNames);
        categorySpinner.setAdapter(categorySpinnerAdapter);
        TextView addCategoryButton = (TextView) findViewById(R.id.add_category_button);
        addCategoryButton.setOnClickListener(v -> presenter.addCategoryButClicked());
        ingredients = new ArrayList<>();
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


    public void showStorage(ArrayList<Ingredient> ingredients) {
        Intent intent = new Intent(MenuView.this, IngredientActivity.class);
        intent.putExtra("ingredients", ingredients);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (null == data) {
                ingredients.clear();
                return;
            }
//            TODO:придумать как очищать ингридиенты если для продукта их не выбрано
            ingredients = (ArrayList<Ingredient>) data.getSerializableExtra("ingredients");
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
            if (product.ingredients!=null && product.ingredients.size()>0)
            {
                ingredients=product.ingredients;
            }

        } else { //иначе очищаем ее
            clearAddProductForm();
        }

        addProductForm.setVisibility(View.VISIBLE);
        addProductForm.setClickable(true);
        Button addIngredientButton = (Button) findViewById(R.id.add_ingredients_button);
        if (editForm) {
            addIngredientButton.setText("Редактировать ингредиенты");
        } else {
            addIngredientButton.setText("Добавить ингредиенты");
        }
        addIngredientButton.setOnClickListener(v -> {
            if (ingredients != null) {
                showStorage(ingredients);
            } else {
                showStorage(new ArrayList<>());
            }
        });
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
                if (editForm) {
                    newProduct.id = product.id;
                    newProduct.categoryId = product.categoryId;
                }
                newProduct.name = productNameEditText.getText().toString();
                newProduct.cost = Double.valueOf(productCostEditText.getText().toString());
                newProduct.ingredients = ingredients;
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
        ingredients=new ArrayList<>();
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
