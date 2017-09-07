package com.example.clevercafe.data.repositories;

import com.example.clevercafe.data.dao.AnalyticsDao;
import com.example.clevercafe.data.dao.DatabaseDao;
import com.example.clevercafe.data.entities.CompleteOrderProduct;
import com.example.clevercafe.entities.Analytics;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.TopProduct;
import com.example.clevercafe.utils.dateTime.Period;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chudofom on 22.08.17.
 */

public class AnalyticsRepository {
    private AnalyticsDao analyticsDao;
    private DatabaseDao databaseDao;

    public AnalyticsRepository(AnalyticsDao analyticsDao, DatabaseDao databaseDao) {
        this.analyticsDao = analyticsDao;
        this.databaseDao = databaseDao;
    }

    public Analytics getAnalyticsForPeriod(Period period) {
        Analytics analytics = new Analytics();
        analytics.proceeds = getProceedForPeriod(period);
        analytics.profit = analytics.proceeds -
                analyticsDao.costSumForPeriod(period.startDate, period.endDate);
        analytics.orders = analyticsDao.orderCountForPeriod(period.startDate, period.endDate);
        analytics.averageCheck = analyticsDao.averageProceedsForPeriod(period.startDate, period.endDate);
        return analytics;
    }

    public Double getProceedForPeriod(Period period) {
        return analyticsDao.proceedsForPeriod(period.startDate, period.endDate);
    }

    public Double getProfitForPeriod(Period period) {
        return analyticsDao.proceedsForPeriod(period.startDate, period.endDate) -
                analyticsDao.costSumForPeriod(period.startDate, period.endDate);
    }

    public Integer getOrdersForPeriod(Period period) {
        return analyticsDao.orderCountForPeriod(period.startDate, period.endDate);
    }

    public Double getAverageForPeriod(Period period) {
        return analyticsDao.averageProceedsForPeriod(period.startDate, period.endDate);
    }

    public ArrayList<TopProduct> getPopularProducts() {
        ArrayList<TopProduct> topProducts = new ArrayList<>();
        List<CompleteOrderProduct> completeOrderProducts = analyticsDao.getPopularProducts();
        for (CompleteOrderProduct product : completeOrderProducts) {
            TopProduct topProduct = new TopProduct(databaseDao.getProduct(product.productId), product.quantity);
            topProducts.add(topProduct);
        }
        return topProducts;
    }

    public ArrayList<TopProduct> getUnpopularProducts() {
        ArrayList<TopProduct> topProducts = new ArrayList<>();
        List<CompleteOrderProduct> completeOrderProducts = analyticsDao.getUnpopularProducts();
        for (CompleteOrderProduct product : completeOrderProducts) {
            TopProduct topProduct = new TopProduct(databaseDao.getProduct(product.productId), product.quantity);
            topProducts.add(topProduct);
        }
        return topProducts;
    }

    public ArrayList<Ingredient> getExpiringIngredients() {
        ArrayList list = new ArrayList();
        list.addAll(analyticsDao.getExpiringIngredients());
        return list;
    }

}
