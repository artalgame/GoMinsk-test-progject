package com.flaxtreme.gominsktestapp.activity;

import java.util.HashMap;

import android.os.Bundle;

import com.flaxtreme.gominsktestapp.FlurryConstants;
import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.entity.WalkObject;
import com.flaxtreme.gominsktestapp.fragment.WalkFragment;
import com.flurry.android.FlurryAgent;

public class WalkActivity extends BaseBackActivity {
	private WalkObject currentWalkObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentWalkObject = getIntent().getParcelableExtra(GoMinskConstants.BUNDLE_WALK_OBJECT_PARCELABLE);	
		
		if(currentWalkObject!=null){
			HashMap<String, String> walkMap = new HashMap<String, String>();
			walkMap.put(FlurryConstants.WALK_ID, ((Long)currentWalkObject.getId()).toString());
			FlurryAgent.logEvent(FlurryConstants.WALK_DETAILS_VIEWED, walkMap);
		}
		
		Bundle fragmentBundle = new Bundle();
		fragmentBundle.putParcelable(GoMinskConstants.BUNDLE_WALK_OBJECT_PARCELABLE, currentWalkObject);
		
		WalkFragment walkFragment = new WalkFragment();
		walkFragment.setArguments(fragmentBundle);
		
		setContentFragment(walkFragment);
	}
}
