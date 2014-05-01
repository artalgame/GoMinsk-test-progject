package com.flaxtreme.gominsktestapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.flaxtreme.gominsktestapp.db.table.GeoObjectCategoryTable;
import com.flaxtreme.gominsktestapp.entity.GeoObjectCategory;

public class GeoObjectCategoryDBAdapter extends AbstractDBAdapter<GeoObjectCategory> {
	
	public GeoObjectCategoryDBAdapter(Context context){
		super(context);
	}
	
	@Override
	public long insertObject(GeoObjectCategory item) {
		if(item != null)
		{
			ContentValues newGeoObjectCategoryRow = new ContentValues();
			
			newGeoObjectCategoryRow.put(GeoObjectCategoryTable.KEY_GEO_OBJECT_ID, item.getGeoObjectId());
			newGeoObjectCategoryRow.put(GeoObjectCategoryTable.KEY_CATEGORY_ID, item.getCategoryId());
			newGeoObjectCategoryRow.put(GeoObjectCategoryTable.KEY_SORT, item.getSort());
			
			return getDB().insert(GeoObjectCategoryTable.TABLE_NAME, null, newGeoObjectCategoryRow);
		}
		else
		{
			return -1;
		}
	}

	@Override
	public boolean removeObject(long objectID) {
		return getDB().delete(GeoObjectCategoryTable.TABLE_NAME, GeoObjectCategoryTable.KEY_ID+ "="+ objectID, null) > 0;
	}

	@Override
	public Cursor getAllObjectsCursor() {
		return getDB().query(GeoObjectCategoryTable.TABLE_NAME, GeoObjectCategoryTable.COLUMNS
				,null,null,null,null,null);
	}

	@Override
	public Cursor getCursorForObject(long objectID) {
		Cursor result = getDB().query(true,
				GeoObjectCategoryTable.TABLE_NAME, 
				GeoObjectCategoryTable.COLUMNS, 
				GeoObjectCategoryTable.KEY_ID + "=" + objectID,
				null, null, null, null, null);
		if((result.getCount() == 0) || !result.moveToFirst()){
			return null;
		}
		return result;
	}

	public long[] getGeoObjectIdsForCategory(long id) {
		Cursor idsCursor = getIDsCursorForSelection(getCategoryEqualSelection(id));
		if(idsCursor != null && idsCursor.moveToFirst()){
			long[] ids = new long[idsCursor.getCount()];
			int i = 0;
			do{
				ids[i] = idsCursor.getLong(0);
				i++;
			}while(idsCursor.moveToNext());
			
			return ids;
		}
		return new long[0];
	}

	private Cursor getIDsCursorForSelection(String categoryEqualSelection) {
		return getDB().query(true, GeoObjectCategoryTable.TABLE_NAME, new String[]{GeoObjectCategoryTable.KEY_GEO_OBJECT_ID}, categoryEqualSelection, null, null, null, GeoObjectCategoryTable.KEY_SORT, null);
	}

	private String getCategoryEqualSelection(long id) {
		return GeoObjectCategoryTable.KEY_CATEGORY_ID + " = " + id;
	}

	@Override
	public GeoObjectCategory getObjectFromCursor(Cursor resultCursor) {
		long id = resultCursor.getLong(resultCursor.getColumnIndex(GeoObjectCategoryTable.KEY_ID));
		long categoryId = resultCursor.getLong(resultCursor.getColumnIndex(GeoObjectCategoryTable.KEY_CATEGORY_ID));
		long geoObjectId = resultCursor.getLong(resultCursor.getColumnIndex(GeoObjectCategoryTable.KEY_GEO_OBJECT_ID));
		int sort = resultCursor.getInt(resultCursor.getColumnIndex(GeoObjectCategoryTable.KEY_SORT));
		
		return new GeoObjectCategory(id, geoObjectId, categoryId, sort);
	}

	public void removeAllObjectsForCategoryId(long categoryId) {
		getDB().delete(GeoObjectCategoryTable.TABLE_NAME, GeoObjectCategoryTable.KEY_CATEGORY_ID + " = " + categoryId, null);
	}
}
