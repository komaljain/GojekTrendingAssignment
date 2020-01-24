package com.gojek.trending.assignment.parser;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.gojek.trending.assignment.network.TrendingAPI;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ParserModule {

    @Provides
    @Singleton
    public Gson provideGson(Context context) {
        return new Gson();
    }
}