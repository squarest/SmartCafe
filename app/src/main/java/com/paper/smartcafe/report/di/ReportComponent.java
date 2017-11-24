package com.paper.smartcafe.report.di;

import com.paper.smartcafe.dagger.scopes.ReportScope;
import com.paper.smartcafe.report.presentation.ReportPresenter;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 06.10.17.
 */
@ReportScope
@Subcomponent(modules = {ReportModule.class})
public interface ReportComponent {
    void inject(ReportPresenter presenter);
}
