package com.flaxtreme.gominsktestapp.interfaces;
/**
 * 
 * @author Alexander Korolchuk
 *
 * Implements methods to get/set data for correct displaying object on map
 */
public interface IMapObject {
	
	public double getLatitude();

	public double getLongitude();

	public String getNameOnMarker();
	
	public String getDescriptionOnMarker();

	public String getDestination();
}
