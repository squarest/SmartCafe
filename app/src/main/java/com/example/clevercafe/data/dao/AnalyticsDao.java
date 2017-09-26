package com.example.clevercafe.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.clevercafe.data.entities.CompleteOrderProduct;
import com.example.clevercafe.entities.Ingredient;

import java.util.Date;
import java.util.List;

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

    @Query("SELECT productId, SUM(quantity) AS quantity FROM completeOrderProducts GROUP BY productId ORDER BY quantity DESC")
    List<CompleteOrderProduct> getPopularProducts();

    @Query("SELECT SUM(quantity) FROM completeOrderProducts WHERE productId = :productId GROUP BY productId")
    Double getProductQuantity(long productId);

    @Query("SELECT * FROM ingredients ORDER BY quantity ASC LIMIT 5")
    List<Ingredient> getExpiringIngredients();

}
