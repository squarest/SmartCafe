package com.example.clevercafe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.clevercafe.R;
import com.example.clevercafe.activities.main.MainView;
import com.example.clevercafe.activities.menu.MenuView;
import com.example.clevercafe.adapters.DrawerListAdapter;

import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity {
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
        setContentView(getLayoutResourceId());
    }

    protected abstract int getLayoutResourceId();

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
        listView.setAdapter(new DrawerListAdapter(this, getResources().getStringArray(R.array.drawer_array), fillTitleArray(), itemIcons));
        listView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            switch (groupPosition) {
                case 0: {
                    Intent intent = new Intent(this, MainView.class);
                    this.startActivity(intent);
                    break;
                }
                case 1: {
                    Intent intent = new Intent(this, MenuView.class);
                    this.startActivity(intent);
                    break;
                }

            }
            return false;
        });
    }

    protected void createToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.burger_menu_ic);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingPaneLayout.isOpen()) {
                    slidingPaneLayout.closePane();
                } else {
                    slidingPaneLayout.openPane();
                }
            }

        });
    }

    private ArrayList<String[]> fillTitleArray() {
        ArrayList<String[]> titleArray = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i == 2) {
                String[] storageSubtitles = {"На складе", "Приходные накладные", "Списание"};
                titleArray.add(storageSubtitles);
            } else {
                titleArray.add(null);
            }
        }
        return titleArray;
    }
}
