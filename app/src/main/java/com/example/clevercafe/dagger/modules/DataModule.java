package com.example.clevercafe.dagger.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.clevercafe.db.AppDatabase;
import com.example.clevercafe.db.CompleteOrderRepository;
import com.example.clevercafe.db.IngredientRepository;
import com.example.clevercafe.db.OrderRepository;
import com.example.clevercafe.db.ProductRepository;
import com.example.clevercafe.db.dao.DatabaseDao;

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
    public OrderRepository provideOrderRepository(DatabaseDao databaseDao) {
        return new OrderRepository(databaseDao);
    }

    @Provides
    @Singleton
    public CompleteOrderRepository provideCompleteRepository(DatabaseDao databaseDao) {
        return new CompleteOrderRepository(databaseDao);
    }

}
