package com.flaxtreme.gominsktestapp.fragment;

import com.flaxtreme.gominsktestapp.DialogHelper;
import com.flaxtreme.gominsktestapp.FlurryConstants;
import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.R;
import com.flurry.android.FlurryAgent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AppInfoFragment extends Fragment {
	
	public AppInfoFragment(){
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View rootView = inflater.inflate(R.layout.fragment_app_info, container, false);
		Button updateButton = (Button)rootView.findViewById(R.id.updateButton);
		
		updateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogHelper.showDataBaseDialog(getActivity());
			}
		});
		
		Button changeLanguageButton = (Button)rootView.findViewById(R.id.changeLanguageButton);
		changeLanguageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogHelper.showWelcomedDialog(getActivity());
			}
		});
		
		CheckBox updateEachDay = (CheckBox)rootView.findViewById(R.id.dbUpdateCheckBox);
		
		boolean checkUpdates = getActivity().getSharedPreferences(GoMinskConstants.SHARED_PREFERENCES, 0).getBoolean(GoMinskConstants.PREF_IS_CHECK_DB_UPDATE_EACH_DAY, true);
		updateEachDay.setChecked(checkUpdates);
		
		updateEachDay.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				getActivity().getSharedPreferences(GoMinskConstants.SHARED_PREFERENCES, 0).edit().putBoolean(GoMinskConstants.PREF_IS_CHECK_DB_UPDATE_EACH_DAY, isChecked).commit();
				if(isChecked){
					FlurryAgent.logEvent(FlurryConstants.CHECK_UPDATE_EACH_DAY);
				}else
					FlurryAgent.logEvent(FlurryConstants.CHECK_NO_UPDATE_EACH_DAY);
			}
		});
		
		return rootView;
		
		
		
		
	}
}
