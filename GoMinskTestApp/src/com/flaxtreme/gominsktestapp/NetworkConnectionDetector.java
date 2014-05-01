package com.flaxtreme.gominsktestapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnectionDetector {
	public static boolean isConnectionToInternet(Context context){
		ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivity!=null){
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			for(int i=0; i<info.length; i++)
			{
				if(info[i].getState() == NetworkInfo.State.CONNECTED){
					return true;
				}
			}
		}
		return false;
	}
}
