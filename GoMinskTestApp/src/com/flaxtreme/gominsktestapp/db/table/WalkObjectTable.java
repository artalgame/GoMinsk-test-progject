package com.flaxtreme.gominsktestapp.db.table;

import android.database.sqlite.SQLiteDatabase;

import com.flaxtreme.gominsktestapp.interfaces.IDBTable;

public class WalkObjectTable implements IDBTable {
	
public static final String KEY_ID = "_id";
	
	public static final String KEY_NAME_RU = "name_ru";
	public static final String KEY_NAME_BY = "name_by";
	public static final String KEY_NAME_EN = "name_en";
	
	public static final String KEY_DESCRIPTION_RU = "description_ru";
	public static final String KEY_DESCRIPTION_BY = "description_by";
	public static final String KEY_DESCRIPTION_EN = "description_en";

	public static final String KEY_COLOR_STYLE_STRING = "style";
	public static final String KEY_SORT = "sort";
	
	public static final String KEY_TIME = "time";
	public static final String KEY_DISTANCE="distance";
	public static final String[] COLUMNS = new String[] {
		KEY_ID,
		KEY_NAME_RU,
		KEY_NAME_BY,
		KEY_NAME_EN,
		KEY_DESCRIPTION_RU,
		KEY_DESCRIPTION_BY,
		KEY_DESCRIPTION_EN,
		KEY_COLOR_STYLE_STRING,
		KEY_SORT,
		KEY_TIME,
		KEY_DISTANCE
	};
	
	public static final String TABLE_NAME = "WALK_OBJECT";
	
	public static final String QUERY_CREATE_TABLE = "create table " + 
			TABLE_NAME + 
			" (" + 
			KEY_ID + " integer primary key not null, " +
			KEY_NAME_RU + " text not null, " +
			KEY_NAME_BY + " text not null, " +
			KEY_NAME_EN + " text not null, " +
			KEY_DESCRIPTION_RU + " text, " +
			KEY_DESCRIPTION_BY + " text, " +
			KEY_DESCRIPTION_EN + " text, " +
			KEY_COLOR_STYLE_STRING + " text, " +
			KEY_TIME + " integer, "+
			KEY_SORT + " integer, "+
			KEY_DISTANCE + " integer);";
			
	public static final String QUERY_DROP_TABLE="DROP TABLE IF EXISTS '" + TABLE_NAME + "'";
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(QUERY_CREATE_TABLE);
	}

	@Override
	public void onUpgrate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDelete(SQLiteDatabase db) {
		db.execSQL(QUERY_DROP_TABLE);
		
	}

	@Override
	public String getTableName(SQLiteDatabase db) {
		return TABLE_NAME;
	}
}
