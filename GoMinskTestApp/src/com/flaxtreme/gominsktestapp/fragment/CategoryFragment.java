package com.flaxtreme.gominsktestapp.fragment;

import java.util.Hashtable;
import com.flaxtreme.gominsktestapp.GoMinskConstants;
import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.adapter.listview.GeoObjectListViewAdapter;
import com.flaxtreme.gominsktestapp.adapter.sectionpager.CategoryActivitySectionPagerAdapter;
import com.flaxtreme.gominsktestapp.db.CategoryDBAdapter;
import com.flaxtreme.gominsktestapp.db.GeoObjectCategoryDBAdapter;
import com.flaxtreme.gominsktestapp.db.GeoObjectDBAdapter;
import com.flaxtreme.gominsktestapp.entity.CategoryObject;
import com.flaxtreme.gominsktestapp.entity.GeoObject;
import com.flaxtreme.gominsktestapp.interfaces.IFragmentsGetter;
import com.flaxtreme.gominsktestapp.interfaces.ICategoryObjectFilter;
import com.flaxtreme.gominsktestapp.interfaces.IGeoObjectListViewItem;
import com.flaxtreme.gominsktestapp.interfaces.IMapDataSource;
import com.flaxtreme.gominsktestapp.interfaces.IMapObject;
import com.flaxtreme.gominsktestapp.view.TabsAndFilterRelativeLayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

