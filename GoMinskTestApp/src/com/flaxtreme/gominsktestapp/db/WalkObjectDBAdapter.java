package com.flaxtreme.gominsktestapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.flaxtreme.gominsktestapp.db.table.WalkObjectTable;
import com.flaxtreme.gominsktestapp.entity.WalkObject;

public class WalkObjectDBAdapter extends AbstractDBAdapter<WalkObject> {
	
	public WalkObjectDBAdapter(Context context) {
		super(context);
	}

	
	@Override
	public long insertObject(WalkObject item) {
		if(item != null)
		{
			ContentValues newWalkObjectRow = new ContentValues();
			
			newWalkObjectRow.put(WalkObjectTable.KEY_ID, item.getId());
			
			newWalkObjectRow.put(WalkObjectTable.KEY_NAME_BY, item.getName_by());
			newWalkObjectRow.put(WalkObjectTable.KEY_NAME_EN, item.getName_en());
			newWalkObjectRow.put(WalkObjectTable.KEY_NAME_RU, item.getName_ru());
			
			newWalkObjectRow.put(WalkObjectTable.KEY_DESCRIPTION_BY, item.getDescription_by());
			newWalkObjectRow.put(WalkObjectTable.KEY_DESCRIPTION_RU, item.getDescription_ru());
			newWalkObjectRow.put(WalkObjectTable.KEY_DESCRIPTION_EN, item.getDescription_en());
			
			newWalkObjectRow.put(WalkObjectTable.KEY_COLOR_STYLE_STRING, item.getColor_style_string());
			newWalkObjectRow.put(WalkObjectTable.KEY_SORT, item.getSort());
			newWalkObjectRow.put(WalkObjectTable.KEY_TIME, item.getPlaningTime());
			newWalkObjectRow.put(WalkObjectTable.KEY_DISTANCE, item.getDistanceWalkMetres());
			
			return getDB().insert(WalkObjectTable.TABLE_NAME, null, newWalkObjectRow);
		}else{
			return -1;
		}
	}

	@Override
	public Cursor getAllObjectsCursor() {
		return getDB().query(WalkObjectTable.TABLE_NAME, WalkObjectTable.COLUMNS
				,null,null,null,null,null);
	}
	
	@Override
	public boolean removeObject(long objectID) {
		return getDB().delete(WalkObjectTable.TABLE_NAME, WalkObjectTable.KEY_ID+ "="+ objectID, null) > 0;
	}

	public WalkObject getObjectFromCursor(Cursor resultCursor){
		long id = resultCursor.getLong(resultCursor.getColumnIndex(WalkObjectTable.KEY_ID));
		
		String name_ru = resultCursor.getString(resultCursor.getColumnIndex(WalkObjectTable.KEY_NAME_RU));
		String name_by = resultCursor.getString(resultCursor.getColumnIndex(WalkObjectTable.KEY_NAME_BY));
		String name_en = resultCursor.getString(resultCursor.getColumnIndex(WalkObjectTable.KEY_NAME_EN));
		
		String description_ru = resultCursor.getString(resultCursor.getColumnIndex(WalkObjectTable.KEY_DESCRIPTION_RU));
		String description_by = resultCursor.getString(resultCursor.getColumnIndex(WalkObjectTable.KEY_DESCRIPTION_BY));
		String description_en = resultCursor.getString(resultCursor.getColumnIndex(WalkObjectTable.KEY_DESCRIPTION_EN));
		
		String colorStirng = resultCursor.getString(resultCursor.getColumnIndex(WalkObjectTable.KEY_COLOR_STYLE_STRING));
		int sort = resultCursor.getInt(resultCursor.getColumnIndex(WalkObjectTable.KEY_SORT));
		int time = resultCursor.getInt(resultCursor.getColumnIndex(WalkObjectTable.KEY_TIME));
		int distance = resultCursor.getInt(resultCursor.getColumnIndex(WalkObjectTable.KEY_DISTANCE));
		
		return new WalkObject(id, name_ru, name_by,name_en,description_ru, description_by, description_en,colorStirng, sort, time, distance);
	}
	
	@Override
	public Cursor getCursorForObject(long objectID) {
		Cursor result = getDB().query(true,
				WalkObjectTable.TABLE_NAME, 
				WalkObjectTable.COLUMNS, 
				WalkObjectTable.KEY_ID + "=" + objectID,
				null, null, null, null, null);
		if((result.getCount() == 0) || !result.moveToFirst()){
			return null;
		}
		return result;
	}
}
