package com.example.clevercafe.report.di;

import com.example.clevercafe.dagger.scopes.ReportScope;
import com.example.clevercafe.report.presentation.ReportPresenter;

import dagger.Subcomponent;

/**
 * Created by Chudofom on 06.10.17.
 */
@ReportScope
@Subcomponent(modules = {ReportModule.class})
public interface ReportComponent {
    void inject(ReportPresenter presenter);
}
