package com.example.clevercafe.main.presentation;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clevercafe.R;
import com.example.clevercafe.adapters.CategoryListAdapter;
import com.example.clevercafe.adapters.ProductListAdapter;
import com.example.clevercafe.base.BaseActivity;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;
import com.example.clevercafe.main.presentation.adapters.OrderAdapter;
import com.example.clevercafe.main.presentation.adapters.OrderListAdapter;
import com.example.clevercafe.main.presentation.adapters.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends BaseActivity implements MainView {
    private ProductListAdapter productAdapter;
    private CategoryListAdapter categoryAdapter;
    private OrderListAdapter orderListAdapter;
    private OrderAdapter orderAdapter;
    private boolean categoryOnScreen = true;
    private RecyclerView categoryProductRecyclerView;
    private RecyclerView orderRecyclerView;
    private ArrayList<Order> orderList = new ArrayList<>();
    private IMainPresenter mainPresenter = new MainPresenter(this);
    private LinearLayout buttonPanel;
    private TextView addOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createRecyclerViews();
        createToolbar();
        createDrawer();
        categoryProductRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                (view, position) -> {
                    mainPresenter.itemClicked(categoryOnScreen, position);
                }));

        addOrderButton = (TextView) findViewById(R.id.add_order_button);
        addOrderButton.setOnClickListener(view -> mainPresenter.addOrderButtonClicked());

        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> mainPresenter.submitButtonClicked());
        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> mainPresenter.cancelButtonClicked());
        mainPresenter.viewInit();

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
    public void setOrders(ArrayList<Order> orders) {
        hideButtonPanel();
        orderList.clear();
        orderList.addAll(orders);
        orderListAdapter = new OrderListAdapter(this, orderList, mainPresenter);
        orderRecyclerView.setAdapter(orderListAdapter);
        addOrderButton.setText("ДОБАВИТЬ");
        addOrderButton.setClickable(true);
    }

    @Override
    public void updateOrders(ArrayList<Order> orders) {
        orderList.clear();
        orderList.addAll(orders);
        orderListAdapter.notifyDataSetChanged();

    }

    @Override
    public void updateOrder(Order order) {
        orderAdapter.notifyDataSetChanged();

    }

    @Override
    public void setOrder(Order order) {
        orderAdapter = new OrderAdapter(order, this);
        orderRecyclerView.setAdapter(orderAdapter);
        showButtonPanel();
        addOrderButton.setText("ЗАКАЗ №" + order.id);
        addOrderButton.setClickable(false);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        Toast.makeText(this, "showProgress", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void hideProgress() {
        Toast.makeText(this, "hideProgress", Toast.LENGTH_SHORT).show();
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

    private void showButtonPanel() {
        buttonPanel = (LinearLayout) findViewById(R.id.button_panel);
        buttonPanel.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        buttonPanel.setVisibility(View.VISIBLE);
    }

    public void hideButtonPanel() {
        if (buttonPanel != null) {
            buttonPanel.getLayoutParams().height = 0;
            buttonPanel.setVisibility(View.INVISIBLE);
        }
    }

    private void createRecyclerViews() {
        orderRecyclerView = (RecyclerView) findViewById(R.id.order_list);
        LinearLayoutManager orderLayoutManager = new LinearLayoutManager(this);
        orderRecyclerView.setLayoutManager(orderLayoutManager);
        ItemTouchHelper.Callback callback =
                new MainViewTouchHelperCallback();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(orderRecyclerView);
        categoryProductRecyclerView = (RecyclerView) findViewById(R.id.product_table);
        categoryProductRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private class MainViewTouchHelperCallback extends ItemTouchHelper.Callback {

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            mainPresenter.itemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return mainPresenter.getOrderSize() > 0;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            mainPresenter.itemRemoved(viewHolder.getAdapterPosition());

        }

    }
}
