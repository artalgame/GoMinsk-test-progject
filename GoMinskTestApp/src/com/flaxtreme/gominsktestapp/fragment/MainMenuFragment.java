package com.flaxtreme.gominsktestapp.fragment;

import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.activity.BaseActivity;
import com.flaxtreme.gominsktestapp.view.BigMainMenuButtons;
import com.flaxtreme.gominsktestapp.view.SmallMainMenuButtons;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

public class MainMenuFragment extends Fragment {
	
	private BigMainMenuButtons bigButtons;
	private SmallMainMenuButtons smallButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	
		View rootLayout = inflater.inflate(R.layout.fragment_main_activity_buttons, container, false);
		
		rootLayout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				BaseActivity activity = (BaseActivity)getActivity();
				if(activity!=null)
					activity.setDefaultState();
				return false;
			}
		});
		
		bigButtons = (BigMainMenuButtons)rootLayout.findViewById(R.id.bigMainButtons);
		smallButton = (SmallMainMenuButtons)rootLayout.findViewById(R.id.smallMainButtons);
		
		return rootLayout;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		setDefaultState();
	};
	
	@Override
	public void onResume(){
		super.onResume();
		setDefaultState();
	}
	

	public void setDefaultState() {
		bigButtons.setDefaultState();
		smallButton.setDefaultState();
		
	}
}
