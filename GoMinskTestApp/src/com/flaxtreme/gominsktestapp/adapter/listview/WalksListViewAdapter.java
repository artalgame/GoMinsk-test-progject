package com.flaxtreme.gominsktestapp.adapter.listview;

import com.flaxtreme.gominsktestapp.GoMinskApplicationClass;
import com.flaxtreme.gominsktestapp.LocaleHelper;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.fragment.SuggestedWalksListFragment;
import com.flaxtreme.gominsktestapp.interfaces.IWalkListViewItem;
import com.flaxtreme.gominsktestapp.interfaces.IDataArraySource;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WalksListViewAdapter extends BaseAdapter {

    private final Context context;
	private IDataArraySource<IWalkListViewItem> walksSource;
	
	public WalksListViewAdapter(Context context, IDataArraySource<IWalkListViewItem> walkSource){
		this.context = context;
		this.walksSource = walkSource;
	}
	
	@Override
	public int getCount() {
		return walksSource.getDataArray().length;
	}

	@Override
	public Object getItem(int index) {
		if(getCount()>index)
		{
			return walksSource.getDataArray()[index];
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if(getCount() > position)
		{
			return walksSource.getDataArray()[position].getId();
		}
		return -1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final IWalkListViewItem walkItem = (IWalkListViewItem) getItem(position);
		
		if(walkItem!=null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View itemLayout = inflater.inflate(R.layout.item_suggested_walk_layout,null);
			
			final TextView titleView = (TextView)itemLayout.findViewById(R.id.walkTitleTextView);
			titleView.setText(walkItem.getTitle());
			
			final TextView timeView = (TextView)itemLayout.findViewById(R.id.walkTimeTextView);
			timeView.setText(LocaleHelper.getLocalizeWalkTime(walkItem.getPlaningTime()));
			
			final ImageView imageView = (ImageView)itemLayout.findViewById(R.id.walkImageView);
			if(walkItem.hasLoadedBitmap())
			{
				if(walkItem.getImageUri() != null){
					ImageLoader.getInstance().displayImage(walkItem.getImageUri().toString(), imageView, GoMinskApplicationClass.OPTIONS);
				}
				
			}
			else
				{
					new SuggestedWalksListFragment.LoadWalkBitmapAsyncTask(context,walkItem.getId(), walkItem).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}
			/*if(walkItem.hasLoadedBitmap()){
				imageView.setImageBitmap(walkItem.getBitmap());
			}
			else
			{
				new SuggestedWalksListFragment.LoadWalkBitmapAsyncTask(context,walkItem.getId(), walkItem).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}*/
			
			return itemLayout;
		}
		return null;
	}
	
	

}
