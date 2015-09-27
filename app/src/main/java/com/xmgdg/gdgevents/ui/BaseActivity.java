package com.xmgdg.gdgevents.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xmgdg.gdgevents.app.App;

/**
 * Created by qixingchen on 15/8/31.
 * Activity 的抽象类
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();
    protected Context mContext;

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mContext = this;
        findView();
        initView();
        setViewEvent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.setActivitys(this);
    }

    /**
     * 获取布局控件
     */
    protected abstract void findView();

    /**
     * 初始化View的一些数据
     */
    protected abstract void initView();

    /**
     * 设置点击监听
     */
    protected abstract void setViewEvent();

}
