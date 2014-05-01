package com.flaxtreme.gominsktestapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.flaxtreme.gominsktestapp.db.table.WalkObjectGeoObjectTable;
import com.flaxtreme.gominsktestapp.entity.WalkObjectGeoObject;

public class WalkObjectGeoObjectDBAdapter extends AbstractDBAdapter<WalkObjectGeoObject>{
	
	public WalkObjectGeoObjectDBAdapter(Context context){
		super(context);
	}

	@Override
	public long insertObject(WalkObjectGeoObject item) {
		if(item != null)
		{
			ContentValues newWalkObjectGeoObjectRow = new ContentValues();
			
			newWalkObjectGeoObjectRow.put(WalkObjectGeoObjectTable.KEY_WALK_OBJECT_ID, item.getWalkObjectId());
			newWalkObjectGeoObjectRow.put(WalkObjectGeoObjectTable.KEY_GEO_OBJECT_ID, item.getGeoObjectId());
			newWalkObjectGeoObjectRow.put(WalkObjectGeoObjectTable.KEY_SORT, item.getSort());
			
			return getDB().insert(WalkObjectGeoObjectTable.TABLE_NAME, null, newWalkObjectGeoObjectRow);
		}
		else
		{
			return -1;
		}
	}

	@Override
	public boolean removeObject(long objectID) {
		return getDB().delete(WalkObjectGeoObjectTable.TABLE_NAME, WalkObjectGeoObjectTable.KEY_ID+ "="+ objectID, null) > 0;
	}

	@Override
	public Cursor getAllObjectsCursor() {
		return getDB().query(WalkObjectGeoObjectTable.TABLE_NAME, WalkObjectGeoObjectTable.COLUMNS
				,null,null,null,null,null);
	}

	@Override
	public Cursor getCursorForObject(long objectID) {
		
		Cursor result = getDB().query(true,
				WalkObjectGeoObjectTable.TABLE_NAME, 
				WalkObjectGeoObjectTable.COLUMNS, 
				WalkObjectGeoObjectTable.KEY_ID + "=" + objectID,
				null, null, null, null, null);
		if((result.getCount() == 0) || !result.moveToFirst()){
			return null;
		}
		return result;
		
	}
	
	@Override
	public WalkObjectGeoObject getObjectFromCursor(Cursor resultCursor){
		
		long id = resultCursor.getLong(resultCursor.getColumnIndex(WalkObjectGeoObjectTable.KEY_ID));
		long walkObjectId = resultCursor.getLong(resultCursor.getColumnIndex(WalkObjectGeoObjectTable.KEY_WALK_OBJECT_ID));
		long geoObjectId = resultCursor.getLong(resultCursor.getColumnIndex(WalkObjectGeoObjectTable.KEY_GEO_OBJECT_ID));
		int sort = resultCursor.getInt(resultCursor.getColumnIndex(WalkObjectGeoObjectTable.KEY_SORT));
		
		return new WalkObjectGeoObject(id, walkObjectId, geoObjectId,  sort);
	}

	public Uri getFirstGeoObjectImageUri(long walkId) {
		
		Cursor resultCursor = getDB().query(
				true, 
				WalkObjectGeoObjectTable.TABLE_NAME, 
				WalkObjectGeoObjectTable.COLUMNS, 
				getWalkIdSelection(walkId), 
				null, 
				null,
				null,
				WalkObjectGeoObjectTable.KEY_SORT, 
				"1");
		
		if(resultCursor == null || !resultCursor.moveToFirst()){
			return null;
		}
		
		WalkObjectGeoObject object = getObjectFromCursor(resultCursor);
		long geoObjectId = object.getGeoObjectId();
		return new GeoObjectDBAdapter(context).getObjectItem(geoObjectId).getUri();
	}

	private String getWalkIdSelection(long walkId) {
		return WalkObjectGeoObjectTable.KEY_WALK_OBJECT_ID + " = " + walkId;
	}

	public Object[] getAllObjectsForWalkId(long walkId) {
		return getAllObjects(getDB().query(true, WalkObjectGeoObjectTable.TABLE_NAME, WalkObjectGeoObjectTable.COLUMNS, getWalkIdSelection(walkId), null, null, null, WalkObjectGeoObjectTable.KEY_SORT, null));
	}

	public void deleteAllObjectsForWalkId(long walkId) {
		getDB().delete(WalkObjectGeoObjectTable.TABLE_NAME, WalkObjectGeoObjectTable.KEY_WALK_OBJECT_ID + " = " + walkId, null);
		
	}

}
