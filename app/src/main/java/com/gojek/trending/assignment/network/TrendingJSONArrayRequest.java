package com.gojek.trending.assignment.network;

import androidx.annotation.Nullable;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

public class TrendingJSONArrayRequest extends JsonArrayRequest {
    public TrendingJSONArrayRequest(String url, Response.Listener<JSONArray> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public TrendingJSONArrayRequest(int method, String url, @Nullable JSONArray jsonRequest, Response.Listener<JSONArray> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        Response<JSONArray> resp = super.parseNetworkResponse(response);
        if(!resp.isSuccess()) {
            return resp;
        }
        long now = System.currentTimeMillis();
        Cache.Entry entry = resp.cacheEntry;
        if(entry == null) {
            entry = new Cache.Entry();
            entry.data = response.data;
            entry.responseHeaders = response.headers;
        }
        entry.ttl = now + 720 * 1000;  //keeps cache for 2 hrs
        //entry.ttl = now + 1 * 1000;  //keeps cache for 1 second

        return Response.success(resp.result, entry);
    }
}
