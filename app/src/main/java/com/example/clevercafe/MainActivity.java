package com.example.clevercafe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<String> orderList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            orderList.add("Заказ №" + i);
        }
        ArrayList<String> categoryList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            categoryList.add("Категория №" + i);
        }
        RecyclerView orderRecyclerView = (RecyclerView) findViewById(R.id.order_list);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final OrderListAdapter orderListAdapter = new OrderListAdapter(orderList);
        orderRecyclerView.setAdapter(orderListAdapter);
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(orderListAdapter, orderList);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(orderRecyclerView);
        TextView addOrderButton = (TextView)findViewById(R.id.add_order_button);
        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderList.add("Заказ №" + (orderList.size()+1));
                orderListAdapter.notifyItemInserted(orderList.size());
            }
        });
        RecyclerView productRecyclerView = (RecyclerView) findViewById(R.id.product_table);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        ProductListAdapter productListAdapter = new ProductListAdapter(categoryList);
        productRecyclerView.setAdapter(productListAdapter);
    }
}
