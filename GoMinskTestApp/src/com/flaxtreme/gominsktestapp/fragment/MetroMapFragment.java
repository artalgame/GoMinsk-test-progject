package com.flaxtreme.gominsktestapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxtreme.gominsktestapp.GoMinskConstants;

public class MetroMapFragment extends BaseMapFragment {
	
	@Override
	public long getCurrentCategoryId() {
		// TODO Auto-generated method stub
		return GoMinskConstants.METRO_MAIN_CATEGORY_ID;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = super.onCreateView(inflater, container, savedInstanceState);		
		return view;
	}
}
