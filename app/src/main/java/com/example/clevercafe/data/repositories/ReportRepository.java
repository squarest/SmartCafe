package com.example.clevercafe.data.repositories;

import com.example.clevercafe.data.dao.AnalyticsDao;
import com.example.clevercafe.data.dao.DatabaseDao;
import com.example.clevercafe.data.dao.ReportDao;
import com.example.clevercafe.data.entities.ProductIngredient;
import com.example.clevercafe.entities.CompleteOrder;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.TopProduct;
import com.example.clevercafe.utils.dateTime.Period;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Chudofom on 06.10.17.
 */

public class ReportRepository {
    public ReportDao reportDao;
    public AnalyticsDao analyticsDao;
    public DatabaseDao databaseDao;

    public ReportRepository(AnalyticsDao analyticsDao, ReportDao reportDao, DatabaseDao databaseDao) {
        this.analyticsDao = analyticsDao;
        this.reportDao = reportDao;
        this.databaseDao = databaseDao;
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

    public ArrayList<TopProduct> getTopOfProducts(Period period) {
        ArrayList<TopProduct> topProducts = new ArrayList<>();
        List<Product> products = databaseDao.getAllProducts();
        List<CompleteOrder> orders = reportDao.getCompleteOrdersForPeiod(period.startDate, period.endDate);
        for (Product product : products) {
            product.ingredients = getIngredients(product);
            double productQuantity = 0.0;
            for (CompleteOrder order : orders) {
                if (reportDao.getProductQuantityForOrder(order.id, product.id) != null) {
                    productQuantity += reportDao.getProductQuantityForOrder(order.id, product.id);
                }
            }
            TopProduct topProduct = new TopProduct(product, productQuantity);
            topProducts.add(topProduct);
        }
        Collections.sort(topProducts, (topProduct, t1) -> Double.compare(t1.quantity, topProduct.quantity));
        return topProducts;
    }

    private ArrayList<Ingredient> getIngredients(Product product) {
        ArrayList<ProductIngredient> productIngredients = (ArrayList<ProductIngredient>) databaseDao.getProductIngredients(product.id);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        if (productIngredients != null && productIngredients.size() > 0) {
            for (ProductIngredient productIngredient : productIngredients) {
                ingredients.add(databaseDao.getIngredient(productIngredient.ingredientId));
                product.setIngredientCount(productIngredient.ingredientId, productIngredient.quantity);
            }
        }
        return ingredients;
    }

}
