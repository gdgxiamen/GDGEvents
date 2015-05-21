package com.xmgdg.gdgevents;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xmgdg.gdgevents.Tools.MainActivityEventsAdapter;
import com.xmgdg.gdgevents.Tools.MaterialDrawer;
import com.xmgdg.gdgevents.Tools.OnMainEventsContextMenuSelect;
import com.xmgdg.gdgevents.Tools.Tool;
import com.xmgdg.gdgevents.model.Topic;
import com.xmgdg.gdgevents.network.RequestManager;

import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * 主界面,显示举办的活动
 * */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, MainActivityEventsAdapter.RecylcerViewOnItemClickListener {


	//TAG
	private static final String logtag = MainActivity.class.getName();

	//toolbar
	private Toolbar toolbar;

	//RecyclerView
	private RecyclerView mRecyclerView;
	private RecyclerView.LayoutManager mLayoutManager;
	private MainActivityEventsAdapter mainActivityEventsAdapter;

	//下拉刷新
	private boolean isRefresh = false;
	private SwipeRefreshLayout swipeLayout;

	//Context Menu 监听器
	OnMainEventsContextMenuSelect onMainEventsContextMenuSelect;


	private List<Topic> mTopicList = new ArrayList<Topic>();

	public static Activity activity;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		activity = this;
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

		//Context Menu 监听器
		onMainEventsContextMenuSelect = new OnMainEventsContextMenuSelect(this);

	}

	//上下文菜单被选定时的监听
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		Topic topic = mainActivityEventsAdapter.getTopicList().get(
				mainActivityEventsAdapter.getPosition());
		return onMainEventsContextMenuSelect.OnLongClickListener(item, topic);
	}

	@Override
	protected void onResume() {
		super.onResume();
		/* 初始化时显示刷新进度条
		*  第一行是解决初始化不出现刷新条的临时方法,后续可能会被 SwipeRefreshLayout 改进
		* */
		swipeLayout.setProgressViewOffset(false, 0,
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
		swipeLayout.setRefreshing(true);
		initData();
	}

	private void initData() {
		RequestManager.getInstance().getTopicInfo(new Response.Listener<List<Topic>>() {
			@Override
			public void onResponse(List<Topic> response) {
				mTopicList = response;
				//RecyclerView 数据填充
				mainActivityEventsAdapter = new MainActivityEventsAdapter(response, MainActivity.this);
				mRecyclerView.setAdapter(mainActivityEventsAdapter);
				mainActivityEventsAdapter.setOnItemClickListener(MainActivity.this);
				swipeLayout.setRefreshing(false);
				isRefresh = false;
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Crouton.makeText(MainActivity.this, getString(R.string.server_error), Style.ALERT).show();
				swipeLayout.setRefreshing(false);
				isRefresh = false;
			}
		});
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
			initData();

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		RequestManager.getInstance().cancel(this);
	}

	@Override
	public void onItemClick(View view, int position) {
		Intent intent = new Intent();
		intent.putExtra("eventInfo", mTopicList.get(position));
		intent.setClass(this, EventInfoActivity.class);
		startActivity(intent);
	}
}
