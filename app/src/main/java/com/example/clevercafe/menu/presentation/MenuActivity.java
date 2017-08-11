package com.example.clevercafe.menu.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.clevercafe.R;
import com.example.clevercafe.base.BaseActivity;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.menu.presentation.addcategory.AddCategoryFragment;
import com.example.clevercafe.menu.presentation.addproduct.AddProductFragment;
import com.example.clevercafe.menu.presentation.categories.CategoriesFragment;
import com.example.clevercafe.menu.presentation.products.ProductsFragment;

/**
 * Created by Chudofom on 20.03.17.
 */

public class MenuActivity extends BaseActivity implements MenuView {
    private AddProductFragment addProductFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        createDrawer();
        setCategories();
    }

    private void setAddProductFragment(long categoryId) {
        addProductFragment = AddProductFragment.newInstance(categoryId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.add_fragment, addProductFragment)
                .commit();
    }

    private void setAddCategoryFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.add_fragment, new AddCategoryFragment())
                .commit();
    }

    private void setCategories() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.table_fragment, CategoriesFragment.newInstance(true))
                .commit();
        createToolbar(getResources().getString(R.string.menu_toolbar_title));
        setAddCategoryFragment();
    }

    @Override
    public void showProducts(long categoryId, String categoryName) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.table_fragment, ProductsFragment.newInstance(categoryId, true))
                .commit();
        toolbar.setNavigationIcon(R.drawable.back_ic);
        toolbar.setNavigationOnClickListener(view -> setCategories());
        toolbar.setTitle(categoryName);
        setAddProductFragment(categoryId);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Product newProduct;
        if (resultCode == RESULT_OK) {
            if (null == data) {
                return;
            }
            newProduct = (Product) data.getSerializableExtra("product");
            addProductFragment.setIngredients(newProduct);
        } else Toast.makeText(this, "Добавление ингедиентов отменено", Toast.LENGTH_SHORT).show();
    }
}
