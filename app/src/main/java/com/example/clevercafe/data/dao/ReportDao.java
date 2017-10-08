package com.example.clevercafe.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.clevercafe.entities.CompleteOrder;

import java.util.Date;
import java.util.List;

/**
 * Created by Chudofom on 06.10.17.
 */
@Dao
public interface ReportDao {
    @Query("SELECT quantity FROM completeOrderProducts WHERE orderId = :orderId AND productId = :productId")
    Double getProductQuantityForOrder(long orderId, long productId);

    @Query("SELECT * FROM completeOrders WHERE dateTime BETWEEN :from AND :to")
    List<CompleteOrder> getCompleteOrdersForPeiod(Date from, Date to);
}
