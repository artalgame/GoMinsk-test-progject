package com.flaxtreme.gominsktestapp.fragment;

import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.LocaleHelper;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.ViewHelper;
import com.flaxtreme.gominsktestapp.activity.GeoObjectDetailedActivity;
import com.flaxtreme.gominsktestapp.adapter.listview.WalkGeoObjectsListViewAdapter;
import com.flaxtreme.gominsktestapp.entity.GeoObject;
import com.flaxtreme.gominsktestapp.entity.WalkObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class WalkDetailsFragment extends Fragment  {
	
	private View rootLayout;
	private ListView categoryObjectsListView;
	private WalkObject currentWalk;
	public static WalkGeoObjectsListViewAdapter geoObjectListViewAdapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getArguments()!=null){
			currentWalk = getArguments().getParcelable(GoMinskConstants.BUNDLE_WALK_OBJECT_PARCELABLE);
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootLayout = inflater.inflate(R.layout.fragment_walk_detailed, container, false);
		if(currentWalk!=null){
			TextView walkName = (TextView) rootLayout.findViewById(R.id.walkNameTextView);
			walkName.setText(currentWalk.getTitle());
			
			TextView walkDescription = (TextView) rootLayout.findViewById(R.id.walkDescriptionTextView);
			walkDescription.setText(currentWalk.getDescription());
			ViewHelper.makeTextViewResizable(walkDescription, 7, getActivity().getString(R.string.view_more), true, getActivity());
			
			TextView walkTime = (TextView) rootLayout.findViewById(R.id.walkTimeView);
			walkTime.setText(LocaleHelper.getLocalizeWalkTime(currentWalk.getPlaningTime()));
		}
		
		categoryObjectsListView = (ListView)rootLayout.findViewById(R.id.walkObjectsListView);
		categoryObjectsListView.setAdapter(geoObjectListViewAdapter);
		categoryObjectsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View item, int position, long id) {
					GeoObject currentGeoObject = (GeoObject) geoObjectListViewAdapter.getItem(position);
					Intent categoryIntent = new Intent(getActivity(), GeoObjectDetailedActivity.class);
					categoryIntent.putExtra(GoMinskConstants.INTENT_GEO_OBJECT_KEY, currentGeoObject);
					getActivity().startActivity(categoryIntent);
			}
		});
		
		return rootLayout;
	}
	
	
		
}
