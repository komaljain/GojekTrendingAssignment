package com.gojek.trending.assignment.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.gojek.trending.assignment.R;
import com.gojek.trending.assignment.application.TrendingApplication;

import javax.inject.Inject;

public class TrendingActivity extends AppCompatActivity {

    @Inject
    TrendingPresenterImpl trendingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TrendingApplication) getApplication()).getAppComponent().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        trendingPresenter.getTrendingRepositories();
    }
}
