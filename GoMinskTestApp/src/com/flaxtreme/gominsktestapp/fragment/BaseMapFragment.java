package com.flaxtreme.gominsktestapp.fragment;

import com.flaxtreme.gominsktestapp.CategoryMapDrawer;
import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.MetroMapDrawer;
import com.flaxtreme.gominsktestapp.NetworkConnectionDetector;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.provider.OfflineMapTileProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.TileOverlayOptions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Base class for all maps fragments in application
 * @author Alexander Korolchuk
 *
 */
public class BaseMapFragment extends Fragment {
	public final static float MIN_OFFLINE_MAP_ZOOM=12f;
	public final static float MAX_OFFLINE_MAP_ZOOM=15f;
	
    protected GoogleMap map;
	protected boolean offlineMap = false;
	protected CameraPosition lastCameraPosition;
	private View rootLayout;
	private LinearLayout bottom_panel;
	
	private CategoryMapDrawer sightCategoryBottomButton;
	private CategoryMapDrawer shopCategoryBottomButton;
	protected CategoryMapDrawer metroCategoryBottomButton;
	private CategoryMapDrawer foodCategoryBottomButton;
	private CategoryMapDrawer wiFiCategoryBottomButton;
	private CategoryMapDrawer nightCategoryBottomButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	};
	
	public long getCurrentCategoryId(){
		return -1;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootLayout = inflater.inflate(R.layout.fragment_map, null, false);
        
		map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        
		initializeBottonPanel();
		
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {	
			@Override
			public void onInfoWindowClick(Marker marker) {
				checkClickedMarker(marker);
			}
		});
		
        //common settings
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        lastCameraPosition = map.getCameraPosition();
        
        map.setOnCameraChangeListener(new OnCameraChangeListener() {
			
			@Override
			public void onCameraChange(CameraPosition cameraPosition) {
				if(lastCameraPosition == null){
					lastCameraPosition = cameraPosition;
					return;
				}
				
				if(offlineMap){
					if(cameraPosition.zoom > MAX_OFFLINE_MAP_ZOOM){
						map.animateCamera(CameraUpdateFactory.zoomTo(MAX_OFFLINE_MAP_ZOOM));
					}
					else if(cameraPosition.zoom<MIN_OFFLINE_MAP_ZOOM)
					{
						map.animateCamera(CameraUpdateFactory.zoomTo(MIN_OFFLINE_MAP_ZOOM));
					}
				}	
			}
		});
        
        
        if(!NetworkConnectionDetector.isConnectionToInternet(getActivity()))
        {
        	offlineMap=true;
        	setOfflineMap();
        	return rootLayout;
        }
        return rootLayout;
	}
	
	protected void checkClickedMarker(Marker marker){	
		if(!sightCategoryBottomButton.checkInfoWindowClicked(marker))
			if(!shopCategoryBottomButton.checkInfoWindowClicked(marker))
				if(!metroCategoryBottomButton.checkInfoWindowClicked(marker))
					if(!foodCategoryBottomButton.checkInfoWindowClicked(marker))
						if(!wiFiCategoryBottomButton.checkInfoWindowClicked(marker))
							nightCategoryBottomButton.checkInfoWindowClicked(marker);
	}
    
	
	private void initializeBottonPanel() {
		bottom_panel = (LinearLayout)rootLayout.findViewById(R.id.categories_bottom_panel);
		
		sightCategoryBottomButton = getCategoryMapDrawer(GoMinskConstants.SIGHT_CATEGORY_ID, R.id.button1);
		
		metroCategoryBottomButton = new MetroMapDrawer(
				getActivity(), 
				GoMinskConstants.METRO_MAIN_CATEGORY_ID, 
				bottom_panel.findViewById(R.id.button2),
				isCurrentCategory(GoMinskConstants.METRO_MAIN_CATEGORY_ID),
				map); 
				
		shopCategoryBottomButton =  getCategoryMapDrawer(GoMinskConstants.SHOPS_CATEGORY_ID, R.id.button3);
		foodCategoryBottomButton =  getCategoryMapDrawer(GoMinskConstants.FOOD_CATEGORY_ID, R.id.button4);
		wiFiCategoryBottomButton =  getCategoryMapDrawer(GoMinskConstants.WiFi_CATEGORY_ID, R.id.button5);
		nightCategoryBottomButton = getCategoryMapDrawer(GoMinskConstants.NIGHT_LIFE_CATEGORY_ID, R.id.button6);
	}

	private CategoryMapDrawer getCategoryMapDrawer(long categoryId,int buttonId) {
		return new CategoryMapDrawer(
				getActivity(), 
				categoryId, 
				bottom_panel.findViewById(buttonId),
				isCurrentCategory(categoryId),
				map);
	}

	private boolean isCurrentCategory(long categoryId) {
		return categoryId == getCurrentCategoryId();
	}

	private void setOfflineMap() {
		   map.setMapType(GoogleMap.MAP_TYPE_NONE);
		   map.getUiSettings().setRotateGesturesEnabled(false);
	       map.addTileOverlay(new TileOverlayOptions().tileProvider(new OfflineMapTileProvider(getResources().getAssets())));
		}
	

}