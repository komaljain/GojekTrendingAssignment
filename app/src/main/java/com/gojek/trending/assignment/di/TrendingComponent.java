package com.gojek.trending.assignment.di;

import com.gojek.trending.assignment.network.NetworkModule;
import com.gojek.trending.assignment.network.TrendingAPI;
import com.gojek.trending.assignment.network.TrendingCompositeDisposableModule;
import com.gojek.trending.assignment.network.TrendingVolleyRequestQueue;
import com.gojek.trending.assignment.parser.ParserModule;
import com.gojek.trending.assignment.ui.TrendingActivity;
import com.gojek.trending.assignment.ui.TrendingPresenterImpl;
import com.gojek.trending.assignment.uiutils.error.ErrorFragment;
import com.gojek.trending.assignment.uiutils.error.ErrorModule;
import com.gojek.trending.assignment.uiutils.error.ErrorPresenterImpl;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TrendingModule.class, NetworkModule.class, ParserModule.class,
        ErrorModule.class, TrendingCompositeDisposableModule.class})
public interface TrendingComponent {
    void inject(TrendingActivity target);
    void inject(TrendingAPI target);
    void inject(TrendingPresenterImpl target);
    void inject(TrendingVolleyRequestQueue target);
    void inject(ErrorFragment target);
    void inject(ErrorPresenterImpl target);
}