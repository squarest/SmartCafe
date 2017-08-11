package com.example.clevercafe.main.presentation.ordersfragment;

import com.arellomobile.mvp.MvpView;
import com.example.clevercafe.entities.Order;

import java.util.ArrayList;

/**
 * Created by Chudofom on 10.08.17.
 */

public interface IOrdersFragment extends MvpView {
    void setOrders(ArrayList<Order> orders);


}
