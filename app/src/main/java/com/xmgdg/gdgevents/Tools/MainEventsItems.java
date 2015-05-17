package com.xmgdg.gdgevents.Tools;


/**
 * Created by Yulan on 2015/5/16.
 * 主界面,举办的活动数组
 * MainEventsEachItem 是单个活动,由 举办时间,活动标题和活动地点组成
 * MainEventsItems 构造时传入 举办时间,活动标题和活动地点组成 的数组
 */
public class MainEventsItems {

	public static class MainEventsEachItem {

		public String eventsTime, eventsTitle, eventsLocation;

		public MainEventsEachItem(String eventsTime, String eventsTitle, String eventsLocation) {
			this.eventsTime = eventsTime;
			this.eventsTitle = eventsTitle;
			this.eventsLocation = eventsLocation;
		}
	}

	public MainEventsEachItem MainEventsItems[];

	public MainEventsItems(String eventsTime[], String eventsTitle[], String
			eventsLocation[]) {
		int length = eventsTime.length;
		MainEventsItems = new MainEventsEachItem[length];
		for (int i = 0; i < length; i++) {
			MainEventsItems[i] = new MainEventsEachItem(eventsTime[i], eventsTitle[i],
					eventsLocation[i]);
		}
	}

}
