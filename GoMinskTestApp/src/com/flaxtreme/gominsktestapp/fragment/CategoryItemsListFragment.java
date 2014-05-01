package com.flaxtreme.gominsktestapp.fragment;

import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.activity.GeoObjectDetailedActivity;
import com.flaxtreme.gominsktestapp.adapter.listview.GeoObjectListViewAdapter;
import com.flaxtreme.gominsktestapp.entity.GeoObject;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author Alexander Korolchuk
 *
 *Fragment for displaying category objects
 *
 */
public class CategoryItemsListFragment extends Fragment{
	
	private View rootLayout;
	private ListView geoObjectsListView;
	private GeoObjectListViewAdapter geoObjectsListViewAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		rootLayout = inflater.inflate(R.layout.fragment_category_items_list, container, false);
		geoObjectsListView = (ListView)rootLayout.findViewById(R.id.categoryItemsListView);
	
		geoObjectsListView.setAdapter(geoObjectsListViewAdapter);
		
		geoObjectsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				view.setSelected(true);
				
				GeoObject selectedGeoObject = (GeoObject)geoObjectsListView.getAdapter().getItem(position);
				
				Intent categoryIntent = new Intent(getActivity(), GeoObjectDetailedActivity.class);
				categoryIntent.putExtra(GoMinskConstants.INTENT_GEO_OBJECT_KEY, selectedGeoObject);
				
				getActivity().startActivity(categoryIntent);
			}
		});

		return rootLayout;
	}
	
	@Override
	public void onStart() {
		super.onStart();
	};
	
	@Override
	public void onResume() {
		super.onResume();
	};

	public void setListViewAdapter(GeoObjectListViewAdapter adapter){
		this.geoObjectsListViewAdapter = adapter;
	}
}
