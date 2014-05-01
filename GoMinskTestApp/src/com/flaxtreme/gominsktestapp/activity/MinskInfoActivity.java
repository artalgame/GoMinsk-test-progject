package com.flaxtreme.gominsktestapp.activity;

import com.flaxtreme.gominsktestapp.FlurryConstants;
import com.flaxtreme.gominsktestapp.fragment.MinskInfoFragment;
import com.flurry.android.FlurryAgent;

import android.os.Bundle;

public class MinskInfoActivity extends BaseBackActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentFragment(new MinskInfoFragment());
		FlurryAgent.logEvent(FlurryConstants.MINSK_INFO_ACTIVITY);
	}
}
