package com.flaxtreme.gominsktestapp;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;

import com.flaxtreme.gominsktestapp.activity.GeoObjectDetailedActivity;
import com.flaxtreme.gominsktestapp.db.CategoryDBAdapter;
import com.flaxtreme.gominsktestapp.db.GeoObjectDBAdapter;
import com.flaxtreme.gominsktestapp.entity.CategoryObject;
import com.flaxtreme.gominsktestapp.entity.GeoObject;
import com.flaxtreme.gominsktestapp.interfaces.IMapObject;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CategoryMapDrawer {

	public CategoryObject category;
	public IMapObject[] geoObjects;
	Context context;
	private long categoryId;
	protected Marker[] markers = null;
	View categoryButton;
	boolean isCurrentCategory;
	protected GoogleMap map;
	protected Bitmap markerBitmap;
	
	public CategoryMapDrawer(Context context, long categoryId, View categoryButton, boolean isCurrentCategory, GoogleMap map){
		this.context = context;
		this.categoryId = categoryId;
		this.categoryButton = categoryButton;
		this.isCurrentCategory = isCurrentCategory;
		this.map = map;
		
		setUpButton();
		
		loadCategoryData();
	}
	
	protected void setUpMarker() {
		markerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker);
		
		
		int[] pixels= new int[markerBitmap.getWidth()*markerBitmap.getHeight()];
		
		markerBitmap.getPixels(pixels,0,markerBitmap.getWidth(),0,0, markerBitmap.getWidth(), markerBitmap.getHeight());
		
		if(pixels!=null){
			for(int i=0; i<pixels.length; i++){
				int red = Color.red(pixels[i]);
				int alpha = Color.alpha(pixels[i]);
				if(red == 255 && alpha!=0)
					pixels[i] = category.getColor();
			}
			
			//markerBitmap.setPixels(pixels, 0, markerBitmap.getWidth(), 0, 0, markerBitmap.getWidth(), markerBitmap.getHeight())
			markerBitmap = Bitmap.createBitmap(pixels, markerBitmap.getWidth(), markerBitmap.getHeight(), Config.ARGB_8888);
			markerBitmap = Bitmap.createScaledBitmap(markerBitmap, markerBitmap.getWidth()/3, markerBitmap.getHeight()/3, true);
		}
	}

	protected void loadCategoryData() {
		if(!isCurrentCategory)
			new LoadCategoryAndMapObjectsAsyncTask().execute(categoryId);
		
	}

	private void setUpButton() {
		if(isCurrentCategory)
			categoryButton.setVisibility(View.GONE);
		else
		{
			categoryButton.setEnabled(false);
			categoryButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(!v.isSelected()){
						drawOnMap();
						if(category!=null){
							HashMap<String, String> categoryMap = new HashMap<String, String>();
							categoryMap.put(FlurryConstants.CATEGORY_ID, ((Long)category.getId()).toString());
							FlurryAgent.logEvent(FlurryConstants.BOTTOM_CATEGORY_SELECTED, categoryMap);
						}
					}
					else{
						removeMarkers();
					}
					v.setSelected(!v.isSelected());				
				}
			});
		}
	}

	class LoadCategoryAndMapObjectsAsyncTask extends AsyncTask<Long, Void, Void>{
		
		protected Void doInBackground(Long... categoryId) {
			
			CategoryDBAdapter categoryDbAdapter = new CategoryDBAdapter(context);
			category = categoryDbAdapter.getObjectItem(categoryId[0]);
			
			GeoObjectDBAdapter geoObjectDBAdapter = new GeoObjectDBAdapter(context);
			Object[] objects = geoObjectDBAdapter.getAllGeoObjectsForCategory(categoryId[0]);
			geoObjects = new IMapObject[objects.length];
			for(int i=0;i<objects.length; i++){
				geoObjects[i] = (IMapObject) objects[i];
			}
			
			return null;
			
		}
		
		protected void onPostExecute(Void result) {
			categoryButton.setEnabled(true);			
			setUpMarker();
		}
	}
	
	public void drawOnMap(){
		markers = new Marker[geoObjects.length];
		
		for(int i=0; i<geoObjects.length; i++)
		{
			IMapObject geoObject = geoObjects[i];
			markers[i] = map.addMarker(new MarkerOptions()
					.icon(BitmapDescriptorFactory.fromBitmap(markerBitmap))
					.title(geoObject.getNameOnMarker())
					.position(new LatLng(geoObject.getLatitude(), geoObject.getLongitude()))
					.snippet(geoObject.getDestination()));
		}
	}
	
	public void removeMarkers(){
		if(markers!=null)
		{
			for(Marker marker:markers){
				marker.remove();
			}
		}
	}

	public boolean checkInfoWindowClicked(Marker marker) {
		if(isCurrentCategory) return false;
		
		if(geoObjects!=null){
			for(int j=0; j<geoObjects.length; j++){
				if(geoObjects[j].getNameOnMarker().equals(marker.getTitle()))
				{
					Intent categoryIntent = new Intent(context, GeoObjectDetailedActivity.class);
					categoryIntent.putExtra(GoMinskConstants.INTENT_GEO_OBJECT_KEY, ((GeoObject)geoObjects[j]));
					context.startActivity(categoryIntent);
					return true;
				}
			}
			
		}
		return false;
	}
}
