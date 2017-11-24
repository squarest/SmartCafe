package com.paper.smartcafe.main.presentation;

import android.os.Bundle;

import com.paper.smartcafe.R;
import com.paper.smartcafe.base.BaseActivity;
import com.paper.smartcafe.main.presentation.orderfragment.OrderFragment;
import com.paper.smartcafe.main.presentation.ordersfragment.OrdersFragment;
import com.paper.smartcafe.menu.presentation.categories.CategoriesFragment;
import com.paper.smartcafe.menu.presentation.products.ProductsFragment;

public class MainActivity extends BaseActivity implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void setOrder(long orderId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.orders_fragment, OrderFragment.newInstance(orderId))
                .commit();
    }
}
