package com.example.clevercafe.menu.di;

import com.example.clevercafe.dagger.scopes.MenuScope;
import com.example.clevercafe.db.ProductRepository;
import com.example.clevercafe.menu.domain.IMenuInteractor;
import com.example.clevercafe.menu.domain.MenuInteractor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chudofom on 28.07.17.
 */
@Module
public class MenuModule {
    @Provides
    @MenuScope
    public IMenuInteractor provideMenuInteractor(ProductRepository productRepository) {
        return new MenuInteractor(productRepository);
    }
}
