package com.xmgdg.gdgevents.network;

import com.android.volley.RequestQueue;

/**
 * Created by ye on 15/5/17.
 */
public class RequestManager {

    private static final String API_HOST = "https://developers.google.com/events/feed/json?group=";
    private static final String ID_XIAMEN = "110967416369300099369";
    private static final String STARTTIME_XIAMEN = "1356998399";

    public static final String API_XIAMEN = API_HOST + ID_XIAMEN + "&start=" + STARTTIME_XIAMEN;



}
