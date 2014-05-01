package com.flaxtreme.gominsktestapp.fragment;

import java.util.ArrayList;

import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.adapter.listview.WalkGeoObjectsListViewAdapter;
import com.flaxtreme.gominsktestapp.adapter.sectionpager.WalkSectionPagerAdapter;
import com.flaxtreme.gominsktestapp.db.GeoObjectDBAdapter;
import com.flaxtreme.gominsktestapp.db.WalkObjectGeoObjectDBAdapter;
import com.flaxtreme.gominsktestapp.entity.GeoObject;
import com.flaxtreme.gominsktestapp.entity.WalkObject;
import com.flaxtreme.gominsktestapp.entity.WalkObjectGeoObject;
import com.flaxtreme.gominsktestapp.interfaces.IDataArraySource;
import com.flaxtreme.gominsktestapp.interfaces.IFragmentsGetter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

public class WalkFragment extends Fragment implements IFragmentsGetter, IDataArraySource<GeoObject> {
	
	private enum TABS{
		LIST,
		MAP
	}
	
	public static final int FRAGMENTS_COUNT = 2;
	
	private static final String TAB_LIST_TAG = "List";
	private static final String TAB_MAP_TAG = "Map";
	
	private ViewPager mViewPager;
	private WalkSectionPagerAdapter mSectionsPagerAdapter;
	
	private TabHost tabHost;

	private WalkObject currentWalk;

	private WalkDetailsFragment walkDetailedFragment;

	private WalkMapFragment mapFragment;
	private static GeoObject[] currentGeoObjects;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentWalk = getArguments().getParcelable(GoMinskConstants.BUNDLE_WALK_OBJECT_PARCELABLE);
		
		walkDetailedFragment = new WalkDetailsFragment();
		Bundle arguments = new Bundle();
		arguments.putParcelable(GoMinskConstants.BUNDLE_WALK_OBJECT_PARCELABLE, currentWalk);
		WalkDetailsFragment.geoObjectListViewAdapter = new WalkGeoObjectsListViewAdapter(getActivity(), this);
		walkDetailedFragment.setArguments(arguments);
		
		mapFragment = new WalkMapFragment();
		
		new LoadWalkGeoObjectAsyncTask(getActivity(),currentWalk.getId()).execute();
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	
		View rootLayout = inflater.inflate(R.layout.fragment_walk, container, false);
		
		initializeTabHost(rootLayout); 
		initializeViewPager(rootLayout);
		
		return rootLayout;
	}

	private void initializeViewPager(View rootLayout) {
		mSectionsPagerAdapter = new WalkSectionPagerAdapter(
				getActivity().getSupportFragmentManager(), getActivity(), this );

		mViewPager = (ViewPager) rootLayout.findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						tabHost.setCurrentTab(position);
					}
				});
	}

	/**
	 * Initialize tabHost object 
	 */
	private void initializeTabHost(View rootLayout) {
		
		tabHost = (TabHost)rootLayout.findViewById(android.R.id.tabhost);
		
		if(tabHost != null)
		{
			tabHost.setup();
			View tabView = getTabView(TABS.LIST, tabHost.getTabWidget());
			TabHost.TabSpec spec = tabHost.newTabSpec(TAB_LIST_TAG).setIndicator(tabView).setContent(android.R.id.tabcontent);
			tabHost.addTab(spec);
			
			
			tabView = getTabView(TABS.MAP, tabHost.getTabWidget());
			spec = tabHost.newTabSpec(TAB_MAP_TAG).setIndicator(tabView).setContent(android.R.id.tabcontent);
			tabHost.addTab(spec);
			
			//Invoke ViewPager setCurrentItem element to change current fragment
			tabHost.setOnTabChangedListener(new OnTabChangeListener() {
				
				@Override
				public void onTabChanged(String tabId) {
					mViewPager.setCurrentItem(tabHost.getCurrentTab());
					
				}
			});
		}
	}
	
    private View getTabView(TABS tabName, ViewGroup rootView) {
		
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_custom_tab, rootView, false);
		
		TextView name = (TextView)view.findViewById(R.id.tabName);
		ImageView image = (ImageView)view.findViewById(R.id.tabImage);
		switch (tabName){
		case LIST:
			name.setText(R.string.button_list_name);
			name.setContentDescription(getString(R.string.button_list_descr));
			
			image.setImageResource(R.drawable.list);
			image.setContentDescription(getString(R.string.button_list_descr));
			break;
		case MAP:
			name.setText(R.string.button_map_name);
			name.setContentDescription(getString(R.string.button_map_descr));
			
			image.setImageResource(R.drawable.map);
			image.setContentDescription(getString(R.string.button_map_descr));
			break;
		default:
			return null;
		}
		
		return view;
	}

    
    public class LoadWalkGeoObjectAsyncTask extends AsyncTask<Void, Void, GeoObject[]>{
		private Context context;
		private long walkId;
		
		public LoadWalkGeoObjectAsyncTask(Context context, long walkId){
			this.context = context;
			this.walkId = walkId;
		}
		
		@Override
		protected GeoObject[] doInBackground(Void...voids) {
			WalkObjectGeoObjectDBAdapter dbAdapter = new WalkObjectGeoObjectDBAdapter(context);
			
			Object[] objects =  dbAdapter.getAllObjectsForWalkId(walkId);
			GeoObjectDBAdapter geoObjectDBAdapter = new GeoObjectDBAdapter(context);
			
			ArrayList<GeoObject> geoObjects = new ArrayList<GeoObject>();
			for(int i=0; i<objects.length; i++){
				WalkObjectGeoObject walkObjectGeoObject = (WalkObjectGeoObject) objects[i];
				long geoObjectId = walkObjectGeoObject.getGeoObjectId();
				GeoObject geoObject = geoObjectDBAdapter.getObjectItem(geoObjectId);
				geoObjects.add(geoObject);
			}
			GeoObject[] geoObjectsArray = new GeoObject[geoObjects.size()];
			return geoObjects.toArray(geoObjectsArray);
		}
		
		@Override
		 protected void onPostExecute(GeoObject[] result) {
			currentGeoObjects = result;
			updateSubFragmentsData();
	     }
	}
    
    public void updateSubFragmentsData(){
    	WalkDetailsFragment.geoObjectListViewAdapter.notifyDataSetChanged();
    	mapFragment.setObjects(currentGeoObjects);
    }
    
	@Override
	public Fragment getSubFragment(int pos) {
		switch(pos){
		case 0:
			return walkDetailedFragment;
		case 1:
			return mapFragment;
		}
		return null;
	}

	@Override
	public int getSubFragmentCount() {
		return FRAGMENTS_COUNT;
	}

	@Override
	public GeoObject[] getDataArray() {
		return currentGeoObjects;
	}
}
