package com.example.clevercafe;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Chudofom on 22.09.16.
 */

public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final OrderListAdapter orderAdapter;
    private ArrayList<String> orderList;

    public MyItemTouchHelperCallback(OrderListAdapter adapter, ArrayList<String> orderList) {
        orderAdapter = adapter;
        this.orderList = orderList;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Collections.swap(orderList, viewHolder.getAdapterPosition(), target.getAdapterPosition());
        orderAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        if (orderList.size() > 1) {
            return true;
        } else return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        orderList.remove(viewHolder.getAdapterPosition());
        orderAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

    }

}