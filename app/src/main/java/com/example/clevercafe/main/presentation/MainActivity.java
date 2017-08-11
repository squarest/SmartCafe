package com.example.clevercafe.main.presentation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.clevercafe.R;
import com.example.clevercafe.base.BaseActivity;
import com.example.clevercafe.databinding.ActivityMainBinding;
import com.example.clevercafe.main.presentation.orderfragment.OrderFragment;
import com.example.clevercafe.main.presentation.ordersfragment.OrdersFragment;
import com.example.clevercafe.menu.presentation.categories.CategoriesFragment;
import com.example.clevercafe.menu.presentation.products.ProductsFragment;

public class MainActivity extends BaseActivity implements MainView {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        createToolbar("");
        createDrawer();
        setCategories();
        showOrders();
    }

    private void setCategories() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.table_fragment, CategoriesFragment.newInstance(false))
                .commit();
        createToolbar("");
    }

    @Override
    public void showOrders() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.orders_fragment, new OrdersFragment())
                .commit();
    }

    @Override
    public void showProducts(long categoryId, String categoryName) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.table_fragment, ProductsFragment.newInstance(categoryId, false))
                .commit();
        toolbar.setNavigationIcon(R.drawable.back_ic);
        toolbar.setNavigationOnClickListener(view -> setCategories());
        toolbar.setTitle(categoryName);


    }

    @Override
    public void setOrder() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.orders_fragment, new OrderFragment())
                .commit();
    }
}
