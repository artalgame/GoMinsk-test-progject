package com.flaxtreme.gominsktestapp.adapter.listview;

import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.entity.GeoObject;
import com.flaxtreme.gominsktestapp.interfaces.IDataArraySource;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WalkGeoObjectsListViewAdapter extends BaseAdapter {

	private IDataArraySource<GeoObject> geoObjectsDataSource;
	private Context context;
	private Bitmap markerBitmap;
	
	
	public WalkGeoObjectsListViewAdapter(Context context, IDataArraySource<GeoObject> geoObjectsDataSource){
		this.context = context;
		this.geoObjectsDataSource = geoObjectsDataSource;
		setUpMarker();
	}
	
	
	protected void setUpMarker() {
		markerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_green);
		/*int[] pixels= new int[markerBitmap.getWidth()*markerBitmap.getHeight()];
		
		markerBitmap.getPixels(pixels,0,markerBitmap.getWidth(),0,0, markerBitmap.getWidth(), markerBitmap.getHeight());
		
		if(pixels!=null){
			for(int i=0; i<pixels.length; i++){
				int red = Color.red(pixels[i]);
				int green = Color.green(pixels[i]);
				int blue = Color.blue(pixels[i]);
				int alpha = Color.alpha(pixels[i]);
				if(!(red == 255 && green==255 && blue==255))
					if(alpha!=0)
					pixels[i] = Color.parseColor("#279919");
			}
			
			//markerBitmap.setPixels(pixels, 0, markerBitmap.getWidth(), 0, 0, markerBitmap.getWidth(), markerBitmap.getHeight())
			markerBitmap = Bitmap.createBitmap(pixels, markerBitmap.getWidth(), markerBitmap.getHeight(), Config.ARGB_8888);
			markerBitmap = Bitmap.createScaledBitmap(markerBitmap, markerBitmap.getWidth(), markerBitmap.getHeight(), true);
		}*/
	}
	
	@Override
	public int getCount() {
		if(geoObjectsDataSource!=null && geoObjectsDataSource.getDataArray()!=null)
			return geoObjectsDataSource.getDataArray().length;
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if( geoObjectsDataSource!=null && geoObjectsDataSource.getDataArray()!=null){
			return geoObjectsDataSource.getDataArray()[position];
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if( geoObjectsDataSource!=null && geoObjectsDataSource.getDataArray()!=null){
			return geoObjectsDataSource.getDataArray()[position].getId();
		}
		return -1;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		
		final GeoObject geoObjectItem = (GeoObject) getItem(position);
		
		if(geoObjectItem!=null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View itemLayout = inflater.inflate(R.layout.item_walk_geo_object,null);
			
			TextView geoObjectTitle = (TextView) itemLayout.findViewById(R.id.geoObjectTitleTextView);
			geoObjectTitle.setText(geoObjectItem.getTitleInListView());
			
			ImageView markerImageView = (ImageView)itemLayout.findViewById(R.id.markerImageView);
			markerImageView.setImageBitmap(markerBitmap);
			
			return itemLayout;
			
		}
		return null;
	}
}
