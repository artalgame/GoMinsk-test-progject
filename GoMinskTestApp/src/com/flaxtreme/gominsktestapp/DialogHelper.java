package com.flaxtreme.gominsktestapp;


import java.util.HashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.flaxtreme.gominsktestapp.activity.LoaderDataActivity;
import com.flurry.android.FlurryAgent;

public class DialogHelper  {
	
	public static void showWelcomedDialog(final Activity context) {
		// custom dialog
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.welcome_dialog);
		dialog.setTitle(R.string.welcome_string);
			
		dialog.setCancelable(false);
			
		RadioButton byRadioButton = (RadioButton)dialog.findViewById(R.id.byRadioButton);
		byRadioButton.setOnClickListener(languageClick);
		languageCode = GoMinskConstants.BE_LANGUAGE;
		byRadioButton.setChecked(true);
				
		RadioButton ruRadioButton = (RadioButton)dialog.findViewById(R.id.ruRadioButton);
		ruRadioButton.setOnClickListener(languageClick);
			
		RadioButton enRadioButton = (RadioButton)dialog.findViewById(R.id.enRadioButton);
		enRadioButton.setOnClickListener(languageClick);
 
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogOkButton);	
		
		dialogButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences settings = context.getSharedPreferences(GoMinskConstants.SHARED_PREFERENCES, 0);
				Editor settingsEditor = settings.edit();
				settingsEditor.putBoolean(GoMinskConstants.PREF_IS_FIRST_START, false);
				settingsEditor.putString(GoMinskConstants.PREF_LAUNGUAGE_CODE, languageCode);
				HashMap<String, String> languageMap = new HashMap<String, String>();
				languageMap.put(FlurryConstants.LANGUAGE, languageCode);
				FlurryAgent.logEvent(FlurryConstants.CHECKED_LANGUAGE, languageMap);
				settingsEditor.commit();
				dialog.dismiss();
				context.recreate();
			}
		});
		dialog.show();
	}

	protected static String languageCode;
	
	static View.OnClickListener languageClick = new OnClickListener() {
		@Override
		public void onClick(View view) {
			boolean checked = ((RadioButton)view).isChecked();
		    if(checked)
		    {
			    switch(view.getId()) {
			        case R.id.byRadioButton:
			             languageCode = GoMinskConstants.BE_LANGUAGE;
			            break;
			        case R.id.ruRadioButton:
			           	languageCode = GoMinskConstants.RU_LANGUAGE;
			            break;
			        case R.id.enRadioButton:
			       		languageCode = GoMinskConstants.EN_LANGUAGE;
			       		break;
			    }
		    }
		}
	};

	public static void showDataBaseDialog(final Activity context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		DialogInterface.OnClickListener databaseUpdateAlertDdialogClickListener = new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		        	if(NetworkConnectionDetector.isConnectionToInternet(context))
		        		context.startActivityForResult(new Intent(context, LoaderDataActivity.class), GoMinskConstants.DATABASE_LOADER_REQUEST_CODE );
		        	else
		        		Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
		            break;

		        case DialogInterface.BUTTON_NEGATIVE:
		        	Time t = new Time(Time.getCurrentTimezone());
		  	      	t.setToNow();
		  	      	String date = t.format("%Y-%m-%d");
		  	      
		  	      	context.getSharedPreferences(GoMinskConstants.SHARED_PREFERENCES,  0).edit().putString(GoMinskConstants.PREF_LAST_DB_DATE_UPDATE, date).commit();
		            dialog.dismiss();
		            break;
		        }
		    }
		};
		
		builder.setMessage(context.getString(R.string.update_database_warning)).setPositiveButton(context.getString(R.string.database_update_yes), databaseUpdateAlertDdialogClickListener)
		    .setNegativeButton(context.getString(R.string.database_update_no), databaseUpdateAlertDdialogClickListener).setCancelable(false).show();
		
		
	}
	
	
}
