package com.flaxtreme.gominsktestapp.entity;

import java.io.IOException;
import java.io.InputStream;

import com.flaxtreme.gominsktestapp.LocaleHelper;
import com.flaxtreme.gominsktestapp.LocationHelper;
import com.flaxtreme.gominsktestapp.interfaces.IGeoObjectListViewItem;
import com.flaxtreme.gominsktestapp.interfaces.IMapObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class GeoObject implements IGeoObjectListViewItem, IMapObject, Parcelable {	
	
   private Bitmap listViewItemBitmap;

	private String name_ru;

	private String name_en;

	private String name_by;

	private String address_ru;

	private String address_by;

	private String address_en;

	private String description_ru;

	private String description_en;

	private String description_by;

	private String info;

	private String worktime;

	private long markerId;

	private long id;

	private double longitude;

	private double latitude;

	private String imageInfo;

	private Uri imageUri;

	private boolean isListViewBitmapLoaded;
	
	public GeoObject(long id, String name_ru, String name_en, String name_by,
			String address_ru, String address_en, String address_by,
			double latitude, double longitude, String description_ru,
			String description_en, String description_by, String info,
			String worktime, long markerId, String imageInfo, Uri imageUri) {

		this.id = id;
		
		this.name_ru = name_ru;
		this.setName_en(name_en);
		this.setName_by(name_by);
		
		this.address_ru = address_ru;
		this.setAddress_by(address_by);
		this.setAddress_en(address_en);
		
		this.longitude = longitude;
		this.latitude = latitude;
		
		this.description_ru = description_ru;
		this.setDescription_en(description_en);
		this.setDescription_by(description_by);
		
		this.setInfo(info);
		this.setWorktime(worktime);
		
		this.setMarkerId(markerId);
		
		this.setImageInfo(imageInfo);
		this.imageUri = imageUri;
	}

	public GeoObject(Parcel in) {
		
		this.id = in.readLong();
		
		this.name_ru = in.readString();
		this.name_by = in.readString();
		this.name_en = in.readString();
		
		this.address_ru = in.readString();
		this.address_by = in.readString();
		this.address_en = in.readString();
		
		this.description_ru = in.readString();
		this.description_by = in.readString();
		this.description_en = in.readString();
		
		this.longitude = in.readDouble();
		this.latitude = in.readDouble();
		
		this.info = in.readString();
		this.worktime = in.readString();
		this.markerId = in.readLong();
	
		setImageUriFromString(in.readString());
		this.imageInfo = in.readString();
	}

	public long getId()
	{
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public String getName(){
		return LocaleHelper.getLocalizeObjectField(name_ru, name_by, name_en);
	}
	
	public String getDescription(){
		return LocaleHelper.getLocalizeObjectField(description_ru, description_by, description_en);
	}
	
	public double getLatitude(){
		return latitude;
	}
	
	public void setLatitude(double latitude){
		this.latitude = latitude;
	}
	
	public double getLongitude(){
		return longitude;
	}
	
	public void setLongitude(double longitude){
		this.longitude = longitude;
	}

	public String getAddress() {
		return LocaleHelper.getLocalizeObjectField(address_ru, address_by, address_en);
	}

	@Override
	public String getNameOnMarker() {
		return getName();
	}

	@Override
	public String getDescriptionOnMarker() {
		return getDescription();
	}

	@Override
	public String getTitleInListView() {
		return getName();
	}

	@Override
	public String getDestinationInListView() {		
		return LocaleHelper.getLocalizeDestination(LocationHelper.getDestinationInMetersFromCurrentUserPosition(getLatitude(), getLongitude()));
	}

	@Override
	public Uri getImageURI() {
		return imageUri;
	}
	
	public Uri getUri(){
		return imageUri;
	}
	
	public void setUri(Uri uri)
	{
		imageUri = uri;
	}
	
	public void setImageUriFromString(String uriString){
		try{
			imageUri = Uri.parse(uriString);
		}catch(Exception ex){
			imageUri = null;
		}
	}

	@Override
	public Bitmap getBitmap() {
		return listViewItemBitmap;
	}

	public static Bitmap loadListViewItemBitmap(Context context, Uri imageUri) {
		try {
			InputStream is = context.getContentResolver().openInputStream(imageUri);
			Bitmap listViewItemBitmap = BitmapFactory.decodeStream(is);
			is.close();
			return listViewItemBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public void setBitmap(Bitmap bitmap) {
		listViewItemBitmap = bitmap;
		isListViewBitmapLoaded=true;
		
	}

	public String getName_ru() {
		return name_ru;
	}

	public void setName_ru(String name_ru) {
		this.name_ru = name_ru;
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
	
	public String getAddress_ru() {
		return address_ru;
	}

	public void setAddress_ru(String address_ru) {
		this.address_ru = address_ru;
	}


	public String getAddress_by() {
		return address_by;
	}

	public void setAddress_by(String address_by) {
		this.address_by = address_by;
	}

	public String getAddress_en() {
		return address_en;
	}

	public void setAddress_en(String address_en) {
		this.address_en = address_en;
	}

	public String getDescription_ru() {
		return description_ru;
	}

	public void setDescription_ru(String description_ru) {
		this.description_ru = description_ru;
	}

	
	public String getDescription_en() {
		return description_en;
	}

	public void setDescription_en(String description_en) {
		this.description_en = description_en;
	}

	public String getDescription_by() {
		return description_by;
	}

	public void setDescription_by(String description_by) {
		this.description_by = description_by;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getWorktime() {
		return worktime;
	}

	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}

	public long getMarkerId() {
		return markerId;
	}

	public void setMarkerId(long markerId) {
		this.markerId = markerId;
	}

	public String getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(String imageInfo) {
		this.imageInfo = imageInfo;
	}

	public String getImageURIString() {
		try{
			return imageUri.toString();
		}catch(Exception ex){
			return null;
		}
	}

	@Override
	public int describeContents() {
		return (int)getId();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		
		dest.writeString(name_ru);
		dest.writeString(name_by);
		dest.writeString(name_en);
		
		dest.writeString(address_ru);
		dest.writeString(address_by);
		dest.writeString(address_en);
		
		dest.writeString(description_ru);
		dest.writeString(description_by);
		dest.writeString(description_en);
		
		dest.writeDouble(longitude);
		dest.writeDouble(latitude);
		
		dest.writeString(info);
		dest.writeString(worktime);
		dest.writeLong(markerId);
	
		
		dest.writeString(getImageURIString());
		dest.writeString(imageInfo);
	}
	
	public static final Parcelable.Creator<GeoObject> CREATOR = new Parcelable.Creator<GeoObject>() {
		  public GeoObject createFromParcel(Parcel in) {
			  return new GeoObject(in);
		  }

		  public GeoObject[] newArray(int size) {
			  return new GeoObject[size];
		  }
	  };

	@Override
	public boolean hasBitmap() {
		return isListViewBitmapLoaded;
	}

	@Override
	public String getDestination() {
		return getDestinationInListView();
	}
	
}
