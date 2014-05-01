package com.flaxtreme.gominsktestapp.db.table;

import android.database.sqlite.SQLiteDatabase;

import com.flaxtreme.gominsktestapp.interfaces.IDBTable;

public class GeoObjectTable implements IDBTable {
	
	public static final String KEY_ID = "_id";
	
	public static final String KEY_NAME_RU = "name_ru";
	public static final String KEY_NAME_BY = "name_by";
	public static final String KEY_NAME_EN = "name_en";
	
	public static final String KEY_ADDRESS_RU = "address_ru";
	public static final String KEY_ADDRESS_BY = "address_by";
	public static final String KEY_ADDRESS_EN = "address_en";
	
	public static final String KEY_DESCRIPTION_RU = "description_ru";
	public static final String KEY_DESCRIPTION_BY = "description_by";
	public static final String KEY_DESCRIPTION_EN = "description_en";
	
	
	public static final String KEY_LONGITUDE = "longitude";
	public static final String KEY_LATITUDE = "latitude";
	
	public static final String KEY_INFO = "info";
	public static final String KEY_WORKTIME = "worktime";
	public static final String KEY_MARKER_ID = "marker_id";
	
	public static final String KEY_IMAGE_URI = "image_uri";
	public static final String KEY_IMAGE_INFO = "image_info";
	
	public static final String ALIAS_KEY_ID="geo_object_id_alias";
	
	public static final String[] COLUMNS = new String[] {
			KEY_ID,
			KEY_NAME_RU,
			KEY_NAME_BY,
			KEY_NAME_EN,
			KEY_ADDRESS_RU,
			KEY_ADDRESS_BY,
			KEY_ADDRESS_EN,
			KEY_DESCRIPTION_RU,
			KEY_DESCRIPTION_BY,
			KEY_DESCRIPTION_EN,
			KEY_LATITUDE,
			KEY_LONGITUDE,
			KEY_INFO,
			KEY_WORKTIME,
			KEY_MARKER_ID,
			KEY_IMAGE_INFO,
			KEY_IMAGE_URI
		};
	
	public static final String TABLE_NAME = "GEO_OBJECT";
	
	public static final String QUERY_CREATE_TABLE = "create table " + 
			TABLE_NAME + 
			" (" + 
			KEY_ID + " integer primary key not null, " +
			KEY_NAME_RU + " text not null, " +
			KEY_NAME_BY + " text not null, " +
			KEY_NAME_EN + " text not null, " +
			KEY_ADDRESS_RU + " text, " +
			KEY_ADDRESS_BY + " text, " +
			KEY_ADDRESS_EN + " text, " +
			KEY_DESCRIPTION_RU + " text, " +
			KEY_DESCRIPTION_BY + " text, " +
			KEY_DESCRIPTION_EN + " text, " +
			KEY_LONGITUDE + " real, " +
			KEY_LATITUDE + " real, " +
			KEY_INFO + " text, " +
			KEY_WORKTIME + " text, " +
			KEY_MARKER_ID + " integer, " +
			KEY_IMAGE_INFO + " text, " +
			KEY_IMAGE_URI + " text);";
	

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
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}

	@Override
	public void onDelete(SQLiteDatabase db) {
		db.execSQL(QUERY_DROP_TABLE);
	}

	public static String columnsToString() {
		String string = TABLE_NAME+"."+COLUMNS[0] + ", ";
		for(int i=1; i<COLUMNS.length; i++){
			string +=( TABLE_NAME+"."+COLUMNS[i]);
			if(COLUMNS.length-i>1)
				string += ", ";
		}
		return string;
	}
}
