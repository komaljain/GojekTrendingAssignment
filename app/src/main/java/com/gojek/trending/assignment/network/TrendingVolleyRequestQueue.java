package com.gojek.trending.assignment.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.gojek.trending.assignment.application.TrendingApplication;

import javax.inject.Inject;

public class TrendingVolleyRequestQueue {

    @Inject
    RequestQueue mRequestQueue;

    private static TrendingVolleyRequestQueue mInstance;
    private ImageLoader mImageLoader;


    private TrendingVolleyRequestQueue(Context context) {
        ((TrendingApplication) context).getAppComponent().inject(this);

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized TrendingVolleyRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TrendingVolleyRequestQueue(context);
        }
        return mInstance;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}