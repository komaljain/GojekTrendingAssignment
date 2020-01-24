package com.gojek.trending.assignment.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.gojek.trending.assignment.R;
import com.gojek.trending.assignment.application.TrendingApplication;

import javax.inject.Inject;

public class TrendingAPI {

    @Inject
    RequestQueue requestQueue;

    private Context mContext;

    public TrendingAPI(Context context) {
        ((TrendingApplication)context).getAppComponent().inject(this);

        this.mContext = context;
    }

    public void getRepositories(Response.Listener responseListener, Response.ErrorListener errorListener) {
        String url = mContext.getString(R.string.SERVER_URL) + WebRequestAPIs.GET_REPOSITORIES.getURL();
        requestQueue.add(new JsonArrayRequest(url, responseListener, errorListener));
    }
}
