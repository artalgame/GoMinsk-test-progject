package com.flaxtreme.gominsktestapp.interfaces;

import android.database.sqlite.SQLiteDatabase;

public interface IDBTable {
	public void onCreate(SQLiteDatabase db);
	public void onUpgrate(SQLiteDatabase db);
	public void onDelete(SQLiteDatabase db);
	public String getTableName(SQLiteDatabase db);
}
