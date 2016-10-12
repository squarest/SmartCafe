package com.example.clevercafe.main;

/**
 * Created by Chudofom on 03.10.16.
 */
public interface IMainPresenter {
    void viewInit();

    void itemClicked(boolean categoryOnscreen, int id);

    void addOrderButtonClicked();

    void backToCategoryButtonClicked();
}
