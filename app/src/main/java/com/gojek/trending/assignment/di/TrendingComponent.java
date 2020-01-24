package com.gojek.trending.assignment.di;

import com.gojek.trending.assignment.network.NetworkModule;
import com.gojek.trending.assignment.network.TrendingAPI;
import com.gojek.trending.assignment.parser.ParserModule;
import com.gojek.trending.assignment.ui.TrendingActivity;
import com.gojek.trending.assignment.ui.TrendingPresenterImpl;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TrendingModule.class, NetworkModule.class, ParserModule.class})
public interface TrendingComponent {
    void inject(TrendingActivity target);
    void inject(TrendingAPI target);
    void inject(TrendingPresenterImpl target);
}