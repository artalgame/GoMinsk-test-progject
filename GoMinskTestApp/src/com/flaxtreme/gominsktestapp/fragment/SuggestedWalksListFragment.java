package com.flaxtreme.gominsktestapp.fragment;

import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.activity.WalkActivity;
import com.flaxtreme.gominsktestapp.adapter.listview.WalksListViewAdapter;
import com.flaxtreme.gominsktestapp.db.WalkObjectDBAdapter;
import com.flaxtreme.gominsktestapp.db.WalkObjectGeoObjectDBAdapter;
import com.flaxtreme.gominsktestapp.entity.WalkObject;
import com.flaxtreme.gominsktestapp.interfaces.IDBAdapter;
import com.flaxtreme.gominsktestapp.interfaces.IWalkListViewItem;
import com.flaxtreme.gominsktestapp.interfaces.IDataArraySource;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SuggestedWalksListFragment extends ListFragment implements IDataArraySource<IWalkListViewItem> {
	
	private View rootLayout;
	private static IWalkListViewItem[] walks = new IWalkListViewItem[0];
	private static WalksListViewAdapter walksListViewAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		walksListViewAdapter = new WalksListViewAdapter(getActivity(),this);
		new LoadWalksAsyncTask(getActivity()).execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		rootLayout = inflater.inflate(R.layout.fragment_suggested_walks_list, container, false);
		setListAdapter(walksListViewAdapter);
		return rootLayout;
	}
	
	public void onListItemClick (ListView l, View v, int position, long id){
		Intent walkActivity = new Intent(getActivity(), WalkActivity.class);
		walkActivity.putExtra(GoMinskConstants.BUNDLE_WALK_OBJECT_PARCELABLE, walks[position]);
		getActivity().startActivity(walkActivity);
	}

	@Override
	public IWalkListViewItem[] getDataArray() {
		return walks;
	}
	
	public static class LoadWalksAsyncTask extends AsyncTask<Void, Void, WalkObject[]>{
		public Activity context;
		
		public LoadWalksAsyncTask(Activity context){
			this.context = context;
		}
		
		@Override
		protected WalkObject[] doInBackground(Void...voids) {
			IDBAdapter<WalkObject> dbAdapter = new WalkObjectDBAdapter(context);
			Object[] objects =  dbAdapter.getAllObjects();
			WalkObject[] walks = new WalkObject[objects.length];
			for(int i=0; i<objects.length; i++){
				walks[i] = (WalkObject) objects[i];
			}
			return walks;
		}
		
		@Override
		 protected void onPostExecute(WalkObject[] result) {
			if(result==null){
				context.finish();
				return;
			}
			walks = result;
			walksListViewAdapter.notifyDataSetChanged();
	     }
	}
	
	public static class LoadWalkBitmapAsyncTask extends AsyncTask<Void, Void, Bitmap>{
		public Context context;
		public long walkId;
		public IWalkListViewItem walkItem;
		
		public LoadWalkBitmapAsyncTask(Context context, long walkId, IWalkListViewItem walkItem){
			this.context = context;
			this.walkId = walkId;
			this.walkItem = walkItem;
		}
		
		@Override
		protected Bitmap doInBackground(Void...voids) {
			WalkObjectGeoObjectDBAdapter dbAdapter = new WalkObjectGeoObjectDBAdapter(context);
			Uri imageUri = dbAdapter.getFirstGeoObjectImageUri(walkId);
			walkItem.setImageUri(imageUri);
			
			//Bitmap bitmap = GeoObject.loadListViewItemBitmap(context, imageUri);
			//return bitmap;
			return null;
		}
		
		@Override
		 protected void onPostExecute(Bitmap result) {
			walkItem.setImageBitmap(result);
			walksListViewAdapter.notifyDataSetChanged();
	     }
	}
}
