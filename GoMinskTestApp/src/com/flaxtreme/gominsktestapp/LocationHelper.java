package com.flaxtreme.gominsktestapp;

import com.google.android.gms.location.LocationClient;

import android.location.Location;

public class LocationHelper  {
	public static LocationClient mLocationClient;
	
	public static Location getLastUserLocation(){
		Location userLocation = mLocationClient.getLastLocation();
	    return userLocation;
	}
	
	public static int getDestinationInMeters(Location loc1, Location loc2){
		return (int)loc1.distanceTo(loc2);
	}

	public static int getDestinationInMetersFromCurrentUserPosition(
			double latitude, double longitude) {

		//wait until connect
		while(mLocationClient.isConnecting()){}
			try{
				Location lastLocation = getLastUserLocation();
				if(lastLocation!=null)
				{
					float[] results = new float[3];
					Location.distanceBetween(lastLocation.getLatitude(), lastLocation.getLongitude(), latitude, longitude, results);
					for(int i=0; i<3; i++){
						if(results[i]!= 0){
							return (int)results[i];
						}
					}
				}
			}catch(Exception ex){
				return 0;
			}
		return GoMinskConstants.VERY_BIG_DESTINATION;
	}
}
