package com.example.clevercafe.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Chudofom on 31.07.17.
 */

public class DialogUtil {

    public static AlertDialog getDeleteAlertDialog(Context context, String title, String message,
                                                   DialogInterface.OnClickListener positiveListener) {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Удалить", positiveListener)
                .setNegativeButton("Отмена", (dialogInterface, i) -> dialogInterface.dismiss())
                .create();
    }    public static AlertDialog getDeleteAlertDialog(Context context, String title, String message,
                                                        DialogInterface.OnClickListener positiveListener,
                                                        DialogInterface.OnClickListener negativeListener) {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Удалить", positiveListener)
                .setNegativeButton("Отмена", negativeListener)
                .create();
    }
}