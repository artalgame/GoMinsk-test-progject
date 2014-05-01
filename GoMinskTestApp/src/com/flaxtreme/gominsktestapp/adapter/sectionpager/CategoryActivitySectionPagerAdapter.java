package com.flaxtreme.gominsktestapp.adapter.sectionpager;

import com.flaxtreme.gominsktestapp.R;
import com.flaxtreme.gominsktestapp.interfaces.IFragmentsGetter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * 
 * @author Alexander Korolchuk
 *
 * This class implements FragmentPagerAdapter logic returning <code>CategoryItemsListFragment</code> or <code>CategoryMapFragment</code> fragments
 */
public class CategoryActivitySectionPagerAdapter extends FragmentPagerAdapter {

	private Context mContext;
	private IFragmentsGetter categoryFragmentGetter;

	
	public CategoryActivitySectionPagerAdapter(FragmentManager fm, Context context, IFragmentsGetter categoryFragmentGetter) {
		super(fm);
		mContext = context;
		this.categoryFragmentGetter = categoryFragmentGetter;
	}

	@Override
	public Fragment getItem(int position) {
		return categoryFragmentGetter.getSubFragment(position);
	}

	@Override
	public int getCount() {
		return categoryFragmentGetter.getSubFragmentCount();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return mContext.getString(R.string.button_list_name);
		case 1:
			return mContext.getString(R.string.button_map_name);
		}
		return null;
	}

}
