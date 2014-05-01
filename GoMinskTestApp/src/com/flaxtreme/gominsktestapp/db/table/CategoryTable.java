package com.flaxtreme.gominsktestapp.db.table;

import android.database.sqlite.SQLiteDatabase;

import com.flaxtreme.gominsktestapp.interfaces.IDBTable;

public class CategoryTable implements IDBTable{
	
	public static final String KEY_ID = "_id";
	
	public static final String KEY_NAME_RU = "name_ru";
	public static final String KEY_NAME_BY = "name_by";
	public static final String KEY_NAME_EN = "name_en";
	
	public static final String KEY_ICON_URI = "icon_uri";
	public static final String KEY_COLOR_STYLE_STRING = "color";
	public static final String KEY_PARENT_ID = "parent_id";
	public static final String KEY_SORT = "sort";
	
	public static final String[] COLUMNS = new String[] {
		KEY_ID,
		KEY_NAME_RU,
		KEY_NAME_BY,
		KEY_NAME_EN,
		KEY_ICON_URI,
		KEY_COLOR_STYLE_STRING,
		KEY_PARENT_ID,
		KEY_SORT
	};
	
	public static final String TABLE_NAME = "CATEGORY_OBJECT";
	
	public static final String QUERY_CREATE_TABLE = "create table " + 
			TABLE_NAME + 
			" (" + 
			KEY_ID + " integer primary key NOT NULL, " +
			KEY_NAME_RU + " text not null, " +
			KEY_NAME_BY + " text not null, " +
			KEY_NAME_EN + " text not null, " +
			KEY_ICON_URI + " text, " +
			KEY_COLOR_STYLE_STRING + " text, " +
			KEY_PARENT_ID + " integer, " +
			KEY_SORT + " integer);";
	
	public static final String QUERY_DROP_TABLE="DROP TABLE IF EXISTS '" + TABLE_NAME + "'";
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(QUERY_CREATE_TABLE);
	}

	@Override
	public void onUpgrate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTableName(SQLiteDatabase db) {
		return TABLE_NAME;
	}

	@Override
	public void onDelete(SQLiteDatabase db) {
		db.execSQL(QUERY_DROP_TABLE);
		
	}
}
