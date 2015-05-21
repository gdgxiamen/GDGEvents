package com.xmgdg.gdgevents.Tools;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.view.MenuItem;

import com.xmgdg.gdgevents.R;
import com.xmgdg.gdgevents.model.Topic;

import java.util.Date;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by Yulan on 2015/5/21.
 * 主界面 活动上下文菜单 监听器实现
 */
public class OnMainEventsContextMenuSelect {
	Activity activity;
	ToolWithActivity toolWithActivity;


	public OnMainEventsContextMenuSelect(Activity activity) {
		this.activity = activity;
		toolWithActivity = new ToolWithActivity(activity);
	}

	public boolean OnLongClickListener(MenuItem item, Topic topic) {

		switch (item.getItemId()) {
			case AppStat.MainEventsContextMenuID.share:
				shareEvents(topic);
				return true;
			case AppStat.MainEventsContextMenuID.addToCalendar:
				addToCalendar(topic);
				return true;
			case AppStat.MainEventsContextMenuID.openInGooglePlus:
				openInGooglePlus(topic);
				return true;
		}

		return true;
	}


	//分享活动到其他应用
	private void shareEvents(Topic topic) {

		String shareString = topic.getSummary();

		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, shareString);
		shareIntent.setType("text/plain");
		activity.startActivity(shareIntent);
	}

	//新增到日历
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private void addToCalendar(Topic topic) {
		Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);

		Date beginTime = Tool.getDateFromString(topic.getStart());
		Date endTime = Tool.getDateFromString(topic.getEnd());
		assert beginTime != null;
		assert endTime != null;
		calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTime());
		calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTime());
		calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, topic.getDescription());
		calendarIntent.putExtra(CalendarContract.Events.TITLE, topic.getTitle());
		calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, topic.getLocation());

		boolean isIntentSafe = toolWithActivity.isIntentSafe(calendarIntent);
		if (isIntentSafe) {
			activity.startActivity(calendarIntent);
		} else {
			Crouton.makeText(activity, activity.getString(R.string.no_calendar_app_found),
					Style.ALERT).show();
		}
	}

	//使用G+打开
	private void openInGooglePlus(Topic topic) {
		Uri GooglePlusPage = Uri.parse(topic.getgPlusEventLink());
		Intent webIntent = new Intent(Intent.ACTION_VIEW, GooglePlusPage);
		if (toolWithActivity.isIntentSafe(webIntent)) {
			activity.startActivity(webIntent);
		} else {
			Crouton.makeText(activity, activity.getString(R.string.no_browser_app_found),
					Style.ALERT).show();
		}
	}
}
