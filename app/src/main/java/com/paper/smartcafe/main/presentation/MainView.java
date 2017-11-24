package com.paper.smartcafe.main.presentation;

import com.paper.smartcafe.menu.presentation.MenuView;

/**
 * Created by Chudofom on 03.10.16.
 */
public interface MainView extends MenuView {
    void setOrder(long orderId);

    void showOrders();
}
