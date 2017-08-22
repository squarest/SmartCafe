package com.example.clevercafe.analytics.di;

import com.example.clevercafe.analytics.presentation.AnalyticsPresenter;
import com.example.clevercafe.dagger.scopes.AnalyticsScope;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 22.08.17.
 */
@AnalyticsScope
@Subcomponent(modules = {AnalyticsModule.class})
public interface AnalyticsComponent {
    void inject(AnalyticsPresenter presenter);
}
