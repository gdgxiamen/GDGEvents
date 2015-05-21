package com.xmgdg.gdgevents.Tools;

import android.support.v4.widget.SwipeRefreshLayout;

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



}
