package com.gojek.trending.assignment.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gojek.trending.assignment.BR;
import com.gojek.trending.assignment.R;
import com.gojek.trending.assignment.adapters.TrendingRecyclerViewAdapter;
import com.gojek.trending.assignment.application.TrendingApplication;
import com.gojek.trending.assignment.databinding.ActivityTrendingBinding;
import com.gojek.trending.assignment.model.Repository;
import com.gojek.trending.assignment.uiutils.error.ErrorEventBus;
import com.gojek.trending.assignment.uiutils.error.ErrorFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TrendingActivity extends AppCompatActivity implements TrendingRecyclerViewAdapter.OnItemClickListener, ITrendingPresenter.View {

    @Inject
    TrendingPresenterImpl trendingPresenter;

    @Inject
    ErrorEventBus errorEventBus;

    private ActivityTrendingBinding binding;
    private TrendingRecyclerViewAdapter trendingRecyclerViewAdapter;

    private static final String KEY_TRENDING_LIST = "KEY_TRENDING_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((TrendingApplication) getApplication()).getAppComponent().inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_trending);
        binding.setVariable(BR.presenter, trendingPresenter);

        initToolbar();

        List<Repository> repositories;
        if (savedInstanceState == null) {
            repositories = new ArrayList<>();
        } else {
            repositories = savedInstanceState.getParcelableArrayList(KEY_TRENDING_LIST);
        }

        setupRecyclerView(repositories);
        trendingPresenter.setView(this);

        initRefreshLayout();
        initErrorSubscriber();
    }

    private void initRefreshLayout() {
        binding.refreshTrendingRepositories.setEnabled(false);
    }

    private void initErrorSubscriber() {
        errorEventBus.getErrorEventObservable().subscribe(integer -> {
                if(integer == ErrorEventBus.ACTION_RETRY_CLICKED) {
                    trendingPresenter.retryClicked();
                }
        });
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar) binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
   }

    @Override
    protected void onStart() {
        hideError();
        showLoading();
        trendingPresenter.getTrendingRepositories();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_sort_by_name:
                trendingPresenter.sortRepositoriesByName();
                break;
            case R.id.action_sort_by_stars:
                trendingPresenter.sortRepositoriesByStars();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        List<Repository> repositories = trendingRecyclerViewAdapter.getRepositories();
        ArrayList<Parcelable> parcelableArrayList = new ArrayList<>(repositories);
        outState.putParcelableArrayList(KEY_TRENDING_LIST, parcelableArrayList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        List<Repository> repositories = savedInstanceState.getParcelableArrayList(KEY_TRENDING_LIST);
        showTrending(repositories);
    }

    private void setupRecyclerView(List<Repository> repositories) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        trendingRecyclerViewAdapter = new TrendingRecyclerViewAdapter(getApplicationContext(), repositories, this);
        binding.recyclerView.setAdapter(trendingRecyclerViewAdapter);
    }

    @Override
    public void onItemClick(Repository item) {
        TransitionManager.beginDelayedTransition(binding.recyclerView);
        trendingRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        binding.shimmerViewContainer.setVisibility(View.VISIBLE);
        binding.shimmerViewContainer.startShimmer();
    }

    @Override
    public void hideLoading() {
        binding.shimmerViewContainer.stopShimmer();
        binding.shimmerViewContainer.setVisibility(View.GONE);

    }

    @Override
    public void showError(Throwable throwable) {
        ErrorFragment errorFragment = new ErrorFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, errorFragment).commit();
    }

    @Override
    public void hideError() {
        if(getSupportFragmentManager().findFragmentById(R.id.fragment_container) != null) {
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.fragment_container)).commit();
        }
    }

    @Override
    public void hideSwipeRefresh() {
        binding.refreshTrendingRepositories.setRefreshing(false);
    }

    @Override
    public void showTrending(List<Repository> items) {
        trendingRecyclerViewAdapter = new TrendingRecyclerViewAdapter(getApplicationContext(), items, this);
        binding.recyclerView.setAdapter(trendingRecyclerViewAdapter);
        binding.recyclerView.getAdapter().notifyDataSetChanged();
        binding.refreshTrendingRepositories.setEnabled(true);
        addSwipeToRefreshList();
    }

    @Override
    public void addSwipeToRefreshList() {
        binding.refreshTrendingRepositories.setOnRefreshListener(() ->
                trendingPresenter.getTrendingRepositories()
        );
    }



    @Override
    public void refreshTrendingList() {
        binding.recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        if (trendingPresenter.getDisposable() != null && !trendingPresenter.getDisposable().isDisposed())
            trendingPresenter.getDisposable().clear();
        super.onStop();
    }
}


