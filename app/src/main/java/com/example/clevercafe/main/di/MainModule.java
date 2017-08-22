package com.example.clevercafe.main.di;

import com.example.clevercafe.dagger.scopes.MainScope;
import com.example.clevercafe.data.repositories.CompleteOrderRepository;
import com.example.clevercafe.data.repositories.IngredientRepository;
import com.example.clevercafe.data.repositories.OrderRepository;
import com.example.clevercafe.data.repositories.ProductRepository;
import com.example.clevercafe.main.domain.IMainInteractor;
import com.example.clevercafe.main.domain.MainInteractor;
import com.example.clevercafe.menu.domain.IMenuInteractor;
import com.example.clevercafe.menu.domain.MenuInteractor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chudofom on 28.07.17.
 */
@Module
public class MainModule {
    @Provides
    @MainScope
    public IMainInteractor provideMainInteractor(ProductRepository productRepository,
                                                 OrderRepository orderRepository, CompleteOrderRepository completeOrderRepository,
                                                 IngredientRepository ingredientRepository) {
        return new MainInteractor(productRepository, orderRepository, completeOrderRepository, ingredientRepository);
    }
    @Provides
    @MainScope
    public IMenuInteractor provideMenuInteractor(ProductRepository productRepository) {
        return new MenuInteractor(productRepository);
    }
}
