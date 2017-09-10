package com.example.clevercafe.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Chudofom on 15.08.17.
 */

public class Utility {
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 280);
    }

    public static Bitmap loadIconFromAssets(Context context, String fileName) {

        AssetManager assetmanager = context.getAssets();
        InputStream is = null;
        try {

            is = assetmanager.open("icons/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(is);
    }
}
