package com.gojek.trending.assignment.application;

import android.app.Application;

import com.gojek.trending.assignment.di.DaggerTrendingComponent;
import com.gojek.trending.assignment.di.TrendingComponent;
import com.gojek.trending.assignment.di.TrendingModule;

public class TrendingApplication extends Application {

    protected TrendingComponent appComponent;

    public TrendingComponent getAppComponent() { return appComponent; }

    protected TrendingComponent initDagger(TrendingApplication application) {
        return DaggerTrendingComponent.builder()
                .trendingModule(new TrendingModule(application))
                .build();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger(this);
    }
}
