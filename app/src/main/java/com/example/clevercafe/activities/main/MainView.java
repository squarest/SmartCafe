package com.example.clevercafe.activities.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clevercafe.MyItemTouchHelperCallback;
import com.example.clevercafe.R;
import com.example.clevercafe.RecyclerItemClickListener;
import com.example.clevercafe.activities.BaseActivity;
import com.example.clevercafe.adapters.CategoryListAdapter;
import com.example.clevercafe.adapters.OrderListAdapter;
import com.example.clevercafe.adapters.ProductListAdapter;
import com.example.clevercafe.model.Order;
import com.example.clevercafe.model.Product;
import com.example.clevercafe.model.ProductCategory;

import java.util.ArrayList;
import java.util.Collections;

public class MainView extends BaseActivity implements IMainView {
    private ProductListAdapter productAdapter;
    private CategoryListAdapter categoryAdapter;
    private OrderListAdapter orderListAdapter;
    private boolean categoryOnScreen = true;
    private RecyclerView categoryProductRecyclerView;
    private RecyclerView orderRecyclerView;
    private ArrayList<Order> orderList = new ArrayList<>();
    private IMainPresenter mainPresenter = new MainPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createRecyclerViews();
        createToolbar();
        createDrawer();
        mainPresenter.viewInit();
        categoryProductRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                (view, position) -> {
                    mainPresenter.itemClicked(categoryOnScreen, position);
                }));

        TextView addOrderButton = (TextView) findViewById(R.id.add_order_button);
        addOrderButton.setOnClickListener(view -> mainPresenter.addOrderButtonClicked());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void showProducts(ArrayList<Product> products) {
        productAdapter = new ProductListAdapter(products, false);
        categoryProductRecyclerView.setAdapter(productAdapter);
        categoryOnScreen = false;
        toolbar.setNavigationIcon(R.drawable.back_ic);
        toolbar.setNavigationOnClickListener(v -> mainPresenter.backToCategoryButtonClicked());

    }


    @Override
    public void showCategories(ArrayList<ProductCategory> categories) {
        categoryAdapter = new CategoryListAdapter(categories, false);
        categoryProductRecyclerView.setAdapter(categoryAdapter);
        categoryOnScreen = true;
        createToolbar();
    }

    @Override
    public void showOrders(ArrayList<Order> orders) {
        orderList.clear();
        orderList.addAll(orders);
        orderListAdapter.notifyDataSetChanged();

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveOrder(int start, int finish) {
        Collections.swap(orderList, start, finish);
        orderListAdapter.notifyItemMoved(start, finish);

    }

    @Override
    public void removeOrder(int position) {
        orderList.remove(position);
        orderListAdapter.notifyItemRemoved(position);
    }

    private void createRecyclerViews() {
        orderRecyclerView = (RecyclerView) findViewById(R.id.order_list);
        LinearLayoutManager orderLayoutManager = new LinearLayoutManager(this);
        orderRecyclerView.setLayoutManager(orderLayoutManager);
        orderListAdapter = new OrderListAdapter(this, orderList, mainPresenter);
        orderRecyclerView.setAdapter(orderListAdapter);
        ItemTouchHelper.Callback callback =
                new MyItemTouchHelperCallback(orderListAdapter, orderList, mainPresenter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(orderRecyclerView);
        categoryProductRecyclerView = (RecyclerView) findViewById(R.id.product_table);
        categoryProductRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }
}
