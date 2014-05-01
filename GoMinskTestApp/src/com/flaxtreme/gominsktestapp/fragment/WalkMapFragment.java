package com.flaxtreme.gominsktestapp.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.activity.GeoObjectDetailedActivity;
import com.flaxtreme.gominsktestapp.entity.GeoObject;
import com.flaxtreme.gominsktestapp.interfaces.IMapObject;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class WalkMapFragment extends BaseMapFragment {

	private IMapObject[] objects;
	private Marker[] markers;
	private Bitmap markerBitmap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setUpMarker();
	}
	
	public void setObjects(IMapObject[] objects){
		this.objects = objects;
		redrawMap();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view =  super.onCreateView(inflater, container, savedInstanceState);
		
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {	
			@Override
			public void onInfoWindowClick(Marker marker) {
				for(int j=0; j<objects.length; j++){
					if(objects[j].getNameOnMarker().equals(marker.getTitle()))
					{
						Intent categoryIntent = new Intent(getActivity(), GeoObjectDetailedActivity.class);
						categoryIntent.putExtra(GoMinskConstants.INTENT_GEO_OBJECT_KEY, ((GeoObject)objects[j]));
						getActivity().startActivity(categoryIntent);
						return;
					}
				}
				checkClickedMarker(marker);
			}
		});
		return view;
	}
	
	protected void setUpMarker() {
		markerBitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.marker_green);
		/*int[] pixels= new int[markerBitmap.getWidth()*markerBitmap.getHeight()];
		
		markerBitmap.getPixels(pixels,0,markerBitmap.getWidth(),0,0, markerBitmap.getWidth(), markerBitmap.getHeight());
		
		if(pixels!=null){
			for(int i=0; i<pixels.length; i++){
				int red = Color.red(pixels[i]);
				int alpha = Color.alpha(pixels[i]);
				if(red == 255 && alpha!=0)
					pixels[i] = Color.parseColor("#279919");
			}
			
			//markerBitmap.setPixels(pixels, 0, markerBitmap.getWidth(), 0, 0, markerBitmap.getWidth(), markerBitmap.getHeight())
			markerBitmap = Bitmap.createBitmap(pixels, markerBitmap.getWidth(), markerBitmap.getHeight(), Config.ARGB_8888);
			markerBitmap = Bitmap.createScaledBitmap(markerBitmap, markerBitmap.getWidth()/3, markerBitmap.getHeight()/3, true);
		}*/
		markerBitmap = Bitmap.createScaledBitmap(markerBitmap, markerBitmap.getWidth()/3, markerBitmap.getHeight()/3, true);
	}

	private void redrawMap() {
		int i=0;
		removeOldMarkers();
		markers = new Marker[objects.length];
		Marker lastMarker = null;
		for(IMapObject mapObject: objects){
			Marker currentMarker = map.addMarker(new MarkerOptions()
			.icon(BitmapDescriptorFactory.fromBitmap(markerBitmap))
			.position(new LatLng(mapObject.getLatitude(), mapObject.getLongitude()))
			.title(mapObject.getNameOnMarker())
			.snippet(mapObject.getDestination()));
			
			markers[i] = currentMarker;
		
		
			if(lastMarker!=null){
				map.addPolyline(new PolylineOptions()
				.add(lastMarker.getPosition())
				.add(currentMarker.getPosition())
				.color(Color.parseColor("#279919")).width(5));
			}
			lastMarker = currentMarker;
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

}
