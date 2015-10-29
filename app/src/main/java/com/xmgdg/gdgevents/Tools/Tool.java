package com.xmgdg.gdgevents.Tools;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.xmgdg.gdgevents.app.App;
import com.xmgdg.gdgevents.otto.BusProvider;
import com.xmgdg.gdgevents.otto.UserInfo;

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

    /**
     * 注册并登陆账号
     *
     * @param Email    用户邮箱，用作账号
     * @param password 密码
     * @param face     用户头像
     * @param showName 用户显示的名称
     * @param activity Activity
     */
    public static void SignUpInLeanCloud(final String Email, final String password, String face, String showName, final Activity activity) {

        final String tag = "LeanCloud 注册";

        AVUser user = new AVUser();
        user.setUsername(Email);
        user.setPassword(password);
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
                SignInLeanCloud(Email, password, activity);
            }
        });
    }

    /**
     * 登陆账号
     *
     * @param userName 用户名，应为邮箱
     * @param password 密码
     * @param activity Activity
     */
    public static void SignInLeanCloud(String userName, String password, final Activity activity) {
        final String tag = "LeanCloud 登陆";

        AVUser.logInInBackground(userName, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (avUser != null) {
                    Log.i(tag, "登陆成功");
                    String personName = avUser.getString("showName"),
                            email = avUser.getEmail(),
                            personPhotoUrl = avUser.getString("face");
                    BusProvider.getInstance().post(new UserInfo(personName, email, personPhotoUrl));
                    App.setAvUser(avUser);
                } else {
                    Log.w(tag, "登陆失败：" + e.getMessage());
                    Toast.makeText(activity, "登陆失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
