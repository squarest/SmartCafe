package com.paper.smartcafe.data.repositories;

import com.paper.smartcafe.data.dao.AnalyticsDao;
import com.paper.smartcafe.data.dao.DatabaseDao;
import com.paper.smartcafe.data.entities.CompleteOrderProduct;
import com.paper.smartcafe.entities.Analytics;
import com.paper.smartcafe.entities.Ingredient;
import com.paper.smartcafe.entities.Product;
import com.paper.smartcafe.entities.TopProduct;
import com.paper.smartcafe.utils.dateTime.Period;

import java.util.ArrayList;
import java.util.Collections;
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
            if (databaseDao.getProduct(product.productId) != null) {
                TopProduct topProduct = new TopProduct(databaseDao.getProduct(product.productId), product.quantity);
                topProducts.add(topProduct);
                if (topProducts.size() == 5) return topProducts;
            }
        }
        return topProducts;
    }

    public ArrayList<TopProduct> getUnpopularProducts() {
        ArrayList<TopProduct> topProducts = new ArrayList<>();
        List<Product> products = databaseDao.getAllProducts();
        for (Product product : products) {
            double productQuantity = 0.0;
            if (analyticsDao.getProductQuantity(product.id) != null) {
                productQuantity = analyticsDao.getProductQuantity(product.id);
            }
            TopProduct topProduct = new TopProduct(product, productQuantity);
            topProducts.add(topProduct);
        }
        Collections.sort(topProducts, (topProduct, t1) -> Double.compare(topProduct.quantity, t1.quantity));
        if (topProducts.size() > 1 & topProducts.size() < 5) {
            topProducts = new ArrayList<>(topProducts.subList(0, topProducts.size()));
        } else if (topProducts.size() >= 5) {
            topProducts = new ArrayList<>(topProducts.subList(0, 5));
        }
        return topProducts;
    }

    public ArrayList<Ingredient> getExpiringIngredients() {
        ArrayList<Ingredient> list = new ArrayList<>();
        list.addAll(analyticsDao.getExpiringIngredients());
        return list;
    }

}
