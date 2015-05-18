package com.xmgdg.gdgevents.app;

import android.app.Application;

/**
 * Created by ye on 15/5/18.
 */
public class App extends Application {

    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static Application getInstance() {
        return mInstance;
    }

}
