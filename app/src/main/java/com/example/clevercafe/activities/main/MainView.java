package com.example.clevercafe.activities.main;

import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clevercafe.MyItemTouchHelperCallback;
import com.example.clevercafe.R;
import com.example.clevercafe.RecyclerItemClickListener;
import com.example.clevercafe.adapters.CategoryListAdapter;
import com.example.clevercafe.adapters.DrawerListAdapter;
import com.example.clevercafe.adapters.OrderListAdapter;
import com.example.clevercafe.adapters.ProductListAdapter;
import com.example.clevercafe.model.ProductCategory;
import com.example.clevercafe.model.Order;
import com.example.clevercafe.model.Product;

import java.util.ArrayList;
import java.util.Collections;

public class MainView extends AppCompatActivity implements IMainView {
    private ProductListAdapter productAdapter;
    private CategoryListAdapter categoryAdapter;
    private OrderListAdapter orderListAdapter;
    private boolean categoryOnScreen = true;
    private RecyclerView categoryProductRecyclerView;
    private RecyclerView orderRecyclerView;
    private ArrayList<Order> orderList = new ArrayList<>();
    private IMainPresenter mainPresenter = new MainPresenter(this);
    private Toolbar toolbar;
    private SlidingPaneLayout slidingPaneLayout;
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
        slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.activity_main_sliding_pane);
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelOpened(View panel) {
                toolbar.setNavigationIcon(R.drawable.back_ic);

            }

            @Override
            public void onPanelClosed(View panel) {
                toolbar.setNavigationIcon(R.drawable.burger_menu_ic);
            }
        });
        createRecyclerViews();
        createToolbar();
        mainPresenter.viewInit();
        categoryProductRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                (view, position) -> {
                    mainPresenter.itemClicked(categoryOnScreen, position);
                }));

        TextView addOrderButton = (TextView) findViewById(R.id.add_order_button);
        addOrderButton.setOnClickListener(view -> mainPresenter.addOrderButtonClicked());
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.drawer_list_view);
        View view = getLayoutInflater().inflate(R.layout.drawer_header, null);
        listView.addHeaderView(view);
        listView.setAdapter(new DrawerListAdapter(this, getResources().getStringArray(R.array.drawer_array), fillTitleArray(), itemIcons));
    }

    public void createToolbar() {
        toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.setNavigationIcon(R.drawable.burger_menu_ic);
        toolbar.setNavigationOnClickListener(v ->
        {
            if (slidingPaneLayout.isOpen()) {
                slidingPaneLayout.closePane();
            } else {
                slidingPaneLayout.openPane();
            }
        });
    }

    private ArrayList<String[]> fillTitleArray() {
        ArrayList<String[]> titleArray = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i == 2) {
                String[] storageSubtitles = {"На складе", "Приходные накладные", "Списание"};
                titleArray.add(storageSubtitles);
            } else
            {
                titleArray.add(null);
            }
        }
        return titleArray;
    }

    @Override
    public void showProducts(ArrayList<Product> products) {
        productAdapter = new ProductListAdapter(products);
        categoryProductRecyclerView.setAdapter(productAdapter);
        categoryOnScreen = false;
        toolbar.setNavigationIcon(R.drawable.back_ic);
        toolbar.setNavigationOnClickListener(v -> mainPresenter.backToCategoryButtonClicked());

    }


    @Override
    public void showCategories(ArrayList<ProductCategory> categories) {
        categoryAdapter = new CategoryListAdapter(categories);
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
