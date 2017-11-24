package com.paper.smartcafe.storage.presentation.storage;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.paper.smartcafe.R;
import com.paper.smartcafe.base.BaseActivity;
import com.paper.smartcafe.databinding.ActivityStorageBinding;
import com.paper.smartcafe.entities.Ingredient;
import com.paper.smartcafe.entities.IngredientCategory;
import com.paper.smartcafe.storage.presentation.adapters.StorageListAdapter;
import com.paper.smartcafe.utils.DialogUtil;
import com.paper.smartcafe.utils.Units;

import java.util.ArrayList;

public class StorageActivity extends BaseActivity implements StorageView {
    private ArrayAdapter categorySpinnerAdapter;
    private ArrayAdapter unitsSpinnerAdapter;
    private StorageListAdapter storageListAdapter;
    private ArrayList<String> categoryNames = new ArrayList<>();
    private ActivityStorageBinding binding;

    @InjectPresenter
    public StoragePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_storage);
        createToolbar("");
        createDrawer();
        createSpinners();
        setClickListeners();

        presenter.viewInit();

    }

    private void setClickListeners() {
        binding.addCategoryButton.setOnClickListener(v -> presenter.addCategoryButClicked());
        binding.addProductButton.setOnClickListener(v -> presenter.addIngredientButClicked());
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
            menu.setHeaderTitle("Изменение продукта");
            menu.add(3, 3, 3, "Cписать");
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
                    presenter.editIngredientButClicked(groupId, childId);
                }
                break;
            }
            case 2: {//remove
                if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    DialogUtil.getDeleteAlertDialog(this, "Удаление категории", "Вы действительно хотите удалить категорию?", (dialogInterface, i) -> {
                        presenter.deleteCategoryButClicked(groupId);
                    }).show();

                } else {
                    DialogUtil.getDeleteAlertDialog(this, "Удаление продукта", "Вы действительно хотите удалить продукт?", (dialogInterface, i) -> {
                        presenter.deleteIngredientButClicked(groupId, childId);
                    }).show();
                }
                break;
            }
            case 3: {//subtract
                if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    presenter.subtractIngredientButClicked(groupId, childId);
                }
                break;
            }

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void createAddIngredientForm(int categoryId, int productId, Ingredient ingredient, boolean editForm) {
        if (editForm) //если форма вызвана для редактирования продукта то заполняем ее данными
        {
            binding.ingredientName.setText(ingredient.name);
            binding.categorySpinner.setSelection(categoryId);
            binding.categorySpinner.setClickable(false);
            binding.categorySpinner.setEnabled(false);
            binding.ingredientQuantity.setText(String.valueOf(ingredient.quantity));
            binding.unitsSpinner.setSelection(Units.idOfUnit(ingredient.units));
            binding.ingredientCost.setText(String.valueOf(ingredient.cost));
        } else { //иначе очищаем ее
            clearAddIngredientForm();
        }
        binding.ingredientCost.setHint(String.format("Цена за %s", Units.units[binding.unitsSpinner.getSelectedItemPosition()]));
        binding.addProductForm.setVisibility(View.VISIBLE);
        binding.addProductForm.setClickable(true);

        binding.productCancelButton.setOnClickListener(v ->
        {
            clearAddIngredientForm();
            binding.addProductForm.setVisibility(View.INVISIBLE);
            binding.addProductForm.setClickable(false);
        });
        binding.productSubmitButton.setOnClickListener(v ->
        {
            String ingredientName = binding.ingredientName.getText().toString();
            String ingredientQuantity = binding.ingredientQuantity.getText().toString();
            String ingredientCost = binding.ingredientCost.getText().toString();
            if (!ingredientName.isEmpty() & !ingredientQuantity.isEmpty() & !ingredientCost.isEmpty()
                    & !ingredientCost.equals(".") & !ingredientQuantity.equals(".")) {
                binding.addProductForm.setVisibility(View.INVISIBLE);
                binding.addProductForm.setClickable(false);
                Ingredient newIngredient = new Ingredient();
                if (editForm) {
                    newIngredient.id = ingredient.id;
                    newIngredient.categoryId = ingredient.categoryId;
                }
                newIngredient.name = ingredientName;
                newIngredient.quantity = Double.valueOf(ingredientQuantity);
                newIngredient.units = binding.unitsSpinner.getSelectedItem().toString();
                newIngredient.cost = Double.valueOf(ingredientCost);
                presenter.submitIngredientFormButClicked(binding.categorySpinner.getSelectedItemPosition(),
                        newIngredient, editForm);
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showCategories(ArrayList<IngredientCategory> categories) {
        storageListAdapter = new StorageListAdapter(categories);
        binding.storageList.setAdapter(storageListAdapter);
        registerForContextMenu(binding.storageList);
        updateSpinners(categories);
    }

    @Override
    public void updateCategories(ArrayList<IngredientCategory> categories) {
        storageListAdapter.notifyDataSetChanged();
        updateSpinners(categories);

    }

    private void updateSpinners(ArrayList<IngredientCategory> categories) {
        categoryNames.clear();
        for (IngredientCategory category : categories) {
            categoryNames.add(category.name);
        }
        categorySpinnerAdapter.notifyDataSetChanged();
        unitsSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void clearAddIngredientForm() {
        binding.ingredientName.setText(null);
        binding.ingredientQuantity.setText(null);
        binding.ingredientCost.setText(null);
        binding.categorySpinner.setSelection(0);
        binding.unitsSpinner.setSelection(0);
        binding.categorySpinner.setClickable(true);
        binding.categorySpinner.setEnabled(true);
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

    @Override
    public void showSubtractDialog(Ingredient ingredient) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.subtract_product_dialog);
        EditText ingredientQuantity = dialog.findViewById(R.id.ingredient_quantity);
        TextView ingredientUnits = dialog.findViewById(R.id.ingredient_units);

        dialog.setTitle("Списание " + ingredient.name);
        ingredientUnits.setText(ingredient.units);

        Button cancelCategoryButton = dialog.findViewById(R.id.cancel_button);
        cancelCategoryButton.setOnClickListener(v ->
        {
            dialog.dismiss();
        });

        Button submitCategoryButton = dialog.findViewById(R.id.submit_button);
        submitCategoryButton.setOnClickListener(v ->
        {

            if (!ingredientQuantity.getText().toString().isEmpty()) {
                ingredient.quantity = Double.valueOf(ingredientQuantity.getText().toString());
                presenter.ingredientSubtracted(ingredient);
                dialog.dismiss();
            } else
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        });

        dialog.show();

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWarningDialog(String title, String message) {
        DialogUtil.getWarningAlertDialog(this, title, message, (dialogInterface, i) -> dialogInterface.dismiss()).show();
    }

    private void createSpinners() {
        categorySpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoryNames);
        unitsSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Units.units);
        binding.categorySpinner.setAdapter(categorySpinnerAdapter);
        binding.unitsSpinner.setAdapter(unitsSpinnerAdapter);
        binding.unitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.ingredientCost.setHint(String.format("Цена за %s", Units.units[i]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
