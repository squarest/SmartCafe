package com.paper.smartcafe.menu.presentation.addcategory;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.paper.smartcafe.R;
import com.paper.smartcafe.utils.RecyclerItemClickListener;
import com.paper.smartcafe.utils.Utility;

import java.io.IOException;

public class IconsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icons);
        AssetManager assetManager = getAssets();
        String[] icons;
        try {
            icons = assetManager.list("icons");
            setTable(icons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTable(String[] icons) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.icon_grid);
        recyclerView.setLayoutManager(new GridLayoutManager(this, Utility.calculateNoOfColumns(this) + 1));
        recyclerView.setAdapter(new IconsAdapter(icons));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, (view, position) -> {
            Intent intent = new Intent();
            intent.putExtra("iconPath", icons[position]);
            setResult(RESULT_OK, intent);
            finish();
        }));
    }
}
