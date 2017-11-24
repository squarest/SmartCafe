package com.paper.smartcafe.utils;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Chudofom on 21.08.17.
 */

public class RoomConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
