package com.example.clevercafe.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.Date;

/**
 * Created by Chudofom on 24.07.17.
 */
@Dao
public interface AnalyticsDao {
    @Query("SELECT SUM(sum) FROM completeOrders WHERE dateTime BETWEEN :from AND :to")
    double proceedsForPeriod(Date from, Date to);

    @Query("SELECT AVG(sum) FROM completeOrders WHERE dateTime BETWEEN :from AND :to")
    double averageProceedsForPeriod(Date from, Date to);

    @Query("SELECT SUM(costSum) FROM completeOrders WHERE dateTime BETWEEN :from AND :to")
    double costSumForPeriod(Date from, Date to);

    @Query("SELECT COUNT(id) FROM completeOrders WHERE dateTime BETWEEN :from AND :to")
    int orderCountForPeriod(Date from, Date to);

}
