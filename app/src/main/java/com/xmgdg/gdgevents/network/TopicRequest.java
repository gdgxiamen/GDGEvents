package com.xmgdg.gdgevents.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.xmgdg.gdgevents.model.Topic;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by ye on 15/5/18.
 * 自定义volley请求（Gson解析数据返回数据List<Topic>）
 */
public class TopicRequest extends Request<List<Topic>> {

    private final Gson gson = new Gson();
    private Response.Listener<List<Topic>> mListener;

    public TopicRequest(String url, Response.Listener<List<Topic>> listener, Response.ErrorListener errorlistener) {
        super(Method.GET, url, errorlistener);
        mListener = listener;
    }

    @Override
    protected Response<List<Topic>> parseNetworkResponse(NetworkResponse response) {
        try {
            //GSON解析
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Topic[] topics = gson.fromJson(json, Topic[].class);
            List<Topic> topicList = Arrays.asList(topics);
            //倒序排列
            Collections.reverse(topicList);
            return Response.success(topicList, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(List<Topic> response) {
        if(mListener != null) {
            mListener.onResponse(response);
        }
    }
}