public class CategoryFragment extends Fragment implements
 PopupMenu.OnMenuItemClickListener, ICategoryObjectFilter, IFragmentsGetter, IMapDataSource {
	
	//tabs fields
	private enum TABS{
		LIST,
		MAP
	}
	
	public static final int FRAGMENTS_COUNT = 2;
	
	private static final String TAB_LIST_TAG = "List";
	private static final String TAB_MAP_TAG = "Map";
	
	//------------------------
	
	private ViewPager mViewPager;
	private CategoryActivitySectionPagerAdapter mSectionsPagerAdapter;
	private TabHost tabHost;
	private ImageButton filterButton;
	private View rootLayout;
	private FrameLayout contentFrame;
	
	private CategoryItemsListFragment categoryItemsListFragment;
	private CategoryMapFragment categoryMapFragment;

	private GeoObjectListViewAdapter geoObjectsAdapter;
	
	
	
	private static CategoryObject currentCategory;
	private static GeoObject[] geoObjects;
	
	private static Hashtable<Long, GeoObject> idGeoObjectsHashTable;
	private static Hashtable<Long, CategoryObject> idCategoryObjectsHashTable;
	
	private static CategoryObject[] filterCategoryObjects;
	
	private long selectedFilterItemId = -1;
	private GeoObject[] currentGeoObjects;
	
	//category id and geoObjects ids
	public static Hashtable<Long, long[]> filteringTable;
	
		
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		geoObjectsAdapter = new GeoObjectListViewAdapter(this.getActivity());
		Bundle categoryBundle = this.getArguments();
		currentCategory = categoryBundle.getParcelable(GoMinskConstants.BUNDLE_CATEGORY_PARCELABLE);		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		rootLayout = inflater.inflate(R.layout.fragment_category, container, false);
		setContentFrame((FrameLayout)rootLayout.findViewById(R.id.contentFragment));
		
		TabsAndFilterRelativeLayout tabsLayout = (TabsAndFilterRelativeLayout)rootLayout.findViewById(R.id.TabsBar);
		
		
		initializeFilterButton(rootLayout);
		initializeTabHost(rootLayout); 
		initializeViewPager(rootLayout);
		
		tabsLayout.setBackgroundColor(currentCategory.getColor());
		
		//loads objects of category
		new GeoObjectsLoader(getActivity(), currentCategory).execute();
		
		return rootLayout;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	};
	
	@Override
	public void onStart() {
		super.onStart();
	};
	
	@Override
	public void onResume() {
		super.onResume();
	};
	
	private void initializeFilterButton(View rootLayout) {
		new FilterLoader(getActivity(), currentCategory).execute();
		filterButton = (ImageButton)rootLayout.findViewById(R.id.filter_button);
		filterButton.setVisibility(View.INVISIBLE);
		filterButton.setOnClickListener(new OnClickListener() {
		
			
			@Override
			public void onClick(View v) {
				showFilterPopup(v);
			}
		});
	}

	private void initializeViewPager(View rootLayout) {
		mSectionsPagerAdapter = new CategoryActivitySectionPagerAdapter(
				getActivity().getSupportFragmentManager(), getActivity(),this );


		mViewPager = (ViewPager) rootLayout.findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);


		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
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
			View tabView = createTabView(TABS.LIST, tabHost.getTabWidget());
			TabHost.TabSpec spec = tabHost.newTabSpec(TAB_LIST_TAG).setIndicator(tabView).setContent(android.R.id.tabcontent);
			tabHost.addTab(spec);
			
			
			tabView = createTabView(TABS.MAP, tabHost.getTabWidget());
			spec = tabHost.newTabSpec(TAB_MAP_TAG).setIndicator(tabView).setContent(android.R.id.tabcontent);
			tabHost.addTab(spec);
			
			//add list fragment as first viewed fragment
			//getActivity().getSupportFragmentManager().beginTransaction().add(R.id.contentFragment, categoryItemsListFragment).commit();
			
			//Invoke ViewPager setCurrentItem element to change current fragment
			tabHost.setOnTabChangedListener(new OnTabChangeListener() {
				
				@Override
				public void onTabChanged(String tabId) {
					
				mViewPager.setCurrentItem(tabHost.getCurrentTab());
			/*	if(tabId == TAB_LIST_TAG)
						getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, categoryItemsListFragment).commit();
						else
							if(tabId == TAB_MAP_TAG)
								getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, categoryMapFragment).commit();*/
					
				}
			});
		}
	}
	
    private View createTabView(TABS tabName, ViewGroup rootView) {
		
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
	
	public void showFilterPopup(View v) {
		PopupMenu popup = new PopupMenu(getActivity(), v);
	    MenuInflater inflater = popup.getMenuInflater();
	    inflater.inflate(R.menu.filter_popup_menu, popup.getMenu());
	   
	    for(int i=0; i<filterCategoryObjects.length; i++){
	    	CategoryObject filter = filterCategoryObjects[i];
	    	 MenuItem item = popup.getMenu().add(R.id.filterItemsList,(int)filter.getId(),Menu.NONE, filter.getTitle()); 
	    	 item.setCheckable(true);
	    	 if(selectedFilterItemId == (int)filter.getId()){
	 	    	item.setChecked(true);
	 	    }
	    }
	    popup.setOnMenuItemClickListener(this);
	    popup.show();
	}

	@Override
	public boolean onMenuItemClick(MenuItem selectedItem) {
		if(selectedItem.getItemId() == selectedFilterItemId){
			selectedItem.setChecked(false);
			selectedFilterItemId = -1;
		}
		else
		{
			selectedFilterItemId = selectedItem.getItemId();
			selectedItem.setChecked(true);
		}
		changeCurrentGeoObjects();
		return false;
	}
	
	private void changeCurrentGeoObjects() {
		if(selectedFilterItemId == -1){
			currentGeoObjects = geoObjects;
		}else{
			long[] objects = filteringTable.get(selectedFilterItemId);
			if(objects!= null)
			{
				currentGeoObjects = new GeoObject[objects.length];
				int i=0;
				for(long geoObjectId: objects)
				{
					currentGeoObjects[i] = idGeoObjectsHashTable.get(geoObjectId);
					i++;
				}
			}
		}
		geoObjectsAdapter.setGeoObjects(currentGeoObjects);
		categoryMapFragment.mapDataChanged();
	}

	public CategoryObject getCurrentCategory(){
		return currentCategory;
	}
	
	public void setCurrentCategory(CategoryObject category){
		currentCategory = category;
	}
	
	public class FilterLoader extends AsyncTask<Void, Void, CategoryObject[]>{
		public Context context;
		public CategoryObject parentCategory;
		
		public FilterLoader(Context context, CategoryObject parentObject){
			this.context = context;
			this.parentCategory = parentObject;
		}
		
		@Override
		protected CategoryObject[] doInBackground(Void... arg0) {
			CategoryDBAdapter dbAdapter = new CategoryDBAdapter(context);
			
			Object[] objects = dbAdapter.getChildCategories(parentCategory.getId());
			
			if(objects == null) return null;
			
			CategoryObject[] subCategories = new CategoryObject[objects.length];
			
			int i=0;
			for(Object object:objects){
				subCategories[i] = (CategoryObject) object;
				i++;
			}
			 		
			if(subCategories!=null){
				filteringTable = new Hashtable<Long, long[]>();
				GeoObjectCategoryDBAdapter geoObjectCategoryDBAdapter = new GeoObjectCategoryDBAdapter(context);
				for(i=0; i< subCategories.length; i++){
					long[] geoObjectIds = geoObjectCategoryDBAdapter.getGeoObjectIdsForCategory(subCategories[i].getId());
					filteringTable.put(subCategories[i].getId(), geoObjectIds);
				}
			}
			return subCategories;
		}
		
		@Override
		 protected void onPostExecute(CategoryObject[] result) {
			if( result!=null){
				idCategoryObjectsHashTable = new Hashtable<Long, CategoryObject>(result.length);
				for(CategoryObject category: result){
					idCategoryObjectsHashTable.put(category.getId(), category);
				}
				filterCategoryObjects = result;
				filterButton.setVisibility(View.VISIBLE);
			}
			else{
				filterCategoryObjects = new CategoryObject[0];
			}
		}
	}
	
	public class GeoObjectsLoader extends AsyncTask<Void, Void, GeoObject[]>{

		public Context context;
		public CategoryObject currentCategory;
		
		
		public GeoObjectsLoader(Context context, CategoryObject currentCategory){
			this.context = context;
			this.currentCategory = currentCategory;
			
		}
		
		@Override
		protected GeoObject[] doInBackground(Void... arg0) {
			GeoObject[] geoObjects = null;
			
			GeoObjectCategoryDBAdapter dbAdapter = new GeoObjectCategoryDBAdapter(context);
			long[] geoObjectIds = dbAdapter.getGeoObjectIdsForCategory(currentCategory.getId());
			
			if(geoObjectIds!=null && geoObjectIds.length!=0)
			{
				geoObjects = new GeoObject[geoObjectIds.length];
				GeoObjectDBAdapter geoObjectDBAdapter = new GeoObjectDBAdapter(context);
				int i=0;
				for(long id: geoObjectIds){
					geoObjects[i] = geoObjectDBAdapter.getObjectItem(id);
					i++;
				}
			}
			return geoObjects;
		}

		@Override
		 protected void onPostExecute(GeoObject[] result) {
			if(result!=null){
				idGeoObjectsHashTable = new Hashtable<Long, GeoObject>(result.length);
				for(GeoObject geoObject:result){
					idGeoObjectsHashTable.put(geoObject.getId(), geoObject);
				}
				geoObjectsAdapter.setGeoObjects(result);
				geoObjects = result;
				currentGeoObjects = result;
				categoryMapFragment.mapDataChanged();
			}
		}
	}
	
	
	public static class ItemBitmapLoader extends AsyncTask<Void, Void, Bitmap>{

		public Context context;
		public CategoryObject currentCategory;
		IGeoObjectListViewItem item;
		private GeoObjectListViewAdapter geoObjectsAdapter;
		
		public int itemPosition;
		
		public ItemBitmapLoader(Context context, int itemPosition, IGeoObjectListViewItem item,GeoObjectListViewAdapter geoObjectsAdapter){
			this.item = item;
			this.itemPosition = itemPosition;
			this.geoObjectsAdapter = geoObjectsAdapter;
			this.context = context;
		}
		
		@Override
		protected Bitmap doInBackground(Void... arg0) {
			return GeoObject.loadListViewItemBitmap(context, item.getImageURI());
		}

		@Override
		 protected void onPostExecute(Bitmap result) {
			IGeoObjectListViewItem item = (IGeoObjectListViewItem)geoObjectsAdapter.getItem(itemPosition);
			item.setBitmap(result);
			geoObjectsAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public GeoObject[] getFilteredGeoObjects() {
		return geoObjects;
	}

	@Override
	public Fragment getSubFragment(int pos) {
		switch(pos){
			case 0:
			{
				categoryItemsListFragment = new CategoryItemsListFragment();
				categoryItemsListFragment.setListViewAdapter(geoObjectsAdapter);
				return categoryItemsListFragment;
			}
			case 1:
			{
				categoryMapFragment = new CategoryMapFragment();
				categoryMapFragment.setArguments(getArguments());
				categoryMapFragment.setMapDataSource(this);
				return categoryMapFragment;			
			}
		}
		return null;
	}

	@Override
	public IMapObject[] getCurrentGeoObjects() {
		return currentGeoObjects;
	}

	@Override
	public int getSubFragmentCount() {
		return FRAGMENTS_COUNT;
	}

	public FrameLayout getContentFrame() {
		return contentFrame;
	}

	public void setContentFrame(FrameLayout contentFrame) {
		this.contentFrame = contentFrame;
	}
}
