package com.example.clevercafe.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Chudofom on 15.08.17.
 */

public class Utility {
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 320);
    }

}
