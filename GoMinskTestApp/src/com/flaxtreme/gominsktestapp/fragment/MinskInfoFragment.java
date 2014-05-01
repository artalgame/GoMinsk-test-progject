package com.flaxtreme.gominsktestapp.fragment;

import com.flaxtreme.gominsktestapp.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MinskInfoFragment extends Fragment {
	public MinskInfoFragment(){
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View rootView = inflater.inflate(R.layout.fragment_minsk_info, container, false);
		
		return rootView;
		
	}
}
