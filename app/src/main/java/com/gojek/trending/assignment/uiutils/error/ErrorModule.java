package com.gojek.trending.assignment.uiutils.error;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ErrorModule {

    @Provides
    @Singleton
    public ErrorEventBus provideErrorModule(Context context) {
        return ErrorEventBus.getInstance();
    }
}
