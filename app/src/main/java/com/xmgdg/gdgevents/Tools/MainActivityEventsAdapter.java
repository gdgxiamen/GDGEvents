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
import com.xmgdg.gdgevents.model.Topic;

import java.util.List;


/**
 * Created by Yulan on 2015/5/16.
 * 主界面,举办的活动 RecyclerView 适配器
 * 按键监听器在 ViewHolder 内添加,
 */
public class MainActivityEventsAdapter extends RecyclerView.Adapter<MainActivityEventsAdapter.ViewHolder> {

	private Activity activity;
	private static String logtag = "主界面活动适配器";

	private List<Topic> topicList;

	public MainActivityEventsAdapter(List<Topic> topicList, Activity activity) {
		this.topicList = topicList;
		this.activity = activity;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		//界面元素
		public TextView eventTime, eventTitle, eventLocation;
		public TextView EventTime, EventTitle, EventLocation, EventEndTime;

		public ViewHolder(View v) {
			super(v);
			v.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d(logtag, "onClick--> position = " + getPosition());
				}
			});
			//todo:移除就界面
			eventTime = (TextView) v.findViewById(R.id.events_time_text_view);
			eventTitle = (TextView) v.findViewById(R.id.events_title_text_view);
			eventLocation = (TextView) v.findViewById(R.id.events_location_text_view);

			EventTime = (TextView) v.findViewById(R.id.event_time);
			EventTitle = (TextView) v.findViewById(R.id.event_title);
			EventLocation = (TextView) v.findViewById(R.id.event_location);
			EventEndTime = (TextView) v.findViewById(R.id.event_end_time);


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

		//todo:移除就界面
		//删除 +0800
		holder.eventTime.setText(topicList.get(position).getStart().replace("+0800", ""));
		holder.eventTitle.setText(topicList.get(position).getTitle());
		holder.eventLocation.setText(topicList.get(position).getLocation());

		holder.EventTime.setText(topicList.get(position).getStart().replace("+0800", ""));
		holder.EventTitle.setText(topicList.get(position).getTitle());
		holder.EventLocation.setText(topicList.get(position).getLocation());
		holder.EventEndTime.setText("结束于:" + topicList.get(position).getEnd());

	}

	@Override
	public int getItemCount() {
		return topicList == null ? 0 : topicList.size();
	}

	//更新数据
	public void notifyDateChanged(List<Topic> topicList) {
		this.topicList = topicList;
		this.notifyDataSetChanged();
	}

}
