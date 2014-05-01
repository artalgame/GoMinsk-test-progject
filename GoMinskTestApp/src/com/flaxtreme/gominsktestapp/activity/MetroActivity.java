package com.flaxtreme.gominsktestapp.activity;

import android.os.Bundle;

import com.flaxtreme.gominsktestapp.fragment.MetroMapFragment;

public class MetroActivity extends BaseBackActivity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentFragment(new MetroMapFragment());
		setWindowTitle("Metropolitan");
	}
}
