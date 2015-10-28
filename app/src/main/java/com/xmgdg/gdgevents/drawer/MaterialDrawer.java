package com.xmgdg.gdgevents.drawer;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.xmgdg.gdgevents.R;
import com.xmgdg.gdgevents.network.RequestManager;

/**
 * Created by Yulan on 2015/5/16.
 * 材料抽屉类
 * 更改  String TITLES[] 和 int ICONS[] 可以增减抽屉项目
 * 构造时传入 Activity 和 Toolbar 即可.
 */
public class MaterialDrawer {


    //界面
    NetworkImageView Profile;
    TextView Name;
    TextView Email;
    Activity activityinviewhold;
    Context contextinviewhold;
    DrawerLayout drawerLayout;
    //生命周期
    private Application mapplication;
    private Context mcontext;
    private Activity mactivity;
    private String logtag = "材料抽屉";
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mtoolbar;
    private String username = "";
    private String usermail = "";
    private Uri userFacePhoto;


    public MaterialDrawer(Activity mact, Toolbar mtoolbar) {
        mactivity = mact;
        mapplication = mact.getApplication();
        mcontext = mact.getApplicationContext();
        this.mtoolbar = mtoolbar;
        init();
    }

    private void init() {

        Name = (TextView) mactivity.findViewById(R.id.name);
        Email = (TextView) mactivity.findViewById(R.id.email);
        Profile = (NetworkImageView) mactivity.findViewById(R.id.userPhotoView);

        mDrawer = (DrawerLayout) mactivity.findViewById(R.id.Material_tool_bar_Drawer);
        mDrawerToggle = new ActionBarDrawerToggle(mactivity, mDrawer, mtoolbar,
                R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }


        };
        mDrawer.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }

    public void setUserInfo(String name, String email, String photo) {
        Name.setText(name);
        Email.setText(email);
        if (photo != null) {
            Profile.setImageUrl(photo, RequestManager.getInstance().getImageLoader());
        }
    }
}
