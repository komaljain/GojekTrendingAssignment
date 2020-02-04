package com.gojek.trending.assignment.ui;

import com.gojek.trending.assignment.model.Repository;

import java.util.List;

public interface ITrendingPresenter {

    void setView(View view);
    void getTrendingRepositories();
    void sortRepositoriesByName();
    void sortRepositoriesByStars();

    interface View {
        void showLoading();
        void hideLoading();
        void showError(Throwable throwable);
        void hideError();
        void hideSwipeRefresh();

        void showTrending(List<Repository> items);
        void addSwipeToRefreshList();
        void refreshTrendingList();
    }

}
