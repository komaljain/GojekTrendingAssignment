package com.gojek.trending.assignment.parser;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ParserModule {

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }
}