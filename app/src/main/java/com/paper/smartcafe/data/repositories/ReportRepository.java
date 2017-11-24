package com.paper.smartcafe.data.repositories;

import com.paper.smartcafe.data.dao.AnalyticsDao;
import com.paper.smartcafe.data.dao.DatabaseDao;
import com.paper.smartcafe.data.dao.ReportDao;
import com.paper.smartcafe.data.entities.ProductIngredient;
import com.paper.smartcafe.entities.CompleteOrder;
import com.paper.smartcafe.entities.Ingredient;
import com.paper.smartcafe.entities.Product;
import com.paper.smartcafe.entities.TopProduct;
import com.paper.smartcafe.report.entity.StorageReportItem;
import com.paper.smartcafe.utils.dateTime.Period;

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

    public ArrayList<StorageReportItem> getStorageIngredients(Period period) {
        List<Ingredient> ingredients = databaseDao.getAllIngredients();
        ArrayList<StorageReportItem> items = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            StorageReportItem item = new StorageReportItem();
            item.ingredient = ingredient;
            ArrayList<TopProduct> products = getTopOfProducts(period);
            for (TopProduct topProduct : products) {
                if (topProduct.quantity > 0) {
                    for (Ingredient usedIngredient : topProduct.product.ingredients) {
                        if (ingredient.id == usedIngredient.id) {
                            item.expensed += topProduct.product.getIngredientCount(ingredient.id)
                                    * topProduct.quantity;
                        }
                    }
                }
            }
            items.add(item);
        }
        return items;
    }
}
