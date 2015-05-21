package com.xmgdg.gdgevents.Tools;

import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xmgdg.gdgevents.MainActivity;
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
	private static final String logtag = "主界面活动适配器";
	private int position;

	private List<Topic> topicList;
	private RecylcerViewOnItemClickListener listener;

	public MainActivityEventsAdapter(List<Topic> topicList, Activity activity) {
		this.topicList = topicList;
		this.activity = activity;
	}


	public static class ViewHolder extends RecyclerView.ViewHolder implements
			OnClickListener,View.OnCreateContextMenuListener {

		//界面元素
		public TextView eventTime, eventTitle, eventLocation;
		public TextView EventTime, EventTitle, EventLocation, EventEndTime;

		private RecylcerViewOnItemClickListener listener;

		public ViewHolder(View v, RecylcerViewOnItemClickListener listener) {
			super(v);

			this.listener = listener;
			v.setOnClickListener(this);
			v.setOnCreateContextMenuListener(this);

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

		@Override
		public void onClick(View v) {
			if(listener != null) {
				listener.onItemClick(v, getPosition());
			}
		}

		//长按监听器
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
			Activity activity = MainActivity.activity;
			menu.add(0, AppStat.MainEventsContextMenuID.share, 0, activity.getString(R
					.string.share));
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				menu.add(0, AppStat.MainEventsContextMenuID.addToCalendar, 1, activity
						.getString(R.string.add_to_calendar));
			}
			menu.add(0, AppStat.MainEventsContextMenuID.openInGooglePlus, 2, activity
					.getString(R.string.open_google_plus));
		}

	}


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.main_events_card_view, parent, false);
		// set the view's size, margins, paddings and layout parameters
		ViewHolder vh = new ViewHolder((CardView) v, listener);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {

		//todo:移除就界面
		//删除 +0800
		holder.eventTime.setText(topicList.get(position).getStart().replace("+0800", ""));
		holder.eventTitle.setText(topicList.get(position).getTitle());
		holder.eventLocation.setText(topicList.get(position).getLocation());

		holder.EventTime.setText(topicList.get(position).getStart().replace("+0800", ""));
		holder.EventTitle.setText(topicList.get(position).getTitle());
		holder.EventLocation.setText(topicList.get(position).getLocation());
		holder.EventEndTime.setText("结束于:" + topicList.get(position).getEnd());

		holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				setPosition(position);
				return false;
			}
		});

	}

	@Override
	public void onViewRecycled(ViewHolder holder) {
		holder.itemView.setOnLongClickListener(null);
		super.onViewRecycled(holder);
	}

	@Override
	public int getItemCount() {
		return topicList == null ? 0 : topicList.size();
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public List<Topic> getTopicList() {
		return topicList;
	}

	//更新数据
	public void notifyDateChanged(List<Topic> topicList) {
		this.topicList = topicList;
		this.notifyDataSetChanged();
	}

	/** 设置item点击事件 **/
	public void setOnItemClickListener(RecylcerViewOnItemClickListener listener) {
		this.listener = listener;
	}

	public interface RecylcerViewOnItemClickListener {
		public void onItemClick(View view, int position);
	}


}
