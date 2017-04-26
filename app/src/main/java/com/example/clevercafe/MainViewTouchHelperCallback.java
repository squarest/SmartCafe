package com.example.clevercafe;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.clevercafe.adapters.OrderListAdapter;
import com.example.clevercafe.activities.main.IMainPresenter;
import com.example.clevercafe.model.Order;

import java.util.ArrayList;

/**
 * Created by Chudofom on 22.09.16.
 */

public class MainViewTouchHelperCallback extends ItemTouchHelper.Callback {

    private IMainPresenter mainPresenter;

    public MainViewTouchHelperCallback(OrderListAdapter adapter, ArrayList<Order> orderList, IMainPresenter mainPresenter) {
        this.mainPresenter=mainPresenter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mainPresenter.itemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        if (mainPresenter.getOrderSize() > 0) {
            return true;
        } else return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        mainPresenter.itemRemoved(viewHolder.getAdapterPosition());

    }

}