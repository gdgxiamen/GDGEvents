package com.xmgdg.gdgevents.network;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
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

    /** 获取地区对应接口地址 **/
    public static String getUrl(String ID_CITY) {
        return API_HOST + ID_CITY + "&start=" + STARTTIME;
    }

    private static RequestManager requestInstance;

    public static RequestManager getInstance() {
        if(requestInstance == null) {
            synchronized (RequestManager.class) {
                if(requestInstance == null) {
                    requestInstance = new RequestManager();
                }
            }
        }
        return requestInstance;
    }

    private RequestQueue mRequestQueue;

    private RequestManager() {
        mRequestQueue = Volley.newRequestQueue(App.getInstance());
    }

    public void getTopicInfo(String url, Response.Listener<List<Topic>> listener, Response.ErrorListener errorListener) {
        mRequestQueue.add(new TopicRequest(url, listener, errorListener));
    }

    /** **/
    public void cancel(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
}
