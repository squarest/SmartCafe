package com.paper.smartcafe.main.presentation.orderfragment;

import com.arellomobile.mvp.MvpView;
import com.paper.smartcafe.entities.Order;

/**
 * Created by Chudofom on 10.08.17.
 */

public interface IOrderFragment extends MvpView {
    void showOrderAlertDialog(String productName, double maxProductCount);

    void setOrder(Order order);

    void showOrders();

    void updateOrder(Order order);

    void showMessage(String message);

    void showCommentDialog(long productId, String comment);
}
