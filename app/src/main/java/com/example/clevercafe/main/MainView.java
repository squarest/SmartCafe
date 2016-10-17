package com.example.clevercafe.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clevercafe.MyItemTouchHelperCallback;
import com.example.clevercafe.R;
import com.example.clevercafe.RecyclerItemClickListener;
import com.example.clevercafe.adapters.CategoryListAdapter;
import com.example.clevercafe.adapters.DrawerListAdapter;
import com.example.clevercafe.adapters.OrderListAdapter;
import com.example.clevercafe.adapters.ProductListAdapter;
import com.example.clevercafe.model.Category;
import com.example.clevercafe.model.Order;
import com.example.clevercafe.model.Product;

import java.util.ArrayList;

public class MainView extends AppCompatActivity implements IMainView {
    private ProductListAdapter productAdapter;
    private CategoryListAdapter categoryAdapter;
    private OrderListAdapter orderListAdapter;
    private boolean categoryOnScreen = true;
    private RecyclerView categoryProductRecyclerView;
    private RecyclerView orderRecyclerView;
    private ArrayList<Order> orderList = new ArrayList<>();
    private IMainPresenter mainPresenter = new MainPresenter(this);
    private Integer[] itemIcons = {
            R.drawable.home_ic,
            R.drawable.menu_ic,
            R.drawable.storage_ic,
            R.drawable.director_ic,
            R.drawable.setting_ic,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createRecyclerViews();
        mainPresenter.viewInit();
        categoryProductRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                (view, position) -> {
                    mainPresenter.itemClicked(categoryOnScreen, position);
                }));

        TextView addOrderButton = (TextView) findViewById(R.id.add_order_button);
        addOrderButton.setOnClickListener(view -> mainPresenter.addOrderButtonClicked());
        ListView listView = (ListView) findViewById(R.id.drawer_list_view);
        listView.setAdapter(new DrawerListAdapter(this, getResources().getStringArray(R.array.drawer_array), itemIcons));
    }

    @Override
    public void showProducts(ArrayList<Product> products) {
        productAdapter = new ProductListAdapter(products);
        categoryProductRecyclerView.setAdapter(productAdapter);
        categoryOnScreen = false;

    }


    @Override
    public void showCategories(ArrayList<Category> categories) {
        categoryAdapter = new CategoryListAdapter(categories);
        categoryProductRecyclerView.setAdapter(categoryAdapter);
        categoryOnScreen = true;
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
