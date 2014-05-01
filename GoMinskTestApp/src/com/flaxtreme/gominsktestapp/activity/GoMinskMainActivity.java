package com.flaxtreme.gominsktestapp.activity;

import com.flaxtreme.gominsktestapp.APIHelper;
import com.flaxtreme.gominsktestapp.DialogHelper;
import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.fragment.MainMenuFragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;

public class GoMinskMainActivity extends BaseActivity {
	
	private MainMenuFragment mainMenuFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences settings = getSharedPreferences(GoMinskConstants.SHARED_PREFERENCES, 0);
		
		boolean isFirstStart = settings.getBoolean(GoMinskConstants.PREF_IS_FIRST_START, true);
		
		if(isFirstStart){
			showWelcomedDialog();
		}else
		{
			boolean isDataBaseLoaded = settings.getBoolean(GoMinskConstants.PREF_IS_DATABASE_LOADED, false);
			if(!isDataBaseLoaded){
				showDataBaseDialog();
			}
			else
			{
				boolean isCheckDBUpdateEachDay = settings.getBoolean(GoMinskConstants.PREF_IS_CHECK_DB_UPDATE_EACH_DAY, true);
				if(isCheckDBUpdateEachDay){
					checkIfNeedUpdate();
				}
			}
		}
		
		mainMenuFragment = new MainMenuFragment();
		setContentFragment(mainMenuFragment);
		setWindowTitle(getString(R.string.app_name));
		//Delete after testing data loading
	//	dropDataBase();
	}
	
	private void checkIfNeedUpdate() {
		String lastUpdateTimeString = getSharedPreferences(GoMinskConstants.SHARED_PREFERENCES, 0).getString(GoMinskConstants.PREF_LAST_DB_DATE_UPDATE, "1900-01-01");
		Time lastUpdateTime = new Time();
		lastUpdateTime.parse3339(lastUpdateTimeString);
		Time currentTime = new Time();
		currentTime.setToNow();
	
		if ((currentTime.toMillis(false) - lastUpdateTime.toMillis(false)> 24*60*60*1000)){
			new CheckUpdateTask(this, lastUpdateTimeString).execute();
		}
		
	}
	
	public class CheckUpdateTask extends AsyncTask<Void, Void, Integer>{

		Activity activity;
		String lastUpdateDate;
		
		public CheckUpdateTask(Activity activity, String lastUpdateDate){
			this.activity = activity;
			this.lastUpdateDate = lastUpdateDate;
		}
		@Override
		protected Integer doInBackground(Void... params) {
			return APIHelper.checkIfNeedUpdate(activity, lastUpdateDate);
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if(result>0){
				DialogHelper.showDataBaseDialog(activity);
			}
		}
	}

	private void showDataBaseDialog() {
		DialogHelper.showDataBaseDialog(this);
	}

	private void showWelcomedDialog() {
		DialogHelper.showWelcomedDialog(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == GoMinskConstants.DATABASE_LOADER_REQUEST_CODE){
			if(resultCode == RESULT_OK){
				//getSharedPreferences(GoMinskConstants.SHARED_PREFERENCES, 0).edit().putBoolean(GoMinskConstants.PREF_IS_DATABASE_LOADED, true).commit();
			}
		}
	};
	
	@Override
	public void setDefaultState() {
		super.setDefaultState();
		mainMenuFragment.setDefaultState();
	};
	
	@Override
	protected void onStart() {
		super.onStart();
	};
	
	@Override
	protected void onResume() {
		super.onResume();
		
		SharedPreferences settings = getSharedPreferences(GoMinskConstants.SHARED_PREFERENCES, 0);
		String currentLanguage = settings.getString(GoMinskConstants.PREF_LAUNGUAGE_CODE, GoMinskConstants.DEFAULT_LANGUAGE);
		if(languageCode!=currentLanguage){
			recreate();
		}
	};
	
	@Override
	protected void onPause() {
		super.onPause();
	};
	
	@Override
	protected void onStop() {
		super.onStop();
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.go_minsk_main, menu);
		return true;
	}
}
