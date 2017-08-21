package com.example.clevercafe.dagger.modules;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.clevercafe.data.AppDatabase;
import com.example.clevercafe.data.CompleteOrderRepository;
import com.example.clevercafe.data.IngredientRepository;
import com.example.clevercafe.data.InvoiceRepository;
import com.example.clevercafe.data.OrderRepository;
import com.example.clevercafe.data.ProductRepository;
import com.example.clevercafe.data.dao.DatabaseDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chudofom on 28.07.17.
 */
@Module
public class DataModule {
    @Provides
    @Singleton
    public AppDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, "smartcafe").build();
    }

    @Provides
    @Singleton
    public DatabaseDao provideDao(AppDatabase database) {
        return database.databaseDao();
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPref(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    public IngredientRepository provideIngredientRepository(DatabaseDao databaseDao) {
        return new IngredientRepository(databaseDao);
    }

    @Provides
    @Singleton
    public ProductRepository provideProductRepository(DatabaseDao databaseDao) {
        return new ProductRepository(databaseDao);
    }

    @Provides
    @Singleton
    public OrderRepository provideOrderRepository(DatabaseDao databaseDao, SharedPreferences sharedPreferences) {
        return new OrderRepository(databaseDao, sharedPreferences);
    }

    @Provides
    @Singleton
    public CompleteOrderRepository provideCompleteRepository(DatabaseDao databaseDao) {
        return new CompleteOrderRepository(databaseDao);
    }

    @Provides
    @Singleton
    public InvoiceRepository provideInvoiceRepository(DatabaseDao databaseDao) {
        return new InvoiceRepository(databaseDao);
    }

}
