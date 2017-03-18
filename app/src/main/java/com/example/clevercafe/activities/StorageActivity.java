package com.example.clevercafe.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clevercafe.R;
import com.example.clevercafe.Units;
import com.example.clevercafe.adapters.StorageListAdapter;
import com.example.clevercafe.model.Ingredient;
import com.example.clevercafe.model.IngredientCategory;

import java.util.ArrayList;

public class StorageActivity extends AppCompatActivity {

    private ArrayList<IngredientCategory> categories;
    private StorageListAdapter storageListAdapter;
    private ArrayAdapter categorySpinnerAdapter;
    private Spinner categorySpinner;
    private Spinner unitsSpinner;
    private EditText ingredientNameEditText;
    private EditText ingredientQuantityEditText;
    private CardView addProductForm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        categories = fillCategories();
        ExpandableListView storageList = (ExpandableListView) findViewById(R.id.storage_list);
        storageListAdapter = new StorageListAdapter(this, categories);
        storageList.setAdapter(storageListAdapter);
        registerForContextMenu(storageList);
        TextView addProductButton = (TextView) findViewById(R.id.add_product_button);
        addProductForm = (CardView) findViewById(R.id.add_product_form);
        ingredientNameEditText = (EditText) findViewById(R.id.ingredient_name_edit_text);
        ingredientQuantityEditText = (EditText) findViewById(R.id.ingredient_quantity_edit_text);

        TextView addCategoryButton = (TextView) findViewById(R.id.add_category_button);
        addCategoryButton.setOnClickListener(v -> createCategoryDialog(null, -1));
        createSpinner();

        addProductButton.setOnClickListener(v ->
        {
            createAddProductForm();
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        int type = ExpandableListView.getPackedPositionType(info.packedPosition);

        menu.add(1, 1, 1, "Редактировать");
        menu.add(2, 2, 2, "Удалить");
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            //menu.add(3, 3, 3, "Cписать");
            menu.setHeaderTitle("Изменение продукта");
        } else {
            menu.setHeaderTitle("Изменение категории");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        int groupId = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        int childId = ExpandableListView.getPackedPositionChild(info.packedPosition);

        switch (item.getItemId()) {
            case 1: {//edit
                if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    createCategoryDialog(categories.get(groupId).name, groupId);
                } else {
                    createEditProductForm(groupId, childId);
                }
                break;
            }
            case 2: {//remove
                //TODO: добавить окно с разрешением на удаление
                if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    categories.remove(groupId);
                } else {
                    categories.get(groupId).ingredients.remove(childId);
                }
                storageListAdapter.notifyDataSetChanged();
                break;
            }
//            case 3: {
//            }

        }
        return super.onContextItemSelected(item);
    }

    private void createAddProductForm() {
        clearAddProductForm();
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
            if (!ingredientNameEditText.getText().toString().isEmpty() & !ingredientQuantityEditText.getText().toString().isEmpty()) {
                addProductForm.setVisibility(View.INVISIBLE);
                addProductForm.setClickable(false);
                addProduct();
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createEditProductForm(int categoryId, int productId) {
        Ingredient ingredient = categories.get(categoryId).ingredients.get(productId);
        ingredientNameEditText.setText(ingredient.name);
        categorySpinner.setSelection(categoryId);
        categorySpinner.setClickable(false);
        ingredientQuantityEditText.setText(String.valueOf(ingredient.quantity));
        unitsSpinner.setSelection(Units.idOfUnit(ingredient.units));

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
            if (!ingredientNameEditText.getText().toString().isEmpty() & !ingredientQuantityEditText.getText().toString().isEmpty()) {
                addProductForm.setVisibility(View.INVISIBLE);
                addProductForm.setClickable(false);
                editProduct(categoryId, productId);
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearAddProductForm() {
        ingredientNameEditText.setText(null);
        ingredientQuantityEditText.setText(null);
        categorySpinner.setSelection(0);
        unitsSpinner.setSelection(0);
    }

    private void addProduct() {

        String ingredientName = ingredientNameEditText.getText().toString();
        double ingredientQuantity = Double.valueOf(ingredientQuantityEditText.getText().toString());
        Ingredient ingredient = new Ingredient(ingredientName, ingredientQuantity, unitsSpinner.getSelectedItem().toString());
        categories.get(categorySpinner.getSelectedItemPosition()).ingredients.add(ingredient);
        storageListAdapter.notifyDataSetChanged();
    }

    private void editProduct(int categoryId, int productId) {

        String ingredientName = ingredientNameEditText.getText().toString();
        double ingredientQuantity = Double.valueOf(ingredientQuantityEditText.getText().toString());
        Ingredient ingredient = new Ingredient(ingredientName, ingredientQuantity, unitsSpinner.getSelectedItem().toString());
        categories.get(categoryId).ingredients.set(productId, ingredient);
        storageListAdapter.notifyDataSetChanged();
    }


    private void createSpinner() {
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        ArrayList<String> categoryNames = new ArrayList<>();
        for (IngredientCategory category : categories) {
            categoryNames.add(category.name);
        }
        categorySpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryNames);
        categorySpinner.setAdapter(categorySpinnerAdapter);
        unitsSpinner = (Spinner) findViewById(R.id.units_spinner);
        ArrayAdapter unitsSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Units.array);
        unitsSpinner.setAdapter(unitsSpinnerAdapter);
    }

    private void createCategoryDialog(String name, int categoryId) {
        Dialog categoryDialog = new Dialog(this);
        categoryDialog.setContentView(R.layout.add_category_dialog);
        EditText categoryNameEditText = (EditText) categoryDialog.findViewById(R.id.add_category_edit_text);

        if (name != null) {
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
                if (name == null & categoryId == -1) {
                    categories.add(new IngredientCategory(categoryName, new ArrayList<Ingredient>()));
                } else {
                    categories.get(categoryId).name = categoryName;
                }
                storageListAdapter.notifyDataSetChanged();
                createSpinner();
                categoryDialog.dismiss();
            } else
                Toast.makeText(this, "Введите название категории", Toast.LENGTH_SHORT).show();
        });

        categoryDialog.show();
    }

    private ArrayList<IngredientCategory> fillCategories() {
        ArrayList<IngredientCategory> categories = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            IngredientCategory category = new IngredientCategory("Категория " + i, fillIngredients());
            categories.add(category);
        }
        return categories;
    }

    private ArrayList<Ingredient> fillIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Ingredient ingredient = new Ingredient("Продукт " + i, 1, Units.count);
            ingredients.add(ingredient);
        }
        return ingredients;
    }
}
