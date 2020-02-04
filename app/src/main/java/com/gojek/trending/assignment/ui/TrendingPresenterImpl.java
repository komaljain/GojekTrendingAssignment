package com.gojek.trending.assignment.ui;

import android.content.Context;
import android.os.Handler;

import com.gojek.trending.assignment.application.TrendingApplication;
import com.gojek.trending.assignment.model.Repository;
import com.gojek.trending.assignment.network.TrendingAPI;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class TrendingPresenterImpl implements ITrendingPresenter {

    @Inject
    TrendingAPI trendingAPI;

    @Inject
    Gson gson;

    @Inject
    CompositeDisposable disposable;

    private Context mContext;

    private View view;
    private boolean error = false;

    private List<Repository> repositoryList;

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
        Disposable subscription = trendingAPI.getRepositories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(getRepositoryObserver());

        disposable.add(subscription);
    }


    private DisposableSingleObserver<List<Repository>> getRepositoryObserver(){
        return new DisposableSingleObserver<List<Repository>>() {
            @Override
            public void onSuccess(@NonNull List<Repository> repositoryList) {
                setRepositoryList(repositoryList);
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
            }

            @Override
            public void onError(@NonNull Throwable e) {
                view.hideLoading();
                view.showError(e.getCause());
                view.hideSwipeRefresh();
            }
        };
    }
    @Override
    public void sortRepositoriesByName() {
        Collections.sort(getRepositoryList(), new Comparator<Repository>() {
            @Override
            public int compare(Repository o1, Repository o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        view.refreshTrendingList();
    }

    @Override
    public void sortRepositoriesByStars() {
        Collections.sort(getRepositoryList(), new Comparator<Repository>() {
            @Override
            public int compare(Repository o1, Repository o2) {
                return Integer.compare(o2.getStars(), o1.getStars());
            }
        });
        view.refreshTrendingList();
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Repository> getRepositoryList() {
        return repositoryList;
    }

    public void setRepositoryList(List<Repository> repositoryList) {
        this.repositoryList = repositoryList;
    }
}
