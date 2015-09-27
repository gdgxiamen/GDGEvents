package com.xmgdg.gdgevents.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.avos.avoscloud.AVOSCloud;
import com.xmgdg.gdgevents.Tools.AppStat;

/**
 * Created by ye on 15/5/18.
 */
public class App extends Application {

    private static SharedPreferences preferences;
    private static Application application;

    public static String getPrefer(String Name) {
        return preferences.getString(Name, "");
    }

    public static void setPrefer(String name, String vaule) {
        preferences.edit().putString(name, vaule).apply();
    }

    public static Application getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        AVOSCloud.initialize(this, Secret.GDG_LeanCloud_AppId, Secret.GDG_LeanCloud_AppKey);
        preferences = getSharedPreferences(AppStat.Preferences.FileName, App.MODE_PRIVATE);
    }
}
