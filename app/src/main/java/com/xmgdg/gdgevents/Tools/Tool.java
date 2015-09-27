package com.xmgdg.gdgevents.Tools;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Yulan on 2015/5/16.
 * 工具类
 */
public class Tool {

    public static void set下拉刷新颜色(SwipeRefreshLayout swipeLayout) {
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.holo_green_light);
    }

    public static Date getDateFromString(String date) {
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm Z",
                locale);
        try {
            Date ans = simpleDateFormat.parse(date);
            return ans;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void SignUpInLeanCloud(final String Email, final String passWord, String face, String showName, final Activity activity) {

        final String tag = "LeanCloud 账号体系";

        AVUser user = new AVUser();
        user.setUsername(Email);
        user.setPassword(passWord);
        user.setEmail(Email);

        // 其他属性可以像其他AVObject对象一样使用put方法添加
        user.put("face", face);
        user.put("showName", showName);

        user.signUpInBackground(new SignUpCallback() {
            public void done(AVException e) {
                if (e == null) {
                    Log.i(tag, "注册成功");
                } else {
                    Log.w(tag, "注册失败：" + e.getMessage());
                    Toast.makeText(activity, "注册失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                AVUser.logInInBackground(Email, passWord, new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (avUser != null) {
                            Log.i(tag, "登陆成功");
                        } else {
                            Log.w(tag, "登陆失败：" + e.getMessage());
                            Toast.makeText(activity, "登陆失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

}
