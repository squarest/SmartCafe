package com.example.clevercafe.main.presentation.orderfragment;

import com.arellomobile.mvp.MvpView;
import com.example.clevercafe.entities.Order;

/**
 * Created by Chudofom on 10.08.17.
 */

public interface IOrderFragment extends MvpView {
    void showOrderAlertDialog(String productName, double maxProductCount);

    void setOrder(Order order);

    void showOrders();

    void updateOrder();

    void showMessage(String message);
}
