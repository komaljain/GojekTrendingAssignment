package com.gojek.trending.assignment.ui;

import android.content.Context;
import android.os.Handler;

import com.gojek.trending.assignment.application.TrendingApplication;
import com.gojek.trending.assignment.model.Repository;
import com.gojek.trending.assignment.network.TrendingAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class TrendingPresenterImpl implements ITrendingPresenter {

    @Inject
    TrendingAPI trendingAPI;

    @Inject
    Gson gson;

    private Context mContext;
    private List<Repository> repositoryList;

    private View view;
    private Disposable disposable;
    private boolean error = false;

    @Inject
    public TrendingPresenterImpl(Context context) {
        this.mContext = context;

        ((TrendingApplication) mContext).getAppComponent().inject(this);
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    public void retryClicked() {
        view.hideError();
        view.showLoading();
        getTrendingRepositories();
    }

    @Override
    public void getTrendingRepositories() {
        disposable = trendingAPI.getRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Type userListType = new TypeToken<ArrayList<Repository>>(){}.getType();
                    repositoryList = gson.fromJson(response.toString(), userListType);
                    if (repositoryList != null && repositoryList.size() > 0) {
                        //The delay is added here so that the shimmer animation can be seen for few seconds,
                        // as the api loads quickly
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                view.hideSwipeRefresh();
                                view.hideLoading();
                                view.showTrending(repositoryList);
                            }
                        }, 200);
                    } else {
                        //TODO: handle no data found scenario
                    }
                }, throwable -> {
                    view.hideLoading();
                    view.showError(throwable.getCause());
                    view.hideSwipeRefresh();
                }, () -> {
                    view.hideSwipeRefresh();
                    view.hideLoading();
                });
    }

    @Override
    public void sortRepositoriesByName() {
        Collections.sort(repositoryList, new Comparator<Repository>() {
            @Override
            public int compare(Repository o1, Repository o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        view.refreshTrendingList();
    }

    @Override
    public void sortRepositoriesByStars() {
        Collections.sort(repositoryList, new Comparator<Repository>() {
            @Override
            public int compare(Repository o1, Repository o2) {
                return Integer.compare(o2.getStars(), o1.getStars());
            }
        });
        view.refreshTrendingList();
    }

    public Disposable getDisposable() {
        return disposable;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
