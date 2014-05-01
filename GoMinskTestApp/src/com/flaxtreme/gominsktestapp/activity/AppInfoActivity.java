package com.flaxtreme.gominsktestapp.activity;

import android.os.Bundle;

import com.flaxtreme.gominsktestapp.FlurryConstants;
import com.flaxtreme.gominsktestapp.fragment.AppInfoFragment;
import com.flurry.android.FlurryAgent;

public class AppInfoActivity extends BaseBackActivity {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentFragment(new AppInfoFragment());
		
		FlurryAgent.logEvent(FlurryConstants.APP_INFO_ACTIVITY);
	}
	
}
