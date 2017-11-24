package com.paper.smartcafe.analytics.di;

import com.paper.smartcafe.analytics.presentation.AnalyticsPresenter;
import com.paper.smartcafe.dagger.scopes.AnalyticsScope;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 22.08.17.
 */
@AnalyticsScope
@Subcomponent(modules = {AnalyticsModule.class})
public interface AnalyticsComponent {
    void inject(AnalyticsPresenter presenter);
}
