package com.gojek.trending.assignment.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.gojek.trending.assignment.R;
import com.gojek.trending.assignment.application.TrendingApplication;

import org.json.JSONArray;

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class TrendingAPI {

    @Inject
    RequestQueue requestQueue;

    private Context mContext;

    public TrendingAPI(Context context) {
        ((TrendingApplication)context).getAppComponent().inject(this);

        this.mContext = context;
    }

    public JSONArray newJSONRepositories() throws ExecutionException, InterruptedException {
        RequestFuture<JSONArray> jsonArrayRequestFuture = RequestFuture.newFuture();
        String url = mContext.getString(R.string.SERVER_URL) + WebRequestAPIs.GET_REPOSITORIES.getURL();
        requestQueue.add(new TrendingJSONArrayRequest(url, jsonArrayRequestFuture, jsonArrayRequestFuture));
        return jsonArrayRequestFuture.get();
    }

    public Flowable<JSONArray> getRepositories() {
        return Flowable.defer(
                () -> {
                    try {
                        return Flowable.just(newJSONRepositories());
                    } catch (InterruptedException | ExecutionException e) {
                        Log.e("repositories", e.getMessage());
                        return Flowable.error(e);
                    }
                });
    }



}
