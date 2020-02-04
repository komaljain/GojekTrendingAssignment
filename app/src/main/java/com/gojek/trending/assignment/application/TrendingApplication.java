package com.gojek.trending.assignment.application;

import android.app.Application;

import com.gojek.trending.assignment.di.DaggerTrendingComponent;
import com.gojek.trending.assignment.di.TrendingComponent;
import com.gojek.trending.assignment.di.TrendingModule;

public class TrendingApplication extends Application {

    protected TrendingComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger(this);
    }

    protected TrendingComponent initDagger(TrendingApplication application) {
        return DaggerTrendingComponent.builder()
                .trendingModule(new TrendingModule(application))
                .build();
    }

    public TrendingComponent getAppComponent() {
        return appComponent;
    }


}
