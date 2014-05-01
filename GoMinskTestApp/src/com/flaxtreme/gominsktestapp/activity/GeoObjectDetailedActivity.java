package com.flaxtreme.gominsktestapp.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.flaxtreme.gominsktestapp.FlurryConstants;
import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.entity.GeoObject;
import com.flaxtreme.gominsktestapp.fragment.GeoObjectDetailedFragment;
import com.flurry.android.FlurryAgent;

public class GeoObjectDetailedActivity extends BaseBackActivity {
	private GeoObject currentGeoObject;
	private Fragment geoObjectDetailedFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		currentGeoObject = (GeoObject)getIntent().getParcelableExtra(GoMinskConstants.INTENT_GEO_OBJECT_KEY);
		
		if(currentGeoObject!=null){
			HashMap<String, String> objectMap = new HashMap<String, String>();
			objectMap.put(FlurryConstants.OBJECT_ID, ((Long)currentGeoObject.getId()).toString());
			FlurryAgent.logEvent(FlurryConstants.OBJECT_DETAILS_VIEWED, objectMap);
		}
		
		Bundle fragmentBudle = new Bundle();
		fragmentBudle.putParcelable(GoMinskConstants.BUNDLE_GEO_OBJECT_PARCELABLE, currentGeoObject);
		
		geoObjectDetailedFragment = new GeoObjectDetailedFragment();
		geoObjectDetailedFragment.setArguments(fragmentBudle);
		
		setContentFragment(geoObjectDetailedFragment);
	}
}
