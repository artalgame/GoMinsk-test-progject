package com.flaxtreme.gominsktestapp;

import com.flaxtreme.gominsktestapp.db.table.CategoryTable;
import com.flaxtreme.gominsktestapp.db.table.GeoObjectCategoryTable;
import com.flaxtreme.gominsktestapp.db.table.GeoObjectTable;
import com.flaxtreme.gominsktestapp.db.table.WalkObjectGeoObjectTable;
import com.flaxtreme.gominsktestapp.db.table.WalkObjectTable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class GoMinskDBAdapter {
	public static final String DATABASE_NAME = "gominsktest.db";
	public static final int DATABASE_VERSION = 1;
	
	private SQLiteDatabase db;
	private final Context context;
	
	private static final String TAG = "GoMinsk-GoMinskDBAdapter";

	private GoMinskDBOpenHelper dbHelper;
	
	public GoMinskDBAdapter(Context context){
		this.context = context;
		dbHelper = new GoMinskDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void close(){
		db.close();
	}
	
	public void open()throws SQLiteException {
		try{
			db = dbHelper.getWritableDatabase();
		}catch (SQLiteException ex){
			Log.e(TAG, "Could not return writteble database. Possyble memory or priviledge limit.");
			db = dbHelper.getReadableDatabase();
		}
	}
	
	public SQLiteDatabase getDB()
	{
		return db;
	}
	
	public void deleteDataBase(){
		new GeoObjectTable().onDelete(db);
		new CategoryTable().onDelete(db);
		new GeoObjectCategoryTable().onDelete(db);
	}
	
	public Context getContext() {
		return context;
	}

	private static class GoMinskDBOpenHelper extends SQLiteOpenHelper{
		
		public GoMinskDBOpenHelper(Context context, String name, CursorFactory factory, int version){
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			new GeoObjectTable().onCreate(db);
			new CategoryTable().onCreate(db);
			new GeoObjectCategoryTable().onCreate(db);
			new WalkObjectTable().onCreate(db);
			new WalkObjectGeoObjectTable().onCreate(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
		
	}
}
