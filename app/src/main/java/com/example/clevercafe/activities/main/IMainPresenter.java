package com.example.clevercafe.activities.main;

import com.example.clevercafe.model.Order;

/**
 * Created by Chudofom on 03.10.16.
 */
public interface IMainPresenter {
    void viewInit();

    void itemClicked(boolean categoryOnscreen, int id);

    void addOrderButtonClicked();

    void submitButtonClicked();

    void cancelButtonClicked();

    void itemMoved(int oldPosition, int newPosition);

    void itemRemoved(int position);

    int getOrderSize();

    void orderSubmitButtonClicked(Order order);

    void backToCategoryButtonClicked();
}
