package com.xmgdg.gdgevents.Tools;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by Yulan on 2015/5/16.
 */
public class Tool {

	public static void set下拉刷新颜色(SwipeRefreshLayout swipeLayout) {
		swipeLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
				android.R.color.holo_blue_light,
				android.R.color.holo_green_light, android.R.color.holo_green_light);
	}
}
