package com.flaxtreme.gominsktestapp.interfaces;

import com.flaxtreme.gominsktestapp.entity.GeoObject;

public interface IFilteredObjectsChangesListener {
	public void notifyFilteredObjectsChanged(GeoObject[] objects);
}
