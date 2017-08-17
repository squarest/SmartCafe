package com.example.clevercafe.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.example.clevercafe.R;
import com.example.clevercafe.base.adapters.DrawerListAdapter;
import com.example.clevercafe.main.presentation.MainActivity;
import com.example.clevercafe.menu.presentation.MenuActivity;

import java.util.ArrayList;


public abstract class BaseActivity extends MvpAppCompatActivity {
    protected Toolbar toolbar;
    private SlidingPaneLayout slidingPaneLayout;
    private Integer[] itemIcons = {
            R.drawable.home_ic,
            R.drawable.menu_ic,
            R.drawable.storage_ic,
            R.drawable.director_ic,
            R.drawable.setting_ic,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    protected void createDrawer() {
        slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.activity_main_sliding_pane);
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelOpened(View panel) {
                toolbar.setNavigationIcon(R.drawable.back_ic);

            }

            @Override
            public void onPanelClosed(View panel) {
                toolbar.setNavigationIcon(R.drawable.burger_menu_ic);
            }
        });
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.drawer_list_view);
        View view = getLayoutInflater().inflate(R.layout.drawer_header, null);
        listView.addHeaderView(view);
        listView.setAdapter(new DrawerListAdapter(getResources().getStringArray(R.array.drawer_array),
                fillTitleArray(), itemIcons));
        listView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            switch (groupPosition) {
                case 0: {
                    Intent intent = new Intent(this, MainActivity.class);
                    this.startActivity(intent);
                    break;
                }
                case 1: {
                    Intent intent = new Intent(this, MenuActivity.class);
                    this.startActivity(intent);
                    break;
                }

            }
            return false;
        });
    }

    protected void createToolbar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.darkBlue));
        toolbar.setNavigationIcon(R.drawable.burger_menu_ic);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            if (slidingPaneLayout.isOpen()) {
                slidingPaneLayout.closePane();
            } else {
                slidingPaneLayout.openPane();
            }
        });
    }

    private ArrayList<String[]> fillTitleArray() {
        ArrayList<String[]> titleArray = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i == 2) {
                String[] storageSubtitles = {"На складе", "Приходные накладные"};
                titleArray.add(storageSubtitles);
            } else if (i == 3) {
                String[] storageSubtitles = {"Состояние предприятия", "Отчеты"};
                titleArray.add(storageSubtitles);
            } else {
                titleArray.add(null);
            }
        }
        return titleArray;
    }
}
