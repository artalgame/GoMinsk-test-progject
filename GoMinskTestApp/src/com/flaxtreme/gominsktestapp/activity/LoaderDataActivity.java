package com.flaxtreme.gominsktestapp.activity;

import com.flaxtreme.gominsktestapp.APIHelper;
import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.interfaces.IProgressCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.Toast;

public class LoaderDataActivity extends Activity {
	
	private static int dialogMaxProgress = 0; 
	private DownloadCategoriesAsyncTask downloadTask;
	private static ProgressDialog dialog;
	private static int currentProgress = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentProgress = 0;
		String lastDBDateUpdate = getSharedPreferences(GoMinskConstants.SHARED_PREFERENCES, 0).getString(GoMinskConstants.PREF_LAST_DB_DATE_UPDATE, null);
		
		dialog = new ProgressDialog(this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.setMax(dialogMaxProgress);
		dialog.setProgress(currentProgress);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setMessage(getString(R.string.db_update_dialog_text));
		dialog.setTitle(R.string.db_update_dialog_title);
		
		downloadTask = new DownloadCategoriesAsyncTask(this, lastDBDateUpdate);
		downloadTask.execute(new String[0]);		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		setAndShowDialog();
	}
	
	private void setAndShowDialog() {
		dialog.show();
	}

	private static class DownloadCategoriesAsyncTask extends AsyncTask<String, Void, String> implements IProgressCallback {
		Activity context;
		private String dateUpdate;
		public DownloadCategoriesAsyncTask(Activity context, String dateUpdate){
			this.context = context;
			this.dateUpdate = dateUpdate;
		}
		
	    @Override
	    protected String doInBackground(String... urls) {
	    	APIHelper apiHelper = new APIHelper(context);
	    	
	    	dialogMaxProgress = APIHelper.checkIfNeedUpdate(context, dateUpdate);
	    	
	    	try
	    	{
	    	if(dateUpdate == null){
	    		apiHelper.firstDBLoad(this);
	    	}
	    	else{
	    		apiHelper.updateDB(this, dateUpdate);
	    	}
	    	}catch(Exception ex){
	    		//TODO roll last updates - maybe should create transactions
	    	}
	    	
	    	return "Done";
	    }
	    
	    @Override
	    protected void onProgressUpdate(Void... values) {
	    	dialog.setMax(dialogMaxProgress);
	    	dialog.setProgress(currentProgress);
	    };

	    @Override
	    protected void onPostExecute(String result) {
	      context.getSharedPreferences(GoMinskConstants.SHARED_PREFERENCES, 0).edit().putBoolean(GoMinskConstants.PREF_IS_DATABASE_LOADED, true).commit();
	      
	      Time t = new Time(Time.getCurrentTimezone());
	      t.setToNow();
	      String date = t.format("%Y-%m-%d");
	      
	      context.getSharedPreferences(GoMinskConstants.SHARED_PREFERENCES,  0).edit().putString(GoMinskConstants.PREF_LAST_DB_DATE_UPDATE, date).commit();
	      
	      dialog.dismiss();
	      context.setResult(Activity.RESULT_OK);
	      Toast.makeText(context.getApplicationContext(), context.getString(R.string.db_update_finish_toast), Toast.LENGTH_LONG).show();
	      context.finish();
	    }

		@Override
		public void addToProgress(int koef) {
			currentProgress+=koef;
		    publishProgress();
		}
	  }

}
