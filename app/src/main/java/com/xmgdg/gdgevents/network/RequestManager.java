package com.xmgdg.gdgevents.network;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.xmgdg.gdgevents.app.App;
import com.xmgdg.gdgevents.model.Topic;

import java.util.List;

/**
 * Created by ye on 15/5/17.
 * Volley工具类
 */
public class RequestManager {

    private static final String API_HOST = "https://developers.google.com/events/feed/json?group=";
    private static final String STARTTIME = "1356998399";
    private static RequestManager requestInstance;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    private RequestManager() {
        mRequestQueue = Volley.newRequestQueue(App.getApplication());

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

    /**
     * 获取地区对应接口地址
     **/
    public static String getUrl(String ID_CITY) {
        return API_HOST + ID_CITY + "&start=" + STARTTIME;
    }

    public static RequestManager getInstance() {
        if (requestInstance == null) {
            synchronized (RequestManager.class) {
                if (requestInstance == null) {
                    requestInstance = new RequestManager();
                }
            }
        }
        return requestInstance;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void getTopicInfo(String url, Response.Listener<List<Topic>> listener, Response.ErrorListener errorListener) {
        mRequestQueue.add(new TopicRequest(url, listener, errorListener));
    }

    /** **/
    public void cancel(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
}
