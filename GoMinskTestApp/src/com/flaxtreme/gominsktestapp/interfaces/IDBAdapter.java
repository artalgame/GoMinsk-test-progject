/**
 * 
 */
package com.flaxtreme.gominsktestapp.interfaces;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Alexander Korolchuk
 *
 */
public interface IDBAdapter<D> {
	public void close();
	
	public SQLiteDatabase getDB();
	
	public long insertObject(D item);
	
	public boolean removeObject (long objectID);
	
	public Cursor getAllObjectsCursor();
	
	public Cursor getCursorForObject(long objectID);
	
	public D getObjectItem(long objectID);

	public Object[] getAllObjects();
	
	public D getObjectFromCursor(Cursor resultCursor);
	
	public Object[] getAllObjects(Cursor resultCursor);
}
