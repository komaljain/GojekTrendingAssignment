package com.gojek.trending.assignment.ui;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gojek.trending.assignment.application.TrendingApplication;
import com.gojek.trending.assignment.model.Repository;
import com.gojek.trending.assignment.network.TrendingAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class TrendingPresenterImpl implements ITrendingPresenter {

    @Inject
    TrendingAPI trendingAPI;

    @Inject
    Gson gson;

    private Context mContext;
    private List<Repository> repositoryList;

    @Inject
    public TrendingPresenterImpl(Context context) {
        this.mContext = context;

        ((TrendingApplication) mContext).getAppComponent().inject(this);
    }

    @Override
    public void getTrendingRepositories() {
        trendingAPI.getRepositories(getResponseListener(), getErrorListener());
    }

    @NonNull
    private Response.Listener<JSONArray> getResponseListener() {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Type userListType = new TypeToken<ArrayList<Repository>>(){}.getType();
                repositoryList = gson.fromJson(response.toString(), userListType);
                //TODO: present the list in recyclerview
            }
        };
    }

    @NonNull
    private Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: handle the error here..
            }
        };
    }
}
