package com.flaxtreme.gominsktestapp.entity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.flaxtreme.gominsktestapp.LocaleHelper;
import com.flaxtreme.gominsktestapp.interfaces.ICategory;

public class CategoryObject implements ICategory, Parcelable {
	
	private long id;
	
	private String name_ru;
	private String name_by;
	private String name_en;
	
	private Uri iconFileUri;
	private String colorStyleString;
	
	private long parent_id;
	private int sort;
	
	
	public CategoryObject(long id, String name_ru, String name_by, String name_en, Uri iconFileUri, String colorStyleString, long parent_id, int sort){
		
		this.id = id;
		this.name_ru = name_ru;
		this.setName_en(name_en);
		this.setName_by(name_by);
		
		this.setIconFileUri(iconFileUri);
		this.colorStyleString = colorStyleString;
		this.setParent_id(parent_id);
		this.setSort(sort);
	}
	
	public CategoryObject(Parcel in) {
		this.id = in.readLong();
		this.name_ru = in.readString();
		this.name_by = in.readString();
		this.name_en = in.readString();
		String iconUriString = in.readString();
		
		try{
			this.iconFileUri = Uri.parse(iconUriString);
		}catch(Exception ex){
			this.iconFileUri = null;
		}
		this.colorStyleString = in.readString();
		this.parent_id = in.readLong();
		this.sort = in.readInt();
	}

	@Override
	public String getTitle() {
		return LocaleHelper.getLocalizeObjectField(name_ru, name_by, name_en);
	}

	@Override
	public int getColor() {
		try
		{
			return Color.parseColor(colorStyleString.toUpperCase());
		}catch(Exception ex){
			return Color.WHITE;
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getName_ru() {
		return name_ru;
	}
	
	public void setName_ru(String name_ru) {
		this.name_ru = name_ru;
	}

	public String getName_by() {
		return name_by;
	}

	public void setName_by(String name_by) {
		this.name_by = name_by;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}



	public Uri getIconFileUri() {
		return iconFileUri;
	}
	
	public String getIconFileUriString(){
		try{
			return iconFileUri.toString();
		}catch(Exception ex){
			return null;
		}
	}

	public void setIconFileUri(Uri iconFileUri) {
		this.iconFileUri = iconFileUri;
	}

	public long getParent_id() {
		return parent_id;
	}

	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return (int)getId();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(name_ru);
		dest.writeString(name_by);
		dest.writeString(name_en);
		dest.writeString(getIconFileUriString());
		dest.writeString(colorStyleString);
		dest.writeLong(parent_id);
		dest.writeInt(sort);
		
	}
	
	  public static final Parcelable.Creator<CategoryObject> CREATOR = new Parcelable.Creator<CategoryObject>() {
		  public CategoryObject createFromParcel(Parcel in) {
			  return new CategoryObject(in);
		  }
 
		  public CategoryObject[] newArray(int size) {
			  return new CategoryObject[size];
		  }
	  };


	public String getStringColor() {
		return colorStyleString;
	}
	
	public void setStringColor(String stringColor){
		colorStyleString = stringColor;
	}



}
