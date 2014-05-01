package com.flaxtreme.gominsktestapp.db.table;

import android.database.sqlite.SQLiteDatabase;

import com.flaxtreme.gominsktestapp.interfaces.IDBTable;

public class WalkObjectGeoObjectTable implements IDBTable {
	
	public static final String KEY_ID = "_id";
	public static final String KEY_WALK_OBJECT_ID = "walk_object_id";
	public static final String KEY_GEO_OBJECT_ID = "geo_object_id";
	public static final String KEY_SORT = "sort";
	
	public static final String[] COLUMNS = new String[] {
		KEY_ID,
		KEY_WALK_OBJECT_ID,
		KEY_GEO_OBJECT_ID,
		KEY_SORT
	};
	public static final String TABLE_NAME = "WALK_OBJECT_GEO_OBJECT_TABLE";
	
	public static final String QUERY_CREATE_TABLE = "create table " + 
			TABLE_NAME + 
			" (" + 
			KEY_ID + " integer primary key AUTOINCREMENT NOT NULL, " +
			KEY_WALK_OBJECT_ID + " integer not null, " +
			KEY_GEO_OBJECT_ID + " integer not null, " +
			KEY_SORT + " integer not null);";
	
	public static final String QUERY_DROP_TABLE="DROP TABLE IF EXISTS '" + TABLE_NAME  + "'";
	
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
