package com.paper.smartcafe.main.di;

import com.paper.smartcafe.dagger.scopes.MainScope;
import com.paper.smartcafe.data.repositories.CompleteOrderRepository;
import com.paper.smartcafe.data.repositories.IngredientRepository;
import com.paper.smartcafe.data.repositories.OrderRepository;
import com.paper.smartcafe.data.repositories.ProductRepository;
import com.paper.smartcafe.main.domain.IMainInteractor;
import com.paper.smartcafe.main.domain.MainInteractor;
import com.paper.smartcafe.menu.domain.IMenuInteractor;
import com.paper.smartcafe.menu.domain.MenuInteractor;

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
