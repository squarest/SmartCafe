package com.example.clevercafe.report.domain;

import com.example.clevercafe.data.repositories.ReportRepository;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.TopProduct;
import com.example.clevercafe.report.entity.GeneralReportItem;
import com.example.clevercafe.report.entity.ProductReportItem;
import com.example.clevercafe.report.entity.StorageReportItem;
import com.example.clevercafe.utils.ReportUtil;
import com.example.clevercafe.utils.dateTime.Period;

import java.util.ArrayList;

import io.reactivex.Single;

/**
 * Created by Chudofom on 05.10.17.
 */

public class ReportInteractor implements IReportInteractor {
    public ReportRepository repository;

    public ReportInteractor(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<ArrayList<GeneralReportItem>> loadGeneralReport(Period period, int periodType) {
        return Single.create(e -> {
            ArrayList<GeneralReportItem> reportItems = new ArrayList<>();
            ArrayList<Period> periods = ReportUtil.getPeriodsForPeriodType(period, periodType);
            for (Period p : periods) {
                GeneralReportItem item = new GeneralReportItem();
                item.date = p.startDate;
                item.proceeds = repository.getProceedForPeriod(p);
                item.profit = repository.getProfitForPeriod(p);
                item.averageCheck = repository.getAverageForPeriod(p);
                item.orders = repository.getOrdersForPeriod(p);
                item.expense = item.proceeds - item.profit;
                reportItems.add(item);
            }
            if (reportItems.size() > 0) e.onSuccess(reportItems);
            else e.onError(new NullPointerException());
        });
    }

    @Override
    public Single<ArrayList<ProductReportItem>> loadProductReport(Period period) {

        return Single.create(e -> {
            ArrayList<TopProduct> topProducts = repository.getTopOfProducts(period);
            ArrayList<ProductReportItem> items = new ArrayList<>();
            for (TopProduct topProduct : topProducts) {
                ProductReportItem item = new ProductReportItem();
                item.name = topProduct.product.name;
                item.count = topProduct.quantity;
                item.proceed = topProduct.product.cost * topProduct.quantity;
                for (Ingredient ingredient : topProduct.product.ingredients) {
                    item.expense += ingredient.cost * topProduct.product.getIngredientCount(ingredient.id)
                            * topProduct.quantity;
                }
                item.profit = item.proceed - item.expense;
                items.add(item);
            }
            if (items.size() > 0) e.onSuccess(items);
            else e.onError(new NullPointerException());
        });
    }

    @Override
    public Single<ArrayList<StorageReportItem>> loadStorageReport(Period period) {
        return Single.create(e -> {
            ArrayList<StorageReportItem> items = repository.getStorageIngredients(period);
            if(items.size()>0) e.onSuccess(items);
            else  e.onError(new NullPointerException());
        });
    }
}
