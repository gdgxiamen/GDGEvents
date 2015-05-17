package com.xmgdg.gdgevents;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.xmgdg.gdgevents.Tools.MainActivityEventsAdapter;
import com.xmgdg.gdgevents.Tools.MainEventsItems;
import com.xmgdg.gdgevents.Tools.MaterialDrawer;
import com.xmgdg.gdgevents.Tools.Tool;

/**
 * 主界面,显示举办的活动
 *
 * */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

	//toolbar
	private Toolbar toolbar;

	//RecyclerView
	private RecyclerView mRecyclerView;
	private RecyclerView.LayoutManager mLayoutManager;
	private MainActivityEventsAdapter mainActivityEventsAdapter;

	//下拉刷新
	private boolean isRefresh = false;
	private SwipeRefreshLayout swipeLayout;

	//events列表
	private MainEventsItems mainEventsItems;

	private String logtag = "mainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Toolbar
		toolbar = (Toolbar) findViewById(R.id.tool_bar);
		setSupportActionBar(toolbar);
		new MaterialDrawer(this, toolbar);

		//RecyclerView
		mRecyclerView = (RecyclerView) findViewById(R.id.mainActivityRecyclerView);
		mRecyclerView.setHasFixedSize(true);
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);

		//SwipeRefresh
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.mainActivitySwipeToRefreash);
		if (Build.VERSION.SDK_INT >= 14) {
			Tool.set下拉刷新颜色(swipeLayout);
		}
		swipeLayout.setOnRefreshListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();

		//测试数据
		String[] eventsTime = {"2013-3-13 13:00  ", "2015-05-15 15:00  "};
		String[] eventsTitle = {"GDG Xiamen Setup 厦门谷歌开发者社区成立大会", "厦门GDG【社区开源APP项目讨论】"};
		String[] eventsLocation = {"中国福建省厦门思明区环岛南路亚洲海湾大酒店", "厦门市软件园二期望海路31号1楼厦门GDG孵化器 "};

		mainEventsItems = new MainEventsItems(eventsTime, eventsTitle, eventsLocation);
		//RecyclerView 数据填充
		mainActivityEventsAdapter = new MainActivityEventsAdapter(mainEventsItems, this);
		mRecyclerView.setAdapter(mainActivityEventsAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	//SwipeRefreshLayout 的刷新调用
	@Override
	public void onRefresh() {
		if (!isRefresh) {
			isRefresh = true;
			//todo: 刷新
			swipeLayout.setRefreshing(false);
		}
	}
}
