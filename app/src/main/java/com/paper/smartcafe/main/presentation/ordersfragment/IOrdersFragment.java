package com.paper.smartcafe.main.presentation.ordersfragment;

import com.arellomobile.mvp.MvpView;
import com.paper.smartcafe.entities.Order;

import java.util.ArrayList;

/**
 * Created by Chudofom on 10.08.17.
 */

public interface IOrdersFragment extends MvpView {
    void setOrders(ArrayList<Order> orders);
    void setOrder(long orderId);


}
