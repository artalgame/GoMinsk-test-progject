package com.flaxtreme.gominsktestapp.activity;

import android.os.Bundle;

import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.fragment.BaseMapFragment;

public class CityMapActivity extends BaseBackActivity {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentFragment(new BaseMapFragment());
		setWindowTitle(getString(R.string.title_city_map));
	}
}
