package com.flaxtreme.gominsktestapp.adapter.sectionpager;

import com.flaxtreme.gominsktestapp.interfaces.IFragmentsGetter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class WalkSectionPagerAdapter extends FragmentPagerAdapter {

	private IFragmentsGetter fragmentGetter;
	
	public WalkSectionPagerAdapter(FragmentManager fm, Context context, IFragmentsGetter fragmentGetter) {
		super(fm);
		this.fragmentGetter = fragmentGetter;
	}

	@Override
	public Fragment getItem(int position) {
		return fragmentGetter.getSubFragment(position);
	}

	@Override
	public int getCount() {
		return fragmentGetter.getSubFragmentCount();
	}

}
