package com.gojek.trending.assignment.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.gojek.trending.assignment.R;
import com.gojek.trending.assignment.application.TrendingApplication;
import com.gojek.trending.assignment.model.Repository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

public class TrendingAPI {

    @Inject
    Gson gson;

    @Inject
    RequestQueue requestQueue;

    private Context mContext;

    public TrendingAPI(Context context) {
        ((TrendingApplication)context).getAppComponent().inject(this);

        this.mContext = context;
    }

    public Single<List<Repository>> getRepositories() {
        return Single.create(new SingleOnSubscribe<List<Repository>>() {
            @Override
            public void subscribe(@NonNull final SingleEmitter<List<Repository>> emitter) throws Exception {
                String url = mContext.getString(R.string.SERVER_URL) + WebRequestAPIs.GET_REPOSITORIES.getURL();
                JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                if (response != null) {

                                    Type userListType = new TypeToken<ArrayList<Repository>>(){}.getType();
                                    List<Repository> repositoryList = gson.fromJson(response.toString(), userListType);

                                    emitter.onSuccess(repositoryList);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                emitter.onError(error);
                            }
                        }
                );

                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}
