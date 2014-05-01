package com.flaxtreme.gominsktestapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.flaxtreme.gominsktestapp.db.table.CategoryTable;
import com.flaxtreme.gominsktestapp.entity.CategoryObject;

public class CategoryDBAdapter extends AbstractDBAdapter<CategoryObject> {
	public CategoryDBAdapter(Context context) {
		super(context);
	}
	
	@Override
	public long insertObject(CategoryObject item) {
		if(item != null)
		{
			ContentValues newCategoryObjectRow = new ContentValues();
			
			newCategoryObjectRow.put(CategoryTable.KEY_ID, item.getId());
			
			newCategoryObjectRow.put(CategoryTable.KEY_NAME_BY, item.getName_by());
			newCategoryObjectRow.put(CategoryTable.KEY_NAME_EN, item.getName_en());
			newCategoryObjectRow.put(CategoryTable.KEY_NAME_RU, item.getName_ru());
			
			newCategoryObjectRow.put(CategoryTable.KEY_COLOR_STYLE_STRING, item.getStringColor());
			newCategoryObjectRow.put(CategoryTable.KEY_ICON_URI, item.getIconFileUriString());
			
			newCategoryObjectRow.put(CategoryTable.KEY_PARENT_ID, item.getParent_id());
			newCategoryObjectRow.put(CategoryTable.KEY_SORT, item.getSort());
					
			return getDB().insert(CategoryTable.TABLE_NAME, null, newCategoryObjectRow);
		}
		else
		{
			return -1;
		}
	}

	@Override
	public boolean removeObject(long objectID) {
		return getDB().delete(CategoryTable.TABLE_NAME, CategoryTable.KEY_ID+ "="+ objectID, null) > 0;
	}

	@Override
	public Cursor getAllObjectsCursor() {
		return getDB().query(CategoryTable.TABLE_NAME, CategoryTable.COLUMNS
				,null,null,null,null,null);
	}

	@Override
	public Cursor getCursorForObject(long objectID) {
		Cursor result = getDB().query(true,
				CategoryTable.TABLE_NAME, 
				CategoryTable.COLUMNS, 
				CategoryTable.KEY_ID + "=" + objectID,
				null, null, null, null, null);
		if((result.getCount() == 0) || !result.moveToFirst()){
			return null;
		}
		return result;
	}

	@Override
	public CategoryObject getObjectItem(long objectID) {
		Cursor resultCursor = getCursorForObject(objectID);
		if(resultCursor != null && resultCursor.moveToFirst()){
			return getObjectFromCursor(resultCursor);
		}
			return null;
	}

	public Object[] getChildCategories(long id) {
		String selection = CategoryTable.KEY_PARENT_ID + " = "+ id;
		Cursor result = getDB().query(true, CategoryTable.TABLE_NAME, CategoryTable.COLUMNS, selection, null, null, null, null, null);
		
		CategoryObject[] childCategories=null;
		if(result!=null && result.getCount()!=0 && result.moveToFirst())
		{
			return getAllObjects(result);
		}
		return childCategories;
		}

	@Override
	public CategoryObject getObjectFromCursor(Cursor resultCursor) {
			long id = resultCursor.getLong(resultCursor.getColumnIndex(CategoryTable.KEY_ID));
			
			String name_ru = resultCursor.getString(resultCursor.getColumnIndex(CategoryTable.KEY_NAME_RU));
			String name_by = resultCursor.getString(resultCursor.getColumnIndex(CategoryTable.KEY_NAME_BY));
			String name_en = resultCursor.getString(resultCursor.getColumnIndex(CategoryTable.KEY_NAME_EN));
			
			String uriString = resultCursor.getString(resultCursor.getColumnIndex(CategoryTable.KEY_ICON_URI));
			Uri iconUri = null;
			if(uriString!=null){
				 iconUri= Uri.parse(uriString);
			}
			String colorStirng = resultCursor.getString(resultCursor.getColumnIndex(CategoryTable.KEY_COLOR_STYLE_STRING));
			
			long parent_id = resultCursor.getLong(resultCursor.getColumnIndex(CategoryTable.KEY_PARENT_ID));
			int sort = resultCursor.getInt(resultCursor.getColumnIndex(CategoryTable.KEY_SORT));
			
			return new CategoryObject(id, name_ru, name_by,name_en,iconUri,colorStirng, parent_id, sort);
	}
}
