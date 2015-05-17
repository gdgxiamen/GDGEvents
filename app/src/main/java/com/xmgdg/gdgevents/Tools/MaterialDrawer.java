package com.xmgdg.gdgevents.Tools;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.widget.RecyclerView;

import com.xmgdg.gdgevents.R;

/**
 * Created by Yulan on 2015/5/16.
 * 材料抽屉类
 * 更改  String TITLES[] 和 int ICONS[] 可以增减抽屉项目
 * 构造时传入 Activity 和 Toolbar 即可.
 */
public class MaterialDrawer {


	//生命周期
	private Application mapplication;
	private Context mcontext;
	private Activity mactivity;
	private String logtag = "材料抽屉";

	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private DrawerLayout mDrawer;
	private ActionBarDrawerToggle mDrawerToggle;
	private Toolbar mtoolbar;

	private String TITLES[] = {"title"};
	private int ICONS[] = {R.mipmap.ic_launcher};
	private String username= "";
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

		mRecyclerView = (RecyclerView) mactivity.findViewById(R.id.DrawerRecyclerView);

		mRecyclerView.setHasFixedSize(true);

		mDrawer = (DrawerLayout) mactivity.findViewById(R.id.Material_tool_bar_Drawer);

		mAdapter = new MetarialDrawerAdapter(TITLES, ICONS, username, usermail,
				userFacePhoto, mactivity, mDrawer);

		mRecyclerView.setAdapter(mAdapter);

		mLayoutManager = new LinearLayoutManager(mactivity);

		mRecyclerView.setLayoutManager(mLayoutManager);


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


}
