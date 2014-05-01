package com.flaxtreme.gominsktestapp.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.flaxtreme.gominsktestapp.GoMinskDBAdapter;
import com.flaxtreme.gominsktestapp.interfaces.IDBAdapter;

public abstract class AbstractDBAdapter<D> implements IDBAdapter<D> {

	protected GoMinskDBAdapter dbAdapter;
	protected Context context;
	
    public AbstractDBAdapter(Context context){
    	this.context = context;
    	dbAdapter = new GoMinskDBAdapter(context);
		dbAdapter.open();
    }
	
	@Override
	public void close() {
		dbAdapter.close();
	}

	@Override
	public SQLiteDatabase getDB() {
		return dbAdapter.getDB();
	}

	@Override
	public D getObjectItem(long objectID) {
		Cursor resultCursor = getCursorForObject(objectID);
		if(resultCursor != null && resultCursor.moveToFirst()){
			return getObjectFromCursor(resultCursor);
			
		}else{
			return null;
		}
	}
	
	@Override
	public Object[] getAllObjects() {
		Cursor resultCursor = getAllObjectsCursor();
		return getAllObjects(resultCursor);
	}
	
	@Override
	public Object[] getAllObjects(Cursor resultCursor) {
		if(resultCursor == null || !resultCursor.moveToNext()){
			return null;
		}
			ArrayList<D> objects = new ArrayList<D>();
			do{
				 objects.add(getObjectFromCursor(resultCursor));
			}while(resultCursor.moveToNext());
			
			return objects.toArray();
	};
	
	@Override
	public abstract long insertObject(D item);

	@Override
	public abstract boolean removeObject(long objectID);

	@Override
	public abstract Cursor getAllObjectsCursor();

	@Override
	public abstract Cursor getCursorForObject(long objectID);
	
	@Override
	public abstract D getObjectFromCursor(Cursor resultCursor);
}
