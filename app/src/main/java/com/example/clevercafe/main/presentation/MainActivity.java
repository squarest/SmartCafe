package com.example.clevercafe.main.presentation;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.clevercafe.R;
import com.example.clevercafe.base.BaseActivity;
import com.example.clevercafe.databinding.ActivityMainBinding;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;
import com.example.clevercafe.main.presentation.adapters.OrderAdapter;
import com.example.clevercafe.main.presentation.adapters.OrderListAdapter;
import com.example.clevercafe.main.presentation.adapters.RecyclerItemClickListener;
import com.example.clevercafe.menu.presentation.categories.CategoryListAdapter;
import com.example.clevercafe.menu.presentation.products.ProductListAdapter;
import com.example.clevercafe.utils.DialogUtil;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends BaseActivity implements MainView {
    private OrderListAdapter orderListAdapter;
    private OrderAdapter orderAdapter;
    private boolean categoryOnScreen = true;
    private ArrayList<Order> orderList = new ArrayList<>();
    private ActivityMainBinding binding;
    private AlertDialog alertDialog;
    @InjectPresenter
    public MainPresenter mainPresenter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = this;
        createToolbar("");
        createDrawer();
        createRecyclerViews();
        setClickListeners();
        mainPresenter.viewInit();
    }

    private void setClickListeners() {
        binding.addOrderButton.setOnClickListener(view -> mainPresenter.addOrderButtonClicked());
        binding.submitButton.setOnClickListener(v -> mainPresenter.submitButtonClicked());
        binding.cancelButton.setOnClickListener(v -> mainPresenter.cancelButtonClicked());
    }


    @Override
    public void showProducts(ArrayList<Product> products) {
        binding.productTable.setAdapter(new ProductListAdapter(products, false));
        categoryOnScreen = false;
        toolbar.setNavigationIcon(R.drawable.back_ic);
        toolbar.setNavigationOnClickListener(v -> mainPresenter.backToCategoryButtonClicked());
    }


    @Override
    public void showCategories(ArrayList<ProductCategory> categories) {
        binding.productTable.setAdapter(new CategoryListAdapter(categories, false));
        categoryOnScreen = true;
        createToolbar("");
    }

    @Override
    public void setOrders(ArrayList<Order> orders) {
        hideButtonPanel();
        orderList.clear();
        orderList.addAll(orders);
        orderListAdapter = new OrderListAdapter(orderList, mainPresenter);
        binding.orderList.setAdapter(orderListAdapter);
        binding.addOrderButton.setText("Новый заказ");
        binding.addOrderButton.setClickable(true);
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
        orderAdapter = new OrderAdapter(order, this, mainPresenter);
        binding.orderList.setAdapter(orderAdapter);
        binding.addOrderButton.setText("ЗАКАЗ №" + order.number);
        binding.addOrderButton.setClickable(false);
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

    @Override
    public void showButtonPanel() {
        binding.buttonPanel.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        binding.buttonPanel.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideButtonPanel() {
        if (binding.buttonPanel != null) {
            binding.buttonPanel.getLayoutParams().height = 0;
            binding.buttonPanel.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showOrderAlertDialog(String productName, double maxProductCount) {
        alertDialog = DialogUtil.getWarningAlertDialog(this, "Недостаточно продуктов",
                "Для добавления товара " + productName + " недостаточно продуктов. \n Возможно добавить только " + maxProductCount,
                (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
        alertDialog.show();
    }

    private void createRecyclerViews() {
        LinearLayoutManager orderLayoutManager = new LinearLayoutManager(this);
        binding.orderList.setLayoutManager(orderLayoutManager);
        ItemTouchHelper.Callback callback =
                new MainViewTouchHelperCallback();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.orderList);
        binding.productTable.setLayoutManager(new GridLayoutManager(this, 3));
        binding.productTable.addOnItemTouchListener(new RecyclerItemClickListener(this,
                (view, position) -> {
                    mainPresenter.itemClicked(categoryOnScreen, position);
                }));
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
            DialogUtil.getDeleteAlertDialog(context, "Удаление заказа", "Вы действительно хотите удалить заказ?",
                    (dialogInterface, i) -> {
                mainPresenter.itemRemoved(viewHolder.getAdapterPosition());
            }, (dialogInterface, i) -> {
                mainPresenter.updateOrders();
                dialogInterface.dismiss();
            }).show();
        }

    }
}
