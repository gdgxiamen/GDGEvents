package com.xmgdg.gdgevents.app;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.xmgdg.gdgevents.Tools.Alarm;
import com.xmgdg.gdgevents.Tools.AppStat;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by ye on 15/5/18.
 */
public class App extends Application {

    private static SharedPreferences preferences;
    private static Application application;
    private static HashMap<String, WeakReference<Activity>> mActivitys = new HashMap<>();
    private static AVUser avUser;

    public static AVUser getAvUser() {
        return avUser;
    }

    public static void setAvUser(AVUser avUser) {
        App.avUser = avUser;
    }

    public static String getPrefer(String Name) {
        return preferences.getString(Name, "");
    }

    public static void setPrefer(String name, String vaule) {
        preferences.edit().putString(name, vaule).apply();
    }

    public static Application getApplication() {
        return application;
    }

    /**
     * 设置Activity引用
     *
     * @param activity 新增的activity
     */
    public static synchronized void setActivitys(Activity activity) {
        WeakReference<Activity> reference = new WeakReference<Activity>(activity);
        mActivitys.put(activity.getClass().getSimpleName(), reference);
    }

    public static Activity getActivity(String ActivityName) {
        return mActivitys.get(ActivityName).get();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        AVOSCloud.initialize(this, Secret.GDG_LeanCloud_AppId, Secret.GDG_LeanCloud_AppKey);
        preferences = getSharedPreferences(AppStat.Preferences.FileName, App.MODE_PRIVATE);
        Alarm.SetAlarm(getApplicationContext());
    }
}
