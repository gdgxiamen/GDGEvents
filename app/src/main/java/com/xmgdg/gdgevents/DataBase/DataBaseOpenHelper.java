package com.xmgdg.gdgevents.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yulan on 2015/5/24.
 * 数据库 String name 是 R.string.DataBaseFileName
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {

	public DataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//主活动数据表
		db.execSQL("Create Table MainEvents (" +
				"id                 TEXT PRIMARY KEY," +
				"groupId            TEXT," +
				"description        TEXT," +
				"start              TEXT," +
				"timezoneName       TEXT," +
				"participantsCount  TEXT," +
				"title              TEXT," +
				"iconUrl            TEXT," +
				"link               TEXT," +
				"location           TEXT," +
				"end                TEXT," +
				"temporalRelation   TEXT," +
				"gPlusEventLink     TEXT," +
				"startTimeMS        INTEGER" +
				" );");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
