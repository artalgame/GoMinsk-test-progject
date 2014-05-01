package com.flaxtreme.gominsktestapp;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.os.Bundle;

public class GoMinskApplicationClass extends Application  implements ConnectionCallbacks, OnConnectionFailedListener{
	private static GoMinskApplicationClass singletone = new GoMinskApplicationClass();
	LocationClient locationClient;
	
	public static DisplayImageOptions OPTIONS;
	
	public static GoMinskApplicationClass getSingletone(){
		if(singletone == null){
			singletone = new GoMinskApplicationClass();
		}
		return singletone;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		locationClient = new LocationClient(this, this, this);
		LocationHelper.mLocationClient = locationClient;
		locationClient.connect();
		
		OPTIONS = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.rectangle_sub)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
			.memoryCacheSize(4*1024*1024)
			.discCacheSize(50*1024*1024)
			.threadPoolSize(7)
			.build();
		
		
		ImageLoader.getInstance().init(config);
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	

     
}
