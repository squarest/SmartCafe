package com.example.clevercafe.utils;

import java.util.Date;

/**
 * Created by Chudofom on 07.07.17.
 */

public class DateTimeUtil {
    public static long getCurrentTimeInMils()
    {
        Date date = new Date();
        return date.getTime();
    }
}
