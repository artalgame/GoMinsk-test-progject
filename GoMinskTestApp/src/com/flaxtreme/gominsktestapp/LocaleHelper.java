package com.flaxtreme.gominsktestapp;

import java.util.Locale;

import android.content.Context;

public class LocaleHelper {
	public static Context context;
	
	    //1.5 h
		public static final String TIME_TEMPLATE1 = "%s.%s %s";
		//1h 20m
		public static final String TIME_TEMLPATE2 = "%s%s %s%s";
		//2h or 40m
		public static final String TIME_TEMPLATE3 = "%s%s";
	
	public static String getLocalizeObjectField(String ruField, String beField, String enField){
	
		String localization = getCurrentLocalization(); 
		
		if(localization.equals( GoMinskConstants.BE_LANGUAGE)){
			if(beField == null || beField.trim().equals(""))
			{
				return ruField;
			}
			return beField;
		}
		if(localization.equals(GoMinskConstants.RU_LANGUAGE)){
			return ruField;
		}
		if(localization.equals(GoMinskConstants.EN_LANGUAGE)){
			return enField;
		}
		if(Locale.getDefault().getLanguage().equals( GoMinskConstants.RU_LANGUAGE)){
			return ruField;
		}
		return enField;
		
	}
	private static String getCurrentLocalization() {
		return context.getSharedPreferences(GoMinskConstants.SHARED_PREFERENCES, 0).getString(GoMinskConstants.PREF_LAUNGUAGE_CODE, GoMinskConstants.DEFAULT_LANGUAGE);
	}
	
	public static String getLocalizeDestination(
			int destinationInMetersFromCurrentUserPosition) {
		
		String kmPrefix = context.getString(R.string.km_prefix);
		String mPrefix = context.getString(R.string.m_prefix);
		
		int km = destinationInMetersFromCurrentUserPosition/1000;
		
		if(km == 0){
			return destinationInMetersFromCurrentUserPosition + mPrefix;
		}else
		{
			int m = destinationInMetersFromCurrentUserPosition % 1000;
			return km + kmPrefix+ " " + m +mPrefix;
		}
	}
	public static CharSequence getLocalizeWalkTime(int planingTime) {
		
		String hourSuffix = context.getString(R.string.hour_suffix);
		String minuteSuffix = context.getString(R.string.minute_suffix);
		
		if(planingTime % 60 == 30){
			return String.format(TIME_TEMPLATE1, ((Integer)(planingTime / 60)).toString(), 5, hourSuffix) ; 
		}
		else
			if (planingTime % 60 == 0){
				return String.format(TIME_TEMPLATE3, ((Integer)(planingTime / 60)).toString(), hourSuffix);
			}
			else
				if(planingTime<60){
					return String.format(TIME_TEMPLATE3, planingTime, minuteSuffix);
				}
				else
					return String.format(TIME_TEMLPATE2,((Integer)(planingTime / 60)).toString(), hourSuffix, ((Integer)(planingTime % 60)).toString(), minuteSuffix );
	}
}
