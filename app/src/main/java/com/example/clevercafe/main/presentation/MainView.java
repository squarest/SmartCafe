package com.example.clevercafe.main.presentation;

import com.example.clevercafe.menu.presentation.MenuView;

/**
 * Created by Chudofom on 03.10.16.
 */
public interface MainView extends MenuView {
    void setOrder(long orderId);

    void showOrders();
}
