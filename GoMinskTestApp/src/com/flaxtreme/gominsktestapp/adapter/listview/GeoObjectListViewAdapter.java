package com.flaxtreme.gominsktestapp.adapter.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flaxtreme.gominsktestapp.GoMinskApplicationClass;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.entity.GeoObject;
import com.flaxtreme.gominsktestapp.interfaces.IGeoObjectListViewItem;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GeoObjectListViewAdapter extends BaseAdapter{

	private final Context mContext;
	
	private IGeoObjectListViewItem[] mItems = new IGeoObjectListViewItem[0];
	private RelativeLayout[] itemLayouts;
	private ImageLoader imageLoader;
	
	public GeoObjectListViewAdapter(Context context){
		mContext = context;
		imageLoader = ImageLoader.getInstance();
		
	}
			
	@Override
	public int getCount() {

		return mItems.length;
	}

	@Override
	public Object getItem(int position) {
		return mItems[position];
	}

	@Override
	public long getItemId(int position) {
		GeoObject item = (GeoObject)getItem(position);
		if(item!=null){
			return item.getId();
		}
		return -1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final IGeoObjectListViewItem geoObjectItem = (IGeoObjectListViewItem) getItem(position);
		
		RelativeLayout itemLayout = itemLayouts[position];
		if(itemLayout == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemLayout = (RelativeLayout) inflater.inflate(R.layout.item_geo_objects_layout,null);
			itemLayouts[position] = itemLayout;
		}
		
		final TextView nameView = (TextView)itemLayout.findViewById(R.id.geoObjectTitleTextView);
		nameView.setText(geoObjectItem.getTitleInListView());
		
		final TextView destinationTextView = (TextView)itemLayout.findViewById(R.id.geoObjectDestinationTextView);
		destinationTextView.setText(geoObjectItem.getDestinationInListView());
		
		
		if(geoObjectItem.getImageURI()!=null)
		{
			final ImageView imageView = (ImageView)itemLayout.findViewById(R.id.geoObjectImageView);
			final String imageUri = geoObjectItem.getImageURI().toString();
			
			imageLoader.displayImage(imageUri, imageView,GoMinskApplicationClass.OPTIONS);
	}
		/*Bitmap imageBitmap = geoObjectItem.getBitmap();
		if(!geoObjectItem.hasBitmap()){
			Bundle bundle = new Bundle();
			bundle.putInt("pos", position);
			new ItemBitmapLoader(mContext, position, geoObjectItem, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
		else{
			imageView.setImageBitmap(imageBitmap);
		}*/
		
		return itemLayout;
	}

	public void setGeoObjects(IGeoObjectListViewItem[] objects) {
		mItems = objects;
		itemLayouts = new RelativeLayout[mItems.length];
		notifyDataSetChanged();
		
	}

}
