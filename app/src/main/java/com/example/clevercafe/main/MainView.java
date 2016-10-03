package com.example.clevercafe.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clevercafe.MyItemTouchHelperCallback;
import com.example.clevercafe.adapters.CategoryListAdapter;
import com.example.clevercafe.adapters.OrderListAdapter;
import com.example.clevercafe.adapters.ProductListAdapter;
import com.example.clevercafe.R;
import com.example.clevercafe.RecyclerItemClickListener;
import com.example.clevercafe.model.Category;
import com.example.clevercafe.model.Product;

import java.util.ArrayList;

public class MainView extends AppCompatActivity implements IMainView {
    private ProductListAdapter productAdapter;
    private CategoryListAdapter categoryAdapter;
    private boolean categoryOnScreen = true;
    private RecyclerView categoryProductRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final IMainPresenter mainPresenter = new MainPresenter(this);

        final ArrayList<String> orderList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            orderList.add("Заказ №" + i);
        }
        RecyclerView orderRecyclerView = (RecyclerView) findViewById(R.id.order_list);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final OrderListAdapter orderListAdapter = new OrderListAdapter(orderList);
        orderRecyclerView.setAdapter(orderListAdapter);
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(orderListAdapter, orderList);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(orderRecyclerView);
        TextView addOrderButton = (TextView) findViewById(R.id.add_order_button);


        categoryProductRecyclerView = (RecyclerView) findViewById(R.id.product_table);
        categoryProductRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mainPresenter.viewInit();
        categoryProductRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mainPresenter.itemClicked(categoryOnScreen, position);
                        Toast.makeText(MainView.this, "clicked", Toast.LENGTH_SHORT).show();
                    }
                }));
        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderList.add("Заказ №" + (orderList.size() + 1));
                orderListAdapter.notifyItemInserted(orderList.size());
            }
        });
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
        categoryOnScreen=true;
    }

    @Override
    public void addProductToOrder(Product product) {

    }
}
