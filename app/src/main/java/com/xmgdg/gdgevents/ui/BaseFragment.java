package com.xmgdg.gdgevents.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by qixingchen on 15/8/31.
 * Fragment 的抽象
 */
public abstract class BaseFragment extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();
    protected Activity mActivity;
    protected Context mContext;
    protected View view;

    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState);

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        findViews(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViewsAndSetEvent();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = getActivity();
    }

    protected abstract void findViews(View view);

    protected abstract void initViewsAndSetEvent();
}
