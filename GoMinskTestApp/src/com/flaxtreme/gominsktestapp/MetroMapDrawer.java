package com.flaxtreme.gominsktestapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;

import com.flaxtreme.gominsktestapp.activity.GeoObjectDetailedActivity;
import com.flaxtreme.gominsktestapp.db.CategoryDBAdapter;
import com.flaxtreme.gominsktestapp.db.GeoObjectDBAdapter;
import com.flaxtreme.gominsktestapp.entity.CategoryObject;
import com.flaxtreme.gominsktestapp.entity.GeoObject;
import com.flaxtreme.gominsktestapp.interfaces.IMapObject;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MetroMapDrawer extends CategoryMapDrawer {

	public CategoryObject redLineCategory;
	public CategoryObject blueLineCategory;
	public IMapObject[] redGeoObjects;
	public IMapObject[] blueGeoObjects;
	private Marker[] redMarkers;
	private Marker[] blueMarkers;
	private Polyline[] redPolyline;
	private Polyline[] bluePolyline;

	public MetroMapDrawer(Context context, long categoryId,
			View categoryButton, boolean isCurrentCategory, GoogleMap map) {
		super(context, categoryId, categoryButton, isCurrentCategory, map);
		setUpMarker();
	}
	
	
	@Override
	protected void setUpMarker() {
		markerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_metro);
		markerBitmap = Bitmap.createScaledBitmap(markerBitmap, markerBitmap.getWidth()/3, markerBitmap.getHeight()/3, true);
	}
	
	@Override
	public void drawOnMap() {
		
		redMarkers = new Marker[redGeoObjects.length];
		Marker lastMarker = null;
		redPolyline = new Polyline[redGeoObjects.length-1];
		bluePolyline = new Polyline[blueGeoObjects.length-1];
		
		for(int i=0; i<redGeoObjects.length; i++)
		{
			
			IMapObject geoObject = redGeoObjects[i];
			redMarkers[i] = map.addMarker(new MarkerOptions()
					.icon(BitmapDescriptorFactory.fromBitmap(markerBitmap))
					.title(geoObject.getNameOnMarker())
					.position(new LatLng(geoObject.getLatitude(), geoObject.getLongitude()))
					.snippet(geoObject.getDestination()));
			
			if(lastMarker!=null){
				redPolyline[i-1]=map.addPolyline(new PolylineOptions().color(Color.RED).width(10).add(lastMarker.getPosition()).add(redMarkers[i].getPosition()));
			}
			lastMarker = redMarkers[i];
		}
		
		lastMarker = null;
		blueMarkers = new Marker[blueGeoObjects.length];
		for(int i=0; i<blueGeoObjects.length; i++)
		{
			IMapObject geoObject = blueGeoObjects[i];
			blueMarkers[i] = map.addMarker(new MarkerOptions()
					.icon(BitmapDescriptorFactory.fromBitmap(markerBitmap))
					.title(geoObject.getNameOnMarker())
					.position(new LatLng(geoObject.getLatitude(), geoObject.getLongitude()))
					.snippet(geoObject.getDestination()));
			
			if(lastMarker!=null){
				bluePolyline[i-1]=map.addPolyline(new PolylineOptions().color(Color.BLUE).width(10).add(lastMarker.getPosition()).add(blueMarkers[i].getPosition()));
			}
			lastMarker = blueMarkers[i];
		}
	}
	
	@Override
	public boolean checkInfoWindowClicked(Marker marker) {
		for(int j=0; j<redGeoObjects.length; j++){
			if(redGeoObjects[j].getNameOnMarker().equals(marker.getTitle()))
			{
				Intent categoryIntent = new Intent(context, GeoObjectDetailedActivity.class);
				categoryIntent.putExtra(GoMinskConstants.INTENT_GEO_OBJECT_KEY, ((GeoObject)redGeoObjects[j]));
				context.startActivity(categoryIntent);
				return true;
			}
		}
		
		for(int j=0; j<blueGeoObjects.length; j++){
			if(blueGeoObjects[j].getNameOnMarker().equals(marker.getTitle()))
			{
				Intent categoryIntent = new Intent(context, GeoObjectDetailedActivity.class);
				categoryIntent.putExtra(GoMinskConstants.INTENT_GEO_OBJECT_KEY, ((GeoObject)blueGeoObjects[j]));
				context.startActivity(categoryIntent);
				return true;
			}
			
		}
		return false;
	}
	
	@Override
	public void removeMarkers() {
		if(blueMarkers!=null)
		for(Marker marker: blueMarkers){
			marker.remove();
		}
		
		if(redMarkers!=null)
		for(Marker marker: redMarkers){
			marker.remove();
		}
		
		if(redPolyline!=null)
			for(Polyline poly: redPolyline){
				poly.remove();
			}
		
		if(bluePolyline!=null)
			for(Polyline poly: bluePolyline){
				poly.remove();
			}
	};

	@Override
	protected void loadCategoryData() {
		new LoadMetroObjectsAsyncTask().execute(GoMinskConstants.METRO_MAIN_CATEGORY_ID);
	}
	
	
	class LoadMetroObjectsAsyncTask extends AsyncTask<Long, Void, Void>{
		
		protected Void doInBackground(Long... categoryId) {
			
			CategoryDBAdapter categoryDbAdapter = new CategoryDBAdapter(context);
			category = categoryDbAdapter.getObjectItem(GoMinskConstants.METRO_MAIN_CATEGORY_ID);
			
			redLineCategory = categoryDbAdapter.getObjectItem(GoMinskConstants.METRO_RED_LINE_CATEGORY_ID);
			blueLineCategory = categoryDbAdapter.getObjectItem(GoMinskConstants.METRO_BLUE_LINE_CATEGORY_ID);
			
					
			
			GeoObjectDBAdapter geoObjectDBAdapter = new GeoObjectDBAdapter(context);
			
			Object[] objects = geoObjectDBAdapter.getAllGeoObjectsForCategory(redLineCategory.getId());
			
			redGeoObjects = new IMapObject[objects.length];
			for(int i=0;i<objects.length; i++){
				redGeoObjects[i] = (IMapObject) objects[i];
			}
			
			objects = geoObjectDBAdapter.getAllGeoObjectsForCategory(blueLineCategory.getId());
			
			blueGeoObjects = new IMapObject[objects.length];
			for(int i=0;i<objects.length; i++){
				blueGeoObjects[i] = (IMapObject) objects[i];
			}
			
			return null;
			
		}
		
		protected void onPostExecute(Void result) {
			if(isCurrentCategory )
				drawOnMap();
			else{
				categoryButton.setEnabled(true);
			}
		}
	}
}
