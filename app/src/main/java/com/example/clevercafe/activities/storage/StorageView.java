package com.example.clevercafe.activities.storage;

import android.app.Dialog;
import android.os.Bundle;
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
import com.example.clevercafe.activities.BaseActivity;
import com.example.clevercafe.adapters.StorageListAdapter;
import com.example.clevercafe.model.Ingredient;
import com.example.clevercafe.model.IngredientCategory;

import java.util.ArrayList;

public class StorageView extends BaseActivity implements IStorageView {

    private Spinner categorySpinner;
    private Spinner unitsSpinner;
    private EditText ingredientNameEditText;
    private EditText ingredientQuantityEditText;
    ArrayAdapter categorySpinnerAdapter;
    ArrayAdapter unitsSpinnerAdapter;
    StorageListAdapter storageListAdapter;
    private CardView addProductForm;
    private IStoragePresenter presenter;
    ArrayList<String> categoryNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createToolbar();
        createDrawer();
        presenter = new StoragePresenter(this);
        presenter.viewInit();
        TextView addProductButton = (TextView) findViewById(R.id.add_product_button);
        addProductForm = (CardView) findViewById(R.id.add_product_form);
        ingredientNameEditText = (EditText) findViewById(R.id.ingredient_name_edit_text);
        ingredientQuantityEditText = (EditText) findViewById(R.id.ingredient_quantity_edit_text);

        createSpinners();

        TextView addCategoryButton = (TextView) findViewById(R.id.add_category_button);
        addCategoryButton.setOnClickListener(v -> presenter.addCategoryButClicked());

        addProductButton.setOnClickListener(v ->
        {
            presenter.addProductButClicked();
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_storage;
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
                    presenter.editCategoryButClicked(groupId);
                } else {
                    presenter.editProductButClicked(groupId, childId);
                }
                break;
            }
            case 2: {//remove
                //TODO: добавить окно с разрешением на удаление
                if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    presenter.deleteCategoryButClicked(groupId);
                } else {
                    presenter.deleteProductButClicked(groupId, childId);
                }
                break;
            }
//            case 3: {
//            }

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void createAddProductForm(int categoryId, int productId, Ingredient ingredient, boolean editForm) {
        if (editForm) //если форма вызвана для редактирования продукта то заполняем ее данными
        {
            ingredientNameEditText.setText(ingredient.name);
            categorySpinner.setSelection(categoryId);
            categorySpinner.setClickable(false);
            categorySpinner.setEnabled(false);
            ingredientQuantityEditText.setText(String.valueOf(ingredient.quantity));
            unitsSpinner.setSelection(Units.idOfUnit(ingredient.units));
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
            if (!ingredientNameEditText.getText().toString().isEmpty() &
                    !ingredientQuantityEditText.getText().toString().isEmpty()) {
                addProductForm.setVisibility(View.INVISIBLE);
                addProductForm.setClickable(false);
                Ingredient newIngredient = new Ingredient();
                newIngredient.name = ingredientNameEditText.getText().toString();
                newIngredient.quantity = Double.valueOf(ingredientQuantityEditText.getText().toString());
                newIngredient.units = unitsSpinner.getSelectedItem().toString();
                presenter.submitProductFormButClicked(categorySpinner.getSelectedItemPosition(),
                        productId, newIngredient, editForm);
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showCategories(ArrayList<IngredientCategory> categories) {
        ExpandableListView storageList = (ExpandableListView) findViewById(R.id.storage_list);
        storageListAdapter  = new StorageListAdapter(this, categories);
        storageList.setAdapter(storageListAdapter);
        registerForContextMenu(storageList);
        for (IngredientCategory category : categories) {
            categoryNames.add(category.name);
        }
    }

    @Override
    public void updateCategories(ArrayList<IngredientCategory> categories) {
        storageListAdapter.notifyDataSetChanged();
        categoryNames.clear();
        for (IngredientCategory category : categories) {
            categoryNames.add(category.name);
        }
        categorySpinnerAdapter.notifyDataSetChanged();
        unitsSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearAddProductForm() {
        ingredientNameEditText.setText(null);
        ingredientQuantityEditText.setText(null);
        categorySpinner.setSelection(0);
        unitsSpinner.setSelection(0);
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

    private void createSpinners() {
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        categorySpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryNames);
        categorySpinner.setAdapter(categorySpinnerAdapter);
        unitsSpinner = (Spinner) findViewById(R.id.units_spinner);
        unitsSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Units.array);
        unitsSpinner.setAdapter(unitsSpinnerAdapter);
    }

}
