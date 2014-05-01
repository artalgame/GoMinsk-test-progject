package com.flaxtreme.gominsktestapp.activity;

import java.util.Locale;

import com.flaxtreme.gominsktestapp.FlurryConstants;
import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.LocaleHelper;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.view.WindowGeneralElementsView;
import com.flurry.android.FlurryAgent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


public class BaseActivity extends FragmentActivity  {
	
	protected String languageCode = GoMinskConstants.DEFAULT_LANGUAGE;
	WindowGeneralElementsView mBaseView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		languageCode = getSharedPreferences(GoMinskConstants.SHARED_PREFERENCES, 0).getString(GoMinskConstants.PREF_LAUNGUAGE_CODE, null);
	    if (languageCode==null)
	    {
	    	languageCode = GoMinskConstants.DEFAULT_LANGUAGE;
	    }
	    
	    LocaleHelper.context = this;
	    setLanguage();
	
		
		initializeBaseView();
		
		getWindow().getDecorView().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			setDefaultState();	
			}
		});
	}
	
	protected void initializeBaseView() {
		setContentView(R.layout.activity_go_minsk_main);
		mBaseView = (WindowGeneralElementsView)((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
		
	}

	private void setLanguage() {
		String languageToLoad  = languageCode; 
	    Locale locale = new Locale(languageToLoad); 
	    Locale.setDefault(locale);
	    Configuration config = new Configuration();
	    config.locale = locale;
	    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
		
	}

	public void setDefaultState() {
		mBaseView.setDefaultDrawerState();			
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		FlurryAgent.onStartSession(this, FlurryConstants.FLURRY_KEY);
		setDefaultState();
	}
	 
	@Override
	protected void onStop()
	{
		super.onStop();		
		FlurryAgent.onEndSession(this);
	}
	
	protected void setContentFragment(Fragment createdFragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
	    fragmentManager.beginTransaction()
	                   .replace(R.id.content_frame, createdFragment)
	                   .commit();		
	}
	
	public void setWindowTitle(CharSequence title){
		mBaseView.setTitle(title);
	}
	
	public CharSequence getWindowTitle(){
		return mBaseView.getTitle();
	}
	
	public void setActionBarBackgroundDrawable(Drawable background){
		mBaseView.setBackgroundDrawable(background);
	}
}
