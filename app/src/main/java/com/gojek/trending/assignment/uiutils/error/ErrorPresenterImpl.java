package com.gojek.trending.assignment.uiutils.error;


import android.content.Context;

import com.gojek.trending.assignment.application.TrendingApplication;

import javax.inject.Inject;

public class ErrorPresenterImpl implements IErrorPresenter {

    @Inject
    ErrorEventBus errorEventBus;

    private Context mContext;

    @Inject
    public ErrorPresenterImpl(Context context) {
        this.mContext = context;

        ((TrendingApplication) mContext).getAppComponent().inject(this);
    }

    @Override
    public void retryClicked() {
        errorEventBus.postFragmentAction(ErrorEventBus.ACTION_RETRY_CLICKED);
    }
}
