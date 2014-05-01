package com.flaxtreme.gominsktestapp.activity;

import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.view.WindowGeneralElementsWithBackButtonView;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class BaseBackActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void initializeBaseView() {
		setContentView(R.layout.activity_go_minsk_back);
		
		mBaseView = (WindowGeneralElementsWithBackButtonView)((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
		((WindowGeneralElementsWithBackButtonView)mBaseView).getBackButton().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}
}
