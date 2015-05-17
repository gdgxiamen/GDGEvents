package com.xmgdg.gdgevents.Tools;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xmgdg.gdgevents.R;


/**
 * Created by Yulan on 2015/5/16.
 * 主界面,举办的活动 RecyclerView 适配器
 * 按键监听器在 ViewHolder 内添加,
 */
public class MainActivityEventsAdapter extends RecyclerView.Adapter<MainActivityEventsAdapter.ViewHolder> {

	private MainEventsItems mainEventsItems;
	private Activity activity;
	private static String logtag = "主界面活动适配器";

	public MainActivityEventsAdapter(MainEventsItems mainEventsItems, Activity activity) {
		this.mainEventsItems = mainEventsItems;
		this.activity = activity;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		//界面元素
		public TextView eventTime, eventTitle, eventLocation;

		public ViewHolder(View v) {
			super(v);
			v.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d(logtag, "onClick--> position = " + getPosition());
				}
			});

			eventTime = (TextView) v.findViewById(R.id.events_time_text_view);
			eventTitle = (TextView) v.findViewById(R.id.events_title_text_view);
			eventLocation = (TextView) v.findViewById(R.id.events_location_text_view);

			//todo:可在这里添加部件的按键监听
		}
	}


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.main_events_card_view, parent, false);
		// set the view's size, margins, paddings and layout parameters
		ViewHolder vh = new ViewHolder((CardView) v);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.eventTime.setText(mainEventsItems.MainEventsItems[position].eventsTime);
		holder.eventTitle.setText(mainEventsItems.MainEventsItems[position].eventsTitle);
		holder.eventLocation.setText(mainEventsItems.MainEventsItems[position].eventsLocation);
	}

	@Override
	public int getItemCount() {
		return mainEventsItems == null ? 0 : mainEventsItems.MainEventsItems.length;
	}
}
