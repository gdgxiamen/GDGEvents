package com.xmgdg.gdgevents;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.otto.Subscribe;
import com.xmgdg.gdgevents.DataBase.DataBaseAct;
import com.xmgdg.gdgevents.Tools.AppStat;
import com.xmgdg.gdgevents.Tools.OnMainEventsContextMenuSelect;
import com.xmgdg.gdgevents.Tools.Tool;
import com.xmgdg.gdgevents.adapter.MainActivityEventsAdapter;
import com.xmgdg.gdgevents.app.App;
import com.xmgdg.gdgevents.drawer.MaterialDrawer;
import com.xmgdg.gdgevents.model.Topic;
import com.xmgdg.gdgevents.network.RequestManager;
import com.xmgdg.gdgevents.otto.UserInfo;
import com.xmgdg.gdgevents.ui.BaseActivity;
import com.xmgdg.gdgevents.ui.login.LoginActivity;
import com.xmgdg.gdgevents.utils.GooglePlusLoginUtils;

import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * 主界面,显示举办的活动
 */
public class MainActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        MainActivityEventsAdapter.RecylcerViewOnItemClickListener {


    //TAG
    private static final String TAG = MainActivity.class.getName();
    public static Activity activity;
    //toolbar
    private Toolbar toolbar;
    private MaterialDrawer drawer;
    private Spinner mSpinner;
    //RecyclerView
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainActivityEventsAdapter mainActivityEventsAdapter;
    private int recyclerViewFirstPosition;
    //下拉刷新
    private boolean isRefresh = false;
    private SwipeRefreshLayout swipeLayout;
    //Context Menu 监听器
    private OnMainEventsContextMenuSelect onMainEventsContextMenuSelect;
    //DataBase
    private DataBaseAct dataBaseAct = DataBaseAct.getDataBaseAct();
    private List<Topic> mTopicList = new ArrayList<>();

    //login button
    private GooglePlusLoginUtils gLogin;
    private Button loginButton;
    //citys
    private String[] cityIDs;
    private int initPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
    }

    @Override
    protected void findView() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        mRecyclerView = (RecyclerView) findViewById(R.id.mainActivityRecyclerView);
        loginButton = (Button) findViewById(R.id.login_button);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.mainActivitySwipeToRefreash);
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        drawer = new MaterialDrawer(this, toolbar);
        gLogin = new GooglePlusLoginUtils(this, R.id.btn_gplus_sign_in);
        String initpos = App.getPrefer(AppStat.Preferences.InitPosition);
        if (initpos.compareTo("") == 0) {
            initPosition = 0;
        } else {
            initPosition = Integer.valueOf(initpos);
        }
        initSpinner();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //RecyclerView 数据填充
        mainActivityEventsAdapter = new MainActivityEventsAdapter(mTopicList, this);
        mRecyclerView.setAdapter(mainActivityEventsAdapter);
        //SwipeRefresh
        if (Build.VERSION.SDK_INT >= 14) {
            Tool.set下拉刷新颜色(swipeLayout);
        }

    }

    @Override
    protected void setViewEvent() {
        cityIDs = getResources().getStringArray(R.array.city_ids);
        mainActivityEventsAdapter.setOnItemClickListener(MainActivity.this);
        onMainEventsContextMenuSelect = new OnMainEventsContextMenuSelect(this);
        swipeLayout.setOnRefreshListener(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(activity, LoginActivity.class);
                activity.startActivityForResult(login, AppStat.MainActivityIntentCode.SIGN_IN);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        gLogin.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gLogin.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case AppStat.MainActivityIntentCode.GPLUS_SIGN_IN:
                gLogin.onActivityResult(requestCode, resultCode, data);
                break;
            case AppStat.MainActivityIntentCode.SIGN_IN:

                break;
            default:
                Log.w(TAG, "Unknown requestCode:" + requestCode);
        }
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
        initDataFromDataBase();
        mRecyclerView.scrollToPosition(recyclerViewFirstPosition);
    }

    private void initSpinner() {
        View spinnerContanner = LayoutInflater.from(this).inflate(R.layout.tool_bar_spinner, toolbar, false);
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        toolbar.addView(spinnerContanner, layoutParams);
        //获得城市
        final String[] cities = getResources().getStringArray(R.array.city_names);
        mSpinner = (Spinner) spinnerContanner.findViewById(R.id.tool_bar_spinner);
        mSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities));
        mSpinner.setSelection(initPosition);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, cities[position], Toast.LENGTH_LONG).show();
                initPosition = position;
                App.setPrefer(AppStat.Preferences.InitPosition, String.valueOf(position));
                updateDisplay(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initDataFromDataBase() {
        List<Topic> topics = dataBaseAct.readEvents(cityIDs[initPosition]);
        mTopicList = topics;
        mainActivityEventsAdapter.notifyDateChanged(topics);
        if (topics.isEmpty()) {
        /* 初始化时显示刷新进度条
        *  第一行是解决初始化不出现刷新条的临时方法,后续可能会被 SwipeRefreshLayout 改进
		* */
            swipeLayout.setProgressViewOffset(false, 0,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                            getResources().getDisplayMetrics()));
            swipeLayout.setRefreshing(true);
            updateDisplay(initPosition);
        }


    }


    //传入城市序号，更新显示列表
    public void updateDisplay(int posistion) {

        swipeLayout.setRefreshing(true);
        RequestManager.getInstance().getTopicInfo(RequestManager.getUrl(cityIDs[posistion]), new Response.Listener<List<Topic>>() {
            @Override
            public void onResponse(List<Topic> response) {
                mTopicList = response;
                //写入数据库
                dataBaseAct.addEvents(response);
                mainActivityEventsAdapter.notifyDateChanged(response);
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
            updateDisplay(initPosition);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        recyclerViewFirstPosition = ((LinearLayoutManager) mLayoutManager)
                .findFirstVisibleItemPosition();
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

    @Subscribe
    public void onLoginSuccess(UserInfo userInfo) {
        drawer.setUserInfo(userInfo.personName, userInfo.email, userInfo.personPhotoUrl);
        loginButton.setVisibility(View.GONE);
        findViewById(R.id.btn_gplus_sign_in).setVisibility(View.GONE);
    }

}
