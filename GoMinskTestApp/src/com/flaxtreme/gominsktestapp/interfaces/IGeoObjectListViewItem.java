package com.flaxtreme.gominsktestapp.interfaces;

import android.graphics.Bitmap;
import android.net.Uri;

public interface IGeoObjectListViewItem {
	
	public String getTitleInListView();
	
	public Uri getImageURI();
	
	public void setBitmap(Bitmap bitmap);

	public boolean hasBitmap();

	Bitmap getBitmap();

	String getDestinationInListView();
}
