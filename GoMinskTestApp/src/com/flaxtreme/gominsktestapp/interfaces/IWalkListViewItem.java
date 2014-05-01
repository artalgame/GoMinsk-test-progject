package com.flaxtreme.gominsktestapp.interfaces;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;

public interface IWalkListViewItem extends Parcelable{
	public Uri getImageUri();
	public String getTitle();
	public int getPlaningTime();
	public long getId();
	public boolean hasLoadedBitmap();
	public Bitmap getBitmap();
	public void setImageBitmap(Bitmap result);
	public void setImageUri(Uri imageUri);
}
