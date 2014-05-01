package com.flaxtreme.gominsktestapp.activity;

import com.flaxtreme.gominsktestapp.fragment.SuggestedWalksListFragment;

import android.os.Bundle;

public class SuggestedWalksActivity extends BaseBackActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentFragment(new SuggestedWalksListFragment());
	    setTitle("SUGGESTED WALKS");
	}
}
