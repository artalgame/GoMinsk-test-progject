package com.flaxtreme.gominsktestapp.fragment;

import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.activity.GeoObjectDetailedActivity;
import com.flaxtreme.gominsktestapp.entity.CategoryObject;
import com.flaxtreme.gominsktestapp.entity.GeoObject;
import com.flaxtreme.gominsktestapp.interfaces.IMapDataChangesListener;
import com.flaxtreme.gominsktestapp.interfaces.IMapDataSource;
import com.flaxtreme.gominsktestapp.interfaces.IMapObject;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CategoryMapFragment extends BaseMapFragment implements IMapDataChangesListener {
	private IMapDataSource mapDataSource;
	private IMapObject[] currentGeoObjects;
	private CategoryObject currentCategory;

	private Marker[] markers;

	private Bitmap markerBitmap;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle arguments = getArguments();
		if(arguments!=null){
			currentCategory = arguments.getParcelable(GoMinskConstants.BUNDLE_CATEGORY_PARCELABLE);
		}
		setUpMarker();
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootLayout = super.onCreateView(inflater, container, savedInstanceState);
		return rootLayout;
	}
	
	@Override
	protected void checkClickedMarker(Marker marker) {
		for(int j=0; j<currentGeoObjects.length; j++){
			if(currentGeoObjects[j].getNameOnMarker().equals(marker.getTitle()))
			{
				Intent categoryIntent = new Intent(getActivity(), GeoObjectDetailedActivity.class);
				categoryIntent.putExtra(GoMinskConstants.INTENT_GEO_OBJECT_KEY, ((GeoObject)currentGeoObjects[j]));
				getActivity().startActivity(categoryIntent);
				return;
			}
		}
		super.checkClickedMarker(marker);
	};
	
	protected void setUpMarker() {
		markerBitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.marker);
		
		
		int[] pixels= new int[markerBitmap.getWidth()*markerBitmap.getHeight()];
		
		markerBitmap.getPixels(pixels,0,markerBitmap.getWidth(),0,0, markerBitmap.getWidth(), markerBitmap.getHeight());
		
		if(pixels!=null){
			for(int i=0; i<pixels.length; i++){
				int red = Color.red(pixels[i]);
				int alpha = Color.alpha(pixels[i]);
				if(red == 255 && alpha!=0)
					pixels[i] = currentCategory.getColor();
			}
			
			//markerBitmap.setPixels(pixels, 0, markerBitmap.getWidth(), 0, 0, markerBitmap.getWidth(), markerBitmap.getHeight())
			markerBitmap = Bitmap.createBitmap(pixels, markerBitmap.getWidth(), markerBitmap.getHeight(), Config.ARGB_8888);
			markerBitmap = Bitmap.createScaledBitmap(markerBitmap, markerBitmap.getWidth()/3, markerBitmap.getHeight()/3, true);
		}
	}

	@Override
	public long getCurrentCategoryId() {
		if(currentCategory!=null){
			return currentCategory.getId();
		}
		return super.getCurrentCategoryId();
	}
	
	
	public void setMapDataSource(IMapDataSource mapDataSource){
		this.mapDataSource = mapDataSource;
		updateCurrentGeoObjects();
	}

	private void updateCurrentGeoObjects() {
		currentGeoObjects = mapDataSource.getCurrentGeoObjects();
		redrawMapObjects();
	}

	private void redrawMapObjects() {
		if(map==null || currentGeoObjects==null) return;
		
		removeOldMarkers();
		markers = new Marker[currentGeoObjects.length];
		int i=0;
		for(IMapObject mapObject: currentGeoObjects){
			markers[i] = map.addMarker(new MarkerOptions()
				.icon(BitmapDescriptorFactory.fromBitmap(markerBitmap))
				.position(new LatLng(mapObject.getLatitude(), mapObject.getLongitude()))
				.title(mapObject.getNameOnMarker())
				.snippet(mapObject.getDestination()));
			i++;
		}
	}

	private void removeOldMarkers() {
		if(markers!=null){
			for(Marker marker:markers){
				marker.remove();
			}
		}
		
	}

	@Override
	public void mapDataChanged() {
		updateCurrentGeoObjects();
		
	}

}
