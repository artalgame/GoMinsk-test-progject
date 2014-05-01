package com.flaxtreme.gominsktestapp.entity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.flaxtreme.gominsktestapp.LocaleHelper;
import com.flaxtreme.gominsktestapp.interfaces.IWalkListViewItem;

public class WalkObject implements IWalkListViewItem {

	private long id;
	
	private String name_ru;
	private String name_by;
	private String name_en;
	
	private String description_ru;
	private String description_by;
	private String description_en;
	
	private String color_style_string;
	private int sort;
	
	private int timeToWalkInMinutes;
	
	private int distanceWalkMetres;
	
    private boolean isBitmapLoaded = false;

	private Bitmap bitmap;

	private Uri imageUri;
	

	public WalkObject(long id, String name_ru, String name_by,
			String name_en, String description_ru, String description_by,
			String description_en, String colorStirng, int sort, int time, int distance) {
		
		this.id = id;
		
		this.name_by = name_by;
		this.name_ru = name_ru;
		this.name_en = name_en;
		
		this.description_by = description_by;
		this.description_en = description_en;
		this.description_ru = description_ru;
		
		this.color_style_string = colorStirng;
		this.timeToWalkInMinutes = time;
		this.sort = sort;
		this.distanceWalkMetres = distance;
		
	}

	public WalkObject(Parcel in) {
		
		this.id = in.readLong();
		
		this.name_by = in.readString();
		this.name_ru = in.readString();
		this.name_en = in.readString();
		
		this.description_by = in.readString();
		this.description_ru = in.readString();
		this.description_en = in.readString();
		
		this.timeToWalkInMinutes = in.readInt();
		
		this.color_style_string = in.readString();
		this.sort = in.readInt();
		this.distanceWalkMetres = in.readInt();
	}


	@Override
	public String getTitle() {
		return LocaleHelper.getLocalizeObjectField(name_ru, name_by, name_en);
	}

	@Override
	public int getPlaningTime() {
		return timeToWalkInMinutes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription_ru() {
		return LocaleHelper.getLocalizeObjectField(description_ru, description_by, description_en);
	}

	public void setDescription_ru(String description_ru) {
		this.description_ru = description_ru;
	}

	public String getDescription_by() {
		return description_by;
	}

	public void setDescription_by(String description_by) {
		this.description_by = description_by;
	}

	public String getDescription_en() {
		return description_en;
	}

	public void setDescription_en(String description_en) {
		this.description_en = description_en;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getName_by() {
		return name_by;
	}

	public void setName_by(String name_by) {
		this.name_by = name_by;
	}
	
	public String getName_ru() {
		return name_ru;
	}

	public void setName_ru(String name_ru) {
		this.name_ru = name_ru;
	}

	public String getColor_style_string() {
		return color_style_string;
	}

	public void setColor_style_string(String color_style_string) {
		this.color_style_string = color_style_string;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Override
	public boolean hasLoadedBitmap() {
		return isBitmapLoaded ;
	}

	@Override
	public Bitmap getBitmap() {
		return bitmap;
	}

	@Override
	public void setImageBitmap(Bitmap result) {
		this.bitmap = result;
		isBitmapLoaded = true;
		
	}

	@Override
	public int describeContents() {
		return (int)getId();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		
		dest.writeString(name_by);
		dest.writeString(name_ru);
		dest.writeString(name_en);
		
		dest.writeString(description_by);
		dest.writeString(description_ru);
		dest.writeString(description_en);
		
		dest.writeInt(timeToWalkInMinutes);
		dest.writeString(color_style_string);
		dest.writeInt(sort);
		dest.writeInt(distanceWalkMetres);
	}
	
	public static final Parcelable.Creator<WalkObject> CREATOR = new Parcelable.Creator<WalkObject>() {
		  public WalkObject createFromParcel(Parcel in) {
			  return new WalkObject(in);
		  }

		  public WalkObject[] newArray(int size) {
			  return new WalkObject[size];
		  }
	  };


	@Override
	public Uri getImageUri() {
		return imageUri;
	}

	@Override
	public void setImageUri(Uri imageUri) {
		this.imageUri =imageUri; 
		
	}

	public CharSequence getDescription() {
		return LocaleHelper.getLocalizeObjectField(description_ru, description_by, description_en);
	}

	public int getDistanceWalkMetres() {
		return distanceWalkMetres;
	}

	public void setDistanceWalkMetres(int distanceWalkMetres) {
		this.distanceWalkMetres = distanceWalkMetres;
	}
}
