package com.paper.smartcafe.main.presentation.ordersfragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.paper.smartcafe.R;
import com.paper.smartcafe.databinding.OrdersFragmentBinding;
import com.paper.smartcafe.entities.Order;
import com.paper.smartcafe.main.presentation.MainView;
import com.paper.smartcafe.main.presentation.ordersfragment.adapters.OrderListAdapter;
import com.paper.smartcafe.utils.DialogUtil;

import java.util.ArrayList;

/**
 * Created by Chudofom on 10.08.17.
 */

public class OrdersFragment extends MvpAppCompatFragment implements IOrdersFragment {
    private OrdersFragmentBinding binding;
    private ArrayList<Order> orders = new ArrayList<>();
    private MainView mainView;

    @InjectPresenter
    public OrdersPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.orders_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainView = (MainView) getActivity();
        createRecyclerView();
        setClickListeners();
        presenter.viewInit();
    }

    private void setClickListeners() {
        binding.addOrderButton.setOnClickListener(view -> mainView.setOrder(-1));
    }

    private void createRecyclerView() {
        LinearLayoutManager orderLayoutManager = new LinearLayoutManager(getContext());
        binding.orderList.setLayoutManager(orderLayoutManager);
        ItemTouchHelper.Callback callback =
                new SwipeCallback();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.orderList);

    }

    @Override
    public void setOrders(ArrayList<Order> orders) {
        this.orders.clear();
        this.orders.addAll(orders);
        binding.orderList.setAdapter(new OrderListAdapter(this.orders, presenter));

    }

    @Override
    public void setOrder(long orderId) {
        mainView.setOrder(orderId);
    }

    private class SwipeCallback extends ItemTouchHelper.Callback {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return orders.size() > 0;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            DialogUtil.getDeleteAlertDialog(getContext(), "Удаление заказа", "Вы действительно хотите удалить заказ?",
                    (dialogInterface, i) -> {
                        presenter.orderRemoved(viewHolder.getAdapterPosition());
                    }, (dialogInterface, i) -> {
                        presenter.viewInit();
                        dialogInterface.dismiss();
                    }).show();
        }

    }

}
