package com.example.clevercafe.data.repositories;

import com.example.clevercafe.data.dao.DatabaseDao;
import com.example.clevercafe.data.entities.CompleteOrderProduct;
import com.example.clevercafe.entities.CompleteOrder;
import com.example.clevercafe.entities.Order;

import java.util.Calendar;

/**
 * Created by Chudofom on 07.07.17.
 */

public class CompleteOrderRepository {
    public DatabaseDao databaseDao;

    public CompleteOrderRepository(DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
    }

    public void addCompleteOrder(Order order) {
        CompleteOrder completeOrder = new CompleteOrder(order.costSum, order.sum, order.products);
        completeOrder.dateTime = Calendar.getInstance().getTime();
        completeOrder.id = databaseDao.insertCompleteOrder(completeOrder);
        if (order.products != null && order.products.size() > 0) addProducts(order);
    }


    private void addProducts(Order order) {
        for (int i = 0; i < order.products.size(); i++) {
            CompleteOrderProduct orderProduct = new CompleteOrderProduct(order.id, order.products.get(i).id,
                    order.getProductCount(order.products.get(i).id));
            databaseDao.insertCompleteOrderProduct(orderProduct);
        }
    }
}
