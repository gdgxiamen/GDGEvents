package com.xmgdg.gdgevents.Tools;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by Yulan on 2015/5/21.
 * 带有 activity 的工具类
 */
public class ToolWithActivity {
	private Activity activity;

	public ToolWithActivity(Activity activity) {
		this.activity = activity;
	}

	//判断 intent 是否安全
	public boolean isIntentSafe(Intent intent) {
		PackageManager packageManager = activity.getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
		return activities.size() > 0;
	}
}
