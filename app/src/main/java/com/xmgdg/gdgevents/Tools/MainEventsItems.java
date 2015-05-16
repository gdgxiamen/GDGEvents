package com.xmgdg.gdgevents.Tools;


/**
 * Created by Yulan on 2015/5/16.
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
