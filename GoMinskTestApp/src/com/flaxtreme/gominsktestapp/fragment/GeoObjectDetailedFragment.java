package com.flaxtreme.gominsktestapp.fragment;

import com.flaxtreme.gominsktestapp.GoMinskApplicationClass;
import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.LocationHelper;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.ViewHelper;
import com.flaxtreme.gominsktestapp.entity.GeoObject;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.ComponentName;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GeoObjectDetailedFragment extends Fragment implements View.OnClickListener {
	
	private GeoObject currentGeoObject;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		currentGeoObject = getArguments().getParcelable(GoMinskConstants.BUNDLE_GEO_OBJECT_PARCELABLE);
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootLayout = inflater.inflate(R.layout.fragment_detailed_place, container, false);
		
		TextView objectName = (TextView)rootLayout.findViewById(R.id.objectNameTextView);
		objectName.setText(currentGeoObject.getName());
		
		TextView objectDescription = (TextView)rootLayout.findViewById(R.id.objectDescriptionTextView);
		objectDescription.setText(currentGeoObject.getDescription());
		ViewHelper.makeTextViewResizable(objectDescription, 3, getActivity().getString(R.string.view_more), true, getActivity());
		
		TextView objectPhone = (TextView)rootLayout.findViewById(R.id.telephoneTextView);
		objectPhone.setText(currentGeoObject.getInfo());
		
		TextView objectOpenHour = (TextView)rootLayout.findViewById(R.id.openHoursTextView);
		objectOpenHour.setText(currentGeoObject.getWorktime());
		
		TextView objectAddress = (TextView)rootLayout.findViewById(R.id.addressTextView);
		objectAddress.setText(currentGeoObject.getAddress());
		
		ImageView objectImageView = (ImageView)rootLayout.findViewById(R.id.objectImageView);
		
		if(currentGeoObject.getImageURI()!=null){
			String fullImageUri = currentGeoObject.getImageURI().toString();
			
			fullImageUri = (String) fullImageUri.subSequence(0, fullImageUri.length()-3);
			
			ImageLoader.getInstance().displayImage(fullImageUri, objectImageView, GoMinskApplicationClass.OPTIONS);
			//objectImageView.setImageURI(currentGeoObject.getImageURI());
		}
		
		 TextView objectDestination = (TextView)rootLayout.findViewById(R.id.destinationTextView);
		 objectDestination.setText(currentGeoObject.getDestinationInListView());
		 objectDestination.setOnClickListener(new GeoClick());
		 
		 ImageView geoImageView = (ImageView)rootLayout.findViewById(R.id.mapMarkerImageView);
		 geoImageView.setOnClickListener(new GeoClick());
		 
		return rootLayout;
	}
	
	
	class GeoClick implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			Location userLocation = LocationHelper.getLastUserLocation();
			if(userLocation != null){
				Uri uri =  Uri.parse("http://maps.google.com/maps?saddr="+userLocation.getLatitude()+","+userLocation.getLongitude()+"&daddr="+currentGeoObject.getLatitude()+","+currentGeoObject.getLongitude());
				//Uri uri =  Uri.parse("geo:"+userLocation.getLatitude()+","+userLocation.getLongitude()+","+currentGeoObject.getLatitude()+","+currentGeoObject.getLongitude());
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				intent.setComponent(new ComponentName("com.google.android.apps.maps",      "com.google.android.maps.MapsActivity"));
				startActivity(intent);
			}else
				Toast.makeText(getActivity(), R.string.no_location_warning, Toast.LENGTH_LONG).show();
			
		}
	}
	
	@Override
	public void onClick(View view) {
		switch(view.getId()){
			case R.id.mapIconImageView:
				goToCityMapActivity();
				break;
			case R.id.destinationTextView:
				goToCityMapActivity();
				break;
		}
	}
	private void goToCityMapActivity() {
		
	}
}
