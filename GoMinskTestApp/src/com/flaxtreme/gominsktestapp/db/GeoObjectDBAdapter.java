package com.flaxtreme.gominsktestapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.flaxtreme.gominsktestapp.db.table.GeoObjectCategoryTable;
import com.flaxtreme.gominsktestapp.db.table.GeoObjectTable;
import com.flaxtreme.gominsktestapp.entity.GeoObject;

public class GeoObjectDBAdapter extends AbstractDBAdapter<GeoObject>{
	
	public GeoObjectDBAdapter(Context context) {
		super(context);
	}

	@Override
	public long insertObject(GeoObject item) {
		if(item != null)
		{
			ContentValues newGeoObjectRow = new ContentValues();
			
			newGeoObjectRow.put(GeoObjectTable.KEY_ID, item.getId());
			
			newGeoObjectRow.put(GeoObjectTable.KEY_NAME_BY, item.getName_by());
			newGeoObjectRow.put(GeoObjectTable.KEY_NAME_EN, item.getName_en());
			newGeoObjectRow.put(GeoObjectTable.KEY_NAME_RU, item.getName_ru());
			
			newGeoObjectRow.put(GeoObjectTable.KEY_ADDRESS_BY, item.getAddress_by());
			newGeoObjectRow.put(GeoObjectTable.KEY_ADDRESS_EN, item.getAddress_en());
			newGeoObjectRow.put(GeoObjectTable.KEY_ADDRESS_RU, item.getAddress_ru());

			newGeoObjectRow.put(GeoObjectTable.KEY_DESCRIPTION_BY, item.getDescription_by());
			newGeoObjectRow.put(GeoObjectTable.KEY_DESCRIPTION_EN, item.getDescription_en());
			newGeoObjectRow.put(GeoObjectTable.KEY_DESCRIPTION_RU, item.getDescription_ru());
			
			newGeoObjectRow.put(GeoObjectTable.KEY_LATITUDE, item.getLatitude());
			newGeoObjectRow.put(GeoObjectTable.KEY_LONGITUDE, item.getLongitude());
			
			newGeoObjectRow.put(GeoObjectTable.KEY_INFO, item.getInfo());
			newGeoObjectRow.put(GeoObjectTable.KEY_WORKTIME, item.getWorktime());
			newGeoObjectRow.put(GeoObjectTable.KEY_MARKER_ID, item.getMarkerId());
			
			newGeoObjectRow.put(GeoObjectTable.KEY_IMAGE_URI, item.getImageURIString());

			newGeoObjectRow.put(GeoObjectTable.KEY_IMAGE_INFO, item.getImageInfo());
			
			return getDB().insert(GeoObjectTable.TABLE_NAME, null, newGeoObjectRow);
		}
		else
		{
			return -1;
		}
	}

	@Override
	public boolean removeObject(long objectID) {
		return getDB().delete(GeoObjectTable.TABLE_NAME, GeoObjectTable.KEY_ID+ "="+ objectID, null) > 0;
	}

	@Override
	public Cursor getAllObjectsCursor() {
		return getDB().query(GeoObjectTable.TABLE_NAME, GeoObjectTable.COLUMNS
				,null,null,null,null,null);
	}
	
	public Cursor getObjectsCursorUsingWhereClause(String whereClause){
		return getDB().query(true,GeoObjectTable.TABLE_NAME, GeoObjectTable.COLUMNS, whereClause, null, null, null, null, null);
	}
	
	@Override
	public Cursor getCursorForObject(long objectID) {
		Cursor result = getDB().query(true,
				GeoObjectTable.TABLE_NAME, 
				GeoObjectTable.COLUMNS, 
				GeoObjectTable.KEY_ID + "=" + objectID,
				null, null, null, null, null);
		if((result.getCount() == 0) || !result.moveToFirst()){
			return null;
		}
		return result;
	}
	
	public GeoObject getObjectFromCursor(Cursor resultCursor){
		long id = resultCursor.getLong(resultCursor.getColumnIndex(GeoObjectTable.KEY_ID));
		
		String name_ru = resultCursor.getString(resultCursor.getColumnIndex(GeoObjectTable.KEY_NAME_RU));
		String name_en = resultCursor.getString(resultCursor.getColumnIndex(GeoObjectTable.KEY_NAME_EN));
		String name_by = resultCursor.getString(resultCursor.getColumnIndex(GeoObjectTable.KEY_NAME_BY));
		
		
		String address_ru = resultCursor.getString(resultCursor.getColumnIndex(GeoObjectTable.KEY_ADDRESS_RU));
		String address_by = resultCursor.getString(resultCursor.getColumnIndex(GeoObjectTable.KEY_ADDRESS_BY));
		String address_en = resultCursor.getString(resultCursor.getColumnIndex(GeoObjectTable.KEY_ADDRESS_EN));
		
		String description_ru = resultCursor.getString(resultCursor.getColumnIndex(GeoObjectTable.KEY_DESCRIPTION_RU));
		String description_en = resultCursor.getString(resultCursor.getColumnIndex(GeoObjectTable.KEY_DESCRIPTION_EN));
		String description_by = resultCursor.getString(resultCursor.getColumnIndex(GeoObjectTable.KEY_DESCRIPTION_BY));
		
		Double latitude = resultCursor.getDouble(resultCursor.getColumnIndex(GeoObjectTable.KEY_LATITUDE));
		Double longitude = resultCursor.getDouble(resultCursor.getColumnIndex(GeoObjectTable.KEY_LONGITUDE));
		
		String info = resultCursor.getString(resultCursor.getColumnIndex(GeoObjectTable.KEY_INFO));
		String worktime = resultCursor.getString(resultCursor.getColumnIndex(GeoObjectTable.KEY_WORKTIME));
		long markerId = resultCursor.getLong(resultCursor.getColumnIndex(GeoObjectTable.KEY_MARKER_ID));
		
		String imageUriString = resultCursor.getString(resultCursor.getColumnIndex(GeoObjectTable.KEY_IMAGE_URI));
		Uri imageUri = null;
		if(imageUriString!=null){
			imageUri= Uri.parse(imageUriString);
		}
		
		String imageInfo =  resultCursor.getString(resultCursor.getColumnIndex(GeoObjectTable.KEY_IMAGE_URI));
		
		return new GeoObject
			(
				id, 
				name_ru, 
				name_en, 
				name_by, 
				address_ru, 
				address_en, 
				address_by, 
				latitude, 
				longitude, 
				description_ru, 
				description_en, 
				description_by, 
				info, 
				worktime, 
				markerId, 
				imageInfo, 
				imageUri
			);
	}

	public Object[] getAllGeoObjectsForCategory(Long categoryId) {
		
		String queryRaw = 
				"SELECT " + GeoObjectTable.columnsToString() +" FROM " + 
			    GeoObjectTable.TABLE_NAME  +
				" INNER JOIN " + GeoObjectCategoryTable.TABLE_NAME + " ON " +
				GeoObjectCategoryTable.TABLE_NAME+"."+GeoObjectCategoryTable.KEY_GEO_OBJECT_ID + " = " + GeoObjectTable.TABLE_NAME+"."+GeoObjectTable.KEY_ID + 
				" WHERE "+GeoObjectCategoryTable.TABLE_NAME+"."+GeoObjectCategoryTable.KEY_CATEGORY_ID + " = " + categoryId; 
		return getAllObjects(getDB().rawQuery(queryRaw, null));
	}

}
