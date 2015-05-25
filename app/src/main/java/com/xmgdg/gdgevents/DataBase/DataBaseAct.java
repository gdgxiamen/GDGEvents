package com.xmgdg.gdgevents.DataBase;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xmgdg.gdgevents.R;
import com.xmgdg.gdgevents.Tools.AppStat;
import com.xmgdg.gdgevents.Tools.Tool;
import com.xmgdg.gdgevents.app.App;
import com.xmgdg.gdgevents.model.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yulan on 2015/5/24.
 * 数据库各种操作
 */
public class DataBaseAct {

	//class内部用
	private Application mapplication;
	private Context mcontext;
	private static final String logtag = "数据库操作";

	//数据库
	private DataBaseOpenHelper dataBaseOpenHelper;
	private SQLiteDatabase db;
	private static DataBaseAct dataBaseAct;

	public static DataBaseAct getDataBaseAct() {
		if (dataBaseAct == null) {
			synchronized (DataBaseAct.class) {
				if (dataBaseAct == null) {
					dataBaseAct = new DataBaseAct();
				}
			}
		}
		return dataBaseAct;
	}

	private DataBaseAct() {
		mapplication = App.getInstance();
		mcontext = mapplication.getApplicationContext();
		initDataBase();
	}

	private void initDataBase() {
		dataBaseOpenHelper = new DataBaseOpenHelper(mcontext, mcontext.getString(R
				.string.DataBaseFileName), null, AppStat.DataBaseVersion.nowVersion);
		db = dataBaseOpenHelper.getWritableDatabase();
	}

	//Add
	//添加一个活动
	private void addEvent(Topic topic) {
		ContentValues content = new ContentValues();

		content.put(mcontext.getString(R.string.DataBaseID), topic.getId());
		content.put(mcontext.getString(R.string.DataBaseGroupID), topic.getGroup());
		content.put(mcontext.getString(R.string.DataBaseStart), topic.getStart());
		content.put(mcontext.getString(R.string.DataBaseTimezoneName), topic.getTimezoneName());
		content.put(mcontext.getString(R.string.DataBaseParticipantsCount), topic.getParticipantsCount());
		content.put(mcontext.getString(R.string.DataBaseTitle), topic.getTitle());
		content.put(mcontext.getString(R.string.DataBaseIconUrl), topic.getIconUrl());
		content.put(mcontext.getString(R.string.DataBaseLink), topic.getLink());
		content.put(mcontext.getString(R.string.DataBaseLocation), topic.getLocation());
		content.put(mcontext.getString(R.string.DataBaseEnd), topic.getEnd());
		content.put(mcontext.getString(R.string.DataBaseTemporalRelation), topic.getTemporalRelation());
		content.put(mcontext.getString(R.string.DataBaseGPlusEventLink), topic.getgPlusEventLink());
		content.put(mcontext.getString(R.string.DataBaseDescription), topic.getDescription());
		//noinspection ConstantConditions
		long startTime = Tool.getDateFromString(topic.getStart()).getTime();
		content.put(mcontext.getString(R.string.DataBaseStartTimeMS), startTime);
		db.replace(mcontext.getString(R.string.DataBaseTableMainEvents), null, content);
	}

	//添加一堆事件
	public void addEvents(List<Topic> topicList) {
		if (topicList == null) {
			return;
		}
		for (Topic topic : topicList) {
			addEvent(topic);
		}
	}


	//read
	//读取一个活动
	private Topic readEvent(Cursor cursor) {
		Topic topic = new Topic();

		topic.setId(cursor.getString(AppStat.DataBaseColumnIndex.id));
		topic.setGroup(cursor.getString(AppStat.DataBaseColumnIndex.groupId));
		topic.setDescription(cursor.getString(AppStat.DataBaseColumnIndex.description));
		topic.setStart(cursor.getString(AppStat.DataBaseColumnIndex.start));
		topic.setTimezoneName(cursor.getString(AppStat.DataBaseColumnIndex.timezoneName));
		topic.setParticipantsCount(cursor.getString(AppStat.DataBaseColumnIndex.participantsCount));
		topic.setTitle(cursor.getString(AppStat.DataBaseColumnIndex.title));
		topic.setIconUrl(cursor.getString(AppStat.DataBaseColumnIndex.iconUrl));
		topic.setLink(cursor.getString(AppStat.DataBaseColumnIndex.link));
		topic.setLocation(cursor.getString(AppStat.DataBaseColumnIndex.location));
		topic.setEnd(cursor.getString(AppStat.DataBaseColumnIndex.end));
		topic.setTemporalRelation(cursor.getString(AppStat.DataBaseColumnIndex.temporalRelation));
		topic.setgPlusEventLink(cursor.getString(AppStat.DataBaseColumnIndex.gPlusEventLink));

		return topic;
	}

	//获取所有活动
	public List<Topic> readEvents(String groupID) {
		Cursor EventCursor = db.query(
				mcontext.getString(R.string.DataBaseTableMainEvents),//table name
				null,//返回的列,null表示全选
				mcontext.getString(R.string.DataBaseGroupID) + "=?",//条件
				new String[]{groupID},//条件的参数
				null,//groupBy
				null,//having
				"startTimeMS DESC"//orderBy
		);

		List<Topic> topics = new ArrayList<>();
		EventCursor.moveToFirst();
		while (!EventCursor.isAfterLast()) {
			topics.add(readEvent(EventCursor));
			EventCursor.moveToNext();
		}
		EventCursor.close();
		return topics;
	}

}
